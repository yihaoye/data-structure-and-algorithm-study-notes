// https://www.topgoer.com/%E5%B9%B6%E5%8F%91%E7%BC%96%E7%A8%8B/%E5%AE%9A%E6%97%B6%E5%99%A8.html
package main

import (
	"fmt"
	"time"
)

func main() { // 定时器 Ticker：时间到了，多次执行
	// 1. 获取 ticker 对象
	ticker := time.NewTicker(1 * time.Second)
	i := 0
	// 子协程
	go func() {
		for {
			// <-ticker.C
			i++
			fmt.Println(<-ticker.C)
			if i == 5 {
				// 停止
				ticker.Stop()
			}
		}
	}()
	// 主协程
	for {
	}
}
