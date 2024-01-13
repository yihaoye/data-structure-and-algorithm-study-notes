package main

import (
	"fmt"
	"time"
)

func main() {
	ch := make(chan int, 5)

	go producer(ch)

	// 模拟消费者处理
	for {
		val := <-ch
		fmt.Printf("Consumed: %d\n", val)
		time.Sleep(2 * time.Second) // 模拟处理时间
	}
}

func producer(ch chan int) {
	fib := fibonacci()
	for i := 0; ; i++ {
		// 尝试将元素发送到通道，采用斐波那契延时策略
		for !trySend(ch, i) { // while !trySend(ch, i)
			time.Sleep(fib())
		}
	}
}

func trySend(ch chan int, value int) bool {
	select {
	case ch <- value:
		fmt.Printf("Produced: %d\n", value)
		return true
	default:
		fmt.Println("Channel is full. Retrying...")
		return false
	}
}

// 斐波那契数列
func fibonacci() func() time.Duration {
	a, b := 0, 1
	return func() time.Duration {
		a, b = b, a+b
		return time.Duration(a) * time.Second
	}
}
