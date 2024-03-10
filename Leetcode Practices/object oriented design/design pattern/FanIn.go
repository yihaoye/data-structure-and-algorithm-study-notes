package main

import (
	"fmt"
	"sync"
)

var done = make(chan struct{})

func producer(ch chan<- int, num int) {
	for i := 0; i < 5; i++ {
		ch <- num + i
	}
	close(ch)
}

func fanIn(channels ...<-chan int) <-chan int {
	out := make(chan int)
	var wg sync.WaitGroup
	wg.Add(len(channels))

	for _, ch := range channels {
		go func(c <-chan int) {
			defer wg.Done()
			for v := range c {
				select {
				case out <- v:
				case <-done:
					return
				}
			}
		}(ch)
	}

	go func() {
		wg.Wait()
		close(out)
	}()

	return out
}

func main() {
	ch1 := make(chan int)
	ch2 := make(chan int)

	go producer(ch1, 0)
	go producer(ch2, 10)

	for v := range fanIn(ch1, ch2) {
		fmt.Println(v)
	}

	close(done)
}
