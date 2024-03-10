package main

import (
	"fmt"
	"sync"
)

// Task 表示要执行的任务
type Task struct {
	ID int
}

// worker 表示一个工作者，用于从任务队列中获取任务并执行它们
func worker(id int, taskQueue chan Task, wg *sync.WaitGroup) {
	defer wg.Done()
	for task := range taskQueue {
		fmt.Printf("Worker %d 执行任务 %d\n", id, task.ID)
	}
}

func main() {
	// 创建一个有缓冲的通道，用于存储要执行的任务
	taskQueue := make(chan Task, 10)

	// 创建一个等待组，用于等待所有工作者完成任务
	var wg sync.WaitGroup

	// 创建一组工作者
	for i := 1; i <= 3; i++ {
		wg.Add(1)
		go worker(i, taskQueue, &wg)
	}

	// 添加一些任务到任务队列中
	for i := 1; i <= 10; i++ {
		task := Task{ID: i}
		taskQueue <- task
	}

	// 关闭任务队列，等待所有工作者完成任务
	close(taskQueue)
	wg.Wait()
}
