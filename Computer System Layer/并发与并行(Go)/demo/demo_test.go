package demo

import (
	"fmt"
	"sync"
	"sync/atomic"
	"testing"
)

func TestParallel(t *testing.T) {
	t.Run("Test Add Int", func(t *testing.T) {
		var wg sync.WaitGroup
		var count int32
		for i := 0; i < 1000; i++ {
			wg.Add(1)
			go func() {
				atomic.AddInt32(&count, 1)
				wg.Done()
			}()
		}
		wg.Wait()
		if count != 1000 {
			t.Errorf("actual %d, expect 1000", count)
		}
	})

	t.Run("Test Channel + Select + Timeout", func(t *testing.T) {
		ch := make(chan int, 2)
		defer close(ch)
		for i := 0; i < 10; i++ {
			go func(i int) {
				ch <- i
			}(i)
		}
		for i := 0; i < 10; i++ {
			select {
			case v := <-ch:
				t.Log(v)
			case <-time.After(1 * time.Second):
				t.Log("timeout")
			default:
				t.Log("default")
			}
		}
	})

	t.Run("Test Mutex", func(t *testing.T) {
		var mu sync.Mutex
		var count int
		for i := 0; i < 1000; i++ {
			go func() {
				mu.Lock()
				defer mu.Unlock()
				count++
			}()
		}
		time.Sleep(1 * time.Second)
		t.Log(count)
	})

	t.Run("Test Compare And Swap", func(t *testing.T) {
		var count int32
		for i := 0; i < 1000; i++ {
			go func() {
				for {
					old := atomic.LoadInt32(&count)
					if atomic.CompareAndSwapInt32(&count, old, old+1) {
						break
					}
				}
			}()
		}
		time.Sleep(1 * time.Second)
		t.Log(count)
	})

	t.Run("Test Once", func(t *testing.T) {
		var once sync.Once
		for i := 0; i < 10; i++ {
			go func() {
				once.Do(func() {
					t.Log("only once")
				})
			}()
		}
	})

	t.Run("Test Pool", func(t *testing.T) {
		pool := &sync.Pool{
			New: func() interface{} {
				return "hello"
			},
		}
		v := pool.Get()
		pool.Put(v)
		v = pool.Get()
		t.Log(v)
	})

	t.Run("Test Cond", func(t *testing.T) {
		var mu sync.Mutex
		cond := sync.NewCond(&mu) // cond 意思是条件变量
		for i := 0; i < 10; i++ {
			go func(i int) {
				cond.L.Lock()
				defer cond.L.Unlock()
				cond.Wait()
				t.Log(i)
			}(i)
		}
		time.Sleep(1 * time.Second)
		cond.Broadcast() // cond.Signal() 只唤醒一个等待的 goroutine, cond.Broadcast() 唤醒所有等待的 goroutine
	})

	t.Run("Test RWMutex", func(t *testing.T) {
		var mu sync.RWMutex
		var count int
		for i := 0; i < 10; i++ {
			go func(i int) {
				mu.Lock()
				defer mu.Unlock()
				count++
			}(i)
		}
		for i := 0; i < 10; i++ {
			go func(i int) {
				mu.RLock()
				defer mu.RUnlock()
				t.Log(count)
			}(i)
		}
		time.Sleep(1 * time.Second)
	})

	t.Run("Test atomic Store and Swap", func(t *testing.T) {
		// https://gfw.go101.org/article/concurrent-atomic-operation.html
		// https://cloud.tencent.com/developer/article/2293811
		type T struct{ a, b, c int }
		var x = T{1, 2, 3}
		var y = T{4, 5, 6}
		var z = T{7, 8, 9}
		var v atomic.Value
		v.Store(x)
		fmt.Println(v)       // {{1 2 3}}
		old := v.Swap(y)     // old == x
		fmt.Println(v)       // {{4 5 6}}
		fmt.Println(old.(T)) // {1 2 3}
		swapped := v.CompareAndSwap(x, z)
		fmt.Println(swapped, v) // false {{4 5 6}}
		swapped = v.CompareAndSwap(y, z)
		fmt.Println(swapped, v) // true {{7 8 9}}
	})
}
