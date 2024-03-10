package main

import (
	"fmt"
	"sync"
	"time"
)

var count int        // 共享变量
var mutex sync.Mutex // 互斥锁

func increment() {
	mutex.Lock()         // 获取互斥锁
	defer mutex.Unlock() // 确保在函数返回时释放互斥锁

	count++ // 访问共享变量
}

func main() {
	// 启动10个goroutine并发访问共享变量
	for i := 0; i < 10; i++ {
		go increment()
	}

	// 等待所有goroutine完成
	time.Sleep(3 * time.Second)
	fmt.Println("count:", count)
}
