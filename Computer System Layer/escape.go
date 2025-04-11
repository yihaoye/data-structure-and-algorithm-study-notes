package main

import (
	"fmt"
	"runtime"
	"time"
)

type Person struct {
	Name    string
	Age     int
	Address string
	Phone   string
	Email   string
}

func NewPersonPointer() *Person {
	p := Person{
		Name:    "John Doe",
		Age:     30,
		Address: "123 Main St, Anytown, AT 12345",
		Phone:   "555-123-4567",
		Email:   "john.doe@example.com",
	}
	return &p // 会逃逸
}

/*
% GOGC=50 go run escape.go

默认 GOGC=100
GOGC 是控制垃圾回收触发的阈值，它是一个百分比。
表示当堆内存增长到上一次 GC 后大小的 100%（即翻倍）时，Go 运行时就会触发下一次 GC。
假设上次 GC 后堆用了 50MB：当堆增长到 50MB + 100% = 100MB，就会触发下一次 GC。

运行 30 秒结果
Total allocations: 2510376
Number of allocations: 29535
Number of GC runs: 1
Average alloc per GC: 2510376 bytes

Go 的 GC（垃圾回收）采用的是并发三色标记清除算法，所以 不会长时间 Stop-The-World（STW），但确实每次 GC 都有短暂的 STW 阶段，通常发生在：
标记开始前（STW #1）
清理结束后（STW #2）
根据 官方 Go runtime 团队的数据:
"Typical stop-the-world pause times are well under 1 millisecond, even for large heaps."
所以基本不用担心
*/
func main() {
	var memStats runtime.MemStats
	runtime.ReadMemStats(&memStats)

	// 模拟 1000 QPS，持续 10 秒
	ticker := time.NewTicker(time.Microsecond * 1000) // 1000μs = 1ms = 1000 QPS
	defer ticker.Stop()

	done := time.After(30 * time.Second)
	count := 0
	var p *Person

	for {
		select {
		case <-ticker.C:
			p = NewPersonPointer()
			_ = p // 使用结果防止编译器优化掉
			count++
		case <-done:
			goto END
		}
	}

END:
	runtime.ReadMemStats(&memStats)

	fmt.Println("Total allocations:", memStats.TotalAlloc)
	fmt.Println("Number of allocations:", count)
	fmt.Println("Number of GC runs:", memStats.NumGC)
	if memStats.NumGC > 0 {
		fmt.Println("Average alloc per GC:", (memStats.TotalAlloc)/uint64(memStats.NumGC), "bytes")
	}
}
