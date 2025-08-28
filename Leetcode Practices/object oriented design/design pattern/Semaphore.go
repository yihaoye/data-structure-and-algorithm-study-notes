package main

import (
	"context"
	"fmt"
	"time"

	"golang.org/x/sync/errgroup"
	"golang.org/x/sync/semaphore"
)

func main() {
	sem := semaphore.NewWeighted(10)
	g, ctx := errgroup.WithContext(context.Background())

	for i := range 100 {
		i := i
		g.Go(func() error {
			if err := sem.Acquire(ctx, 1); err != nil {
				return err
			}
			defer sem.Release(1)

			// Mock request down stream API
			fmt.Printf("Run: %v", i)
			time.Sleep(3 * time.Second)

			return nil
		})
	}

	g.Wait()
	fmt.Printf("Finished")
}
