package main

import (
	"bufio"
	"cmp"
	"fmt"
	"log"
	"math"
	"os"
	"slices"
)

type coord struct {
	x int64
	y int64
}

const (
	fmtHeader  = "#Life 1.06"
	inputFile  = "input.life"
	outputFile = "output.life"
)

func main() {
	fmt.Println("Game of Life: Start")

	cells, err := read(inputFile)
	if err != nil {
		log.Fatalf("failed to read input file: %v", err)
	}

	for range 10 {
		iterate(cells)
	}

	err = write(outputFile, cells)
	if err != nil {
		log.Fatalf("failed to write output file: %v", err)
	}

	fmt.Println("Game of Life: End")
}

func iterate(cells map[coord]struct{}) {
	// map to record the cells neighbor count
	tmp := make(map[coord]int, len(cells)*8)

	// Time Complexity: O(N), where N is the number of live cells.
	for c := range cells {
		// for each live cell, add 1 to its neighbors
		if c.y > math.MinInt64 {
			tmp[coord{c.x, c.y - 1}]++
		}
		if c.y < math.MaxInt64 {
			tmp[coord{c.x, c.y + 1}]++
		}
		if c.x > math.MinInt64 {
			tmp[coord{c.x - 1, c.y}]++
		}
		if c.x < math.MaxInt64 {
			tmp[coord{c.x + 1, c.y}]++
		}
		if c.y > math.MinInt64 && c.x > math.MinInt64 {
			tmp[coord{c.x - 1, c.y - 1}]++
		}
		if c.y > math.MinInt64 && c.x < math.MaxInt64 {
			tmp[coord{c.x + 1, c.y - 1}]++
		}
		if c.y < math.MaxInt64 && c.x > math.MinInt64 {
			tmp[coord{c.x - 1, c.y + 1}]++
		}
		if c.y < math.MaxInt64 && c.x < math.MaxInt64 {
			tmp[coord{c.x + 1, c.y + 1}]++
		}
	}
	for k := range cells {
		// if a live cell has less than 2 or more than 3 neighbors, it dies
		if tmp[k] < 2 || tmp[k] > 3 {
			// delete while iterating map is safe in Go, but not in other languages like Python or Java
			delete(cells, k)
		}
	}
	for k, v := range tmp {
		// dead cell with 3 neighbors becomes alive
		// live cell with 3 neighbors could be rewritten again, but map will not be changed, which is fine
		if v == 3 {
			cells[k] = struct{}{}
		}
	}
}

// read initial version cells from input file, return a map of live cells
func read(filename string) (map[coord]struct{}, error) {
	file, err := os.Open(filename)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	scanner.Scan()
	if scanner.Text() != fmtHeader {
		return nil, fmt.Errorf("invalid input file format")
	}

	cells := map[coord]struct{}{}
	for scanner.Scan() {
		line := scanner.Text()

		var x, y int64
		_, err := fmt.Sscanf(line, "%d %d", &x, &y)
		if err != nil {
			return nil, err
		}
		cells[coord{x, y}] = struct{}{}
	}
	if err := scanner.Err(); err != nil {
		return nil, err
	}
	return cells, nil
}

// write the final version cells to output file
func write(filename string, cells map[coord]struct{}) error {
	file, err := os.Create(filename)
	if err != nil {
		return err
	}
	defer file.Close()

	_, err = fmt.Fprintf(file, "%s\n", fmtHeader)
	if err != nil {
		return err
	}

	// sort is not required but nice to have for better readability
	sorted := make([]coord, 0, len(cells))
	for c := range cells {
		sorted = append(sorted, c)
	}
	slices.SortFunc(sorted, func(a, b coord) int {
		if c := cmp.Compare(a.x, b.x); c != 0 {
			return c
		}
		return cmp.Compare(a.y, b.y)
	})
	for _, c := range sorted {
		_, err = fmt.Fprintf(file, "%d %d\n", c.x, c.y)
		if err != nil {
			return err
		}
	}
	return nil
}
