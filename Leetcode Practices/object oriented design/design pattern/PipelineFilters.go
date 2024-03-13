package main

import (
	"fmt"
	"strings"
)

func source(out chan<- string) {
	// 生成一些字符串数据
	data := []string{"apple", "banana", "cherry", "date", "elderberry", "fig", "grape"}

	// 将数据输出到管道中
	for _, s := range data {
		out <- s
	}
	close(out)
}

func uppercase(in <-chan string, out chan<- string) {
	// 从输入管道中读取数据，并将其转换成大写字母后输出到输出管道中
	for s := range in {
		out <- strings.ToUpper(s)
	}
	close(out)
}

func sink(in <-chan string, end chan struct{}) {
	// 从输入管道中读取数据，并将其输出到标准输出中
	for s := range in {
		fmt.Println(s)
	}
	close(end)
}

func main() {
	// 创建三个管道
	sourceChan := make(chan string)    // 可以设置缓冲区大小 sourceChan := make(chan string, 3)，写入超过缓冲区大小的数据会阻塞，但是不会被丢弃
	uppercaseChan := make(chan string) // 可以设置缓冲区大小 uppercaseChan := make(chan string, 3)
	sinkChan := make(chan struct{})    // 可以设置缓冲区大小 sinkChan := make(chan struct{}, 3)

	// 启动三个过滤器，分别将它们串联起来
	go source(sourceChan)
	go uppercase(sourceChan, uppercaseChan)
	go sink(uppercaseChan, sinkChan)

	// 等待处理完成
	<-sinkChan
}
