package main

import (
	"fmt"
	"time"
)

const bufferSize = 10 // 缓冲区大小

func producer(ch chan<- int) {
	for i := 0; i < bufferSize; i++ {
		ch <- i // 将数据写入通道
	}
	close(ch) // 关闭通道
}

func consumer(ch <-chan int, done chan<- bool) {
	for data := range ch { // 从通道中读取数据
		fmt.Println(data) // 处理数据
	}
	done <- true // 发送完成信号
}

func main() {
	ch := make(chan int, bufferSize) // 创建缓冲区通道
	done := make(chan bool)          // 创建完成信号通道

	go producer(ch) // 启动生产者
	time.Sleep(5 * time.Second)
	go consumer(ch, done) // 启动消费者

	<-done // 等待完成信号
}
