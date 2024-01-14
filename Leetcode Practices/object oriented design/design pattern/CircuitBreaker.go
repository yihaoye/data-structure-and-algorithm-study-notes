package main

import (
	"fmt"
	"sync"
	"time"
)

// CircuitBreaker 是断路器结构
type CircuitBreaker struct {
	mu          sync.Mutex
	isOpen      bool
	consecutive int
}

// NewCircuitBreaker 返回一个新的断路器实例
func NewCircuitBreaker() *CircuitBreaker {
	return &CircuitBreaker{}
}

// Execute 用于执行受断路器保护的操作
func (c *CircuitBreaker) Execute(operation func() error) error {
	c.mu.Lock()
	defer c.mu.Unlock()

	if c.isOpen {
		// 如果断路器开启，直接返回错误
		return fmt.Errorf("Circuit is open")
	}

	// 执行操作
	err := operation()

	// 根据操作结果更新断路器状态
	if err != nil {
		// 操作失败，增加连续失败计数
		c.consecutive++

		// 如果连续失败次数达到阈值，开启断路器
		if c.consecutive >= 3 {
			c.isOpen = true
			fmt.Println("Circuit breaker opened")
			go c.halfOpenTimer()
		}
	} else {
		// 操作成功，重置连续失败计数
		c.consecutive = 0
	}

	return err
}

// halfOpenTimer 定时器，用于在一段时间后尝试将断路器从开启状态切换到半开状态
func (c *CircuitBreaker) halfOpenTimer() {
	time.Sleep(5 * time.Second) // 5秒后尝试半开
	c.mu.Lock()
	defer c.mu.Unlock()

	fmt.Println("Attempting to switch to half-open state")
	c.isOpen = false
	fmt.Println("Circuit breaker switched to half-open state")
}

func main() {
	// 创建断路器
	breaker := NewCircuitBreaker()

	// 模拟执行受断路器保护的操作
	for i := 0; i < 10; i++ {
		err := breaker.Execute(func() error {
			// 模拟一些操作，这里简单返回成功或失败
			if i%2 == 0 {
				return nil // 操作成功
			}
			return fmt.Errorf("Operation failed") // 操作失败
		})

		if err != nil {
			fmt.Printf("Error: %s\n", err.Error())
		} else {
			fmt.Println("Operation succeeded")
		}

		// 等待一段时间
		time.Sleep(1 * time.Second)
	}
}
