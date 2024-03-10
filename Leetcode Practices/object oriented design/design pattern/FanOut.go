package main

import (
	"fmt"
	"sync"
	"time"
)

var wg sync.WaitGroup

func worker(id int, jobs <-chan int, results chan<- int) {
	defer wg.Done()
	for j := range jobs {
		fmt.Println("worker", id, "processing job", j)
		time.Sleep(time.Second)
		results <- j * 2
	}
}

func main() {
	// 创建两个 channel：一个用于发送任务，一个用于接收结果
	jobs := make(chan int, 5)
	results := make(chan int, 5)

	// 启动三个 worker
	wg.Add(3)
	for w := 1; w <= 3; w++ {
		go worker(w, jobs, results)
	}

	go func() {
		wg.Wait()
		close(results)
	}()

	// 发送五个任务
	for j := 1; j <= 5; j++ {
		jobs <- j
	}
	close(jobs)

	// 输出结果
	for a := 1; a <= 5; a++ {
		<-results
	}
}
