package lurand

import (
	"sync"
	"testing"
)

func TestRegular(t *testing.T) {
	t.Run("Default Max Succeed", func(t *testing.T) {
		rg := New()
		dedup := make(map[int]bool)
		for i := 0; i < defaultMax; i++ {
			num := rg.Intn()
			if dedup[num] {
				t.Errorf("%d: duplicate num: %d", i, num)
				return
			}
			dedup[num] = true
		}
		if len(dedup) != defaultMax {
			t.Errorf("len(dedup) != defaultMax: %d != %d", len(dedup), defaultMax)
			return
		}
	})

	t.Run("Default Max Failed", func(t *testing.T) {
		defer func() {
			if r := recover(); r != nil {
				if r != "No more numbers available" {
					t.Errorf("Unexpected panic message: %v", r)
				}
			} else {
				t.Errorf("Expected a panic, but none occurred")
			}
		}()

		rg := New()
		dedup := make(map[int]bool)
		for i := 0; i < defaultMax+1; i++ {
			num := rg.Intn()
			if dedup[num] {
				t.Errorf("%d: duplicate num: %d", i, num)
				return
			}
			dedup[num] = true
		}
	})
}

func BenchmarkTest(b *testing.B) {
	// BenchmarkTest/Customize_Max_Succeed-8         	 4058866	       491.1 ns/op	      76 B/op	       0 allocs/op
	b.Run("Customize Max Succeed", func(b *testing.B) {
		rg := New_(b.N)
		dedup := make(map[int]bool)
		for i := 0; i < b.N; i++ {
			num := rg.Intn()
			if dedup[num] {
				b.Errorf("%d: duplicate num: %d", i, num)
				return
			}
			dedup[num] = true
			// b.Log(num)
		}
	})

	// BenchmarkTest/Parallel_Customize_Max_Succeed-8         	 1000000	      1056 ns/op	     194 B/op	       4 allocs/op
	b.Run("Parallel Customize Max Succeed", func(b *testing.B) {
		rg := New_(b.N)
		dedup := sync.Map{}
		b.RunParallel(func(pb *testing.PB) {
			for i := 0; pb.Next(); i++ {
				num := rg.Intn()
				if _, ok := dedup.Load(num); ok {
					b.Errorf("%d: duplicate num: %d", i, num)
					return
				}
				dedup.Store(num, true)
				// b.Log(num)
			}
		})
	})
}
