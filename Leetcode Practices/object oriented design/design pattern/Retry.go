package main

import (
	"fmt"
	"math/rand"
	"time"
)

// ToDo 除了斐波那契外，更多退避策略：固定间隔、指数或带抖动的指数、随机或比例概率、最大重试、自定义策略等等

func main() {
	fib := fibonacci()
	for i := 0; ; i++ {
		// 尝试进行某项操作，采用斐波那契延时策略
		for !try() { // while !try()
			time.Sleep(fib())
		}
	}
}

func try() bool {
	val := rand.Intn(100)
	if val < 30 {
		fmt.Printf("Succeed: %d\n", val)
		return true
	}
	fmt.Println("Failed, retrying...")
	return false
}

// 斐波那契数列
func fibonacci() func() time.Duration {
	a, b := 0, 1
	return func() time.Duration {
		a, b = b, a+b
		return time.Duration(a) * time.Second
	}
}
