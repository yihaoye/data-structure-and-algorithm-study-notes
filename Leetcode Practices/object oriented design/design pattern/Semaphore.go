package main

import (
	"context"
	"fmt"
	"math/rand"
	"time"

	"golang.org/x/sync/errgroup"
	"golang.org/x/sync/semaphore"
)

func main() {
	err := run(context.Background())
	if err != nil {
		panic(err)
	}
}

func run(ctx context.Context) error {
	sem := semaphore.NewWeighted(10)
	g, ctx := errgroup.WithContext(ctx)

	for i := 0; i < 100; i++ {
		// i := i
		if err := sem.Acquire(ctx, 1); err != nil {
			return err
		}

		g.Go(func() error {
			defer sem.Release(1)
			return callAPI(ctx)
		})
	}

	return g.Wait()
}

// Mock request down stream API
func callAPI(ctx context.Context) error {
	random := rand.Intn(5)
	if random == 0 {
		return fmt.Errorf("error")
	}
	time.Sleep(time.Duration(random) * time.Second)
	return nil
}
