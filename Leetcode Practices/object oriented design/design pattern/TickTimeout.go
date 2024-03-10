package main

import (
	"context"
	"fmt"
	"time"
)

func main() {
	// 超时模式
	// 创建一个带超时的上下文，设置超时时间为 2 秒
	ctx, cancel := context.WithTimeout(context.Background(), 2*time.Second)
	defer cancel()

	// 模拟一个需要耗时的操作，比如查询数据库
	go func() {
		time.Sleep(3 * time.Second)
		cancel() // 如果操作完成，则取消超时
	}()

	select {
	case <-ctx.Done():
		fmt.Println("Operation timed out")
	case <-time.After(4 * time.Second):
		fmt.Println("Program terminated")
	}

	// 定时器模式
	// 在 2 秒后执行某个操作
	<-time.After(2 * time.Second)
	fmt.Println("2 seconds have passed")

	// 每隔 1 秒执行某个操作，直到主动停止
	ticker := time.Tick(1 * time.Second)
	for {
		select {
		case <-ticker:
			fmt.Println("1 second has passed")
		}
	}
}
