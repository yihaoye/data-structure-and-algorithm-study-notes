package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func main() {
	// 使用当前时间的纳秒数作为种子创建一个随机源
	src := rand.NewSource(time.Now().UnixNano())
	r := rand.New(src)

	var wg sync.WaitGroup
	var mu sync.Mutex

	// 启动多个并发 goroutine
	for i := 0; i < 10; i++ {
		wg.Add(1)
		go func(id int) {
			defer wg.Done()
			mu.Lock()
			defer mu.Unlock()
			// 使用互斥锁保护 rand.Rand 实例的并发访问
			n := r.Intn(100)
			fmt.Printf("Goroutine %d: %d\n", id, n)
		}(i)
	}

	wg.Wait()
}
