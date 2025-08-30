package main

import (
	"context"
	"log"
	"time"

	"golang.org/x/sync/errgroup"
	"golang.org/x/sync/semaphore"
)

func main() {
	sem := semaphore.NewWeighted(10)
	g, ctx := errgroup.WithContext(context.Background())

	for i := range 100 {
		i := i
		if err := sem.Acquire(ctx, 1); err != nil {
			panic(err)
		}

		g.Go(func() error {
			defer sem.Release(1)

			// Mock request down stream API
			log.Printf("Run: %v", i)
			time.Sleep(3 * time.Second)

			return nil
		})
	}

	g.Wait()
	log.Printf("Finished")
}
