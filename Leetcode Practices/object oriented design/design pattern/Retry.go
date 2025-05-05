package main

import (
	"fmt"
	"math/rand"
	"time"
)

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
