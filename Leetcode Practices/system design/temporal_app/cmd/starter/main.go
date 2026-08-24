package main

import (
	"context"
	"fmt"
	"log"

	internal "temporal_app/internal"

	"go.temporal.io/sdk/client"
)

func main() {
	// Starter 使用 Temporal Client 发起一次 Workflow Execution。
	c, err := client.Dial(client.Options{})
	if err != nil {
		log.Fatalln("Unable to create Temporal client", err)
	}
	defer c.Close()

	options := client.StartWorkflowOptions{
		ID:        "greeting-workflow",
		TaskQueue: internal.GreetingTaskQueue,
	}

	// 监听该 Task Queue 的 Worker 会真正执行 Workflow 代码。
	run, err := c.ExecuteWorkflow(context.Background(), options, (&internal.GreetingWorkflow{}).Greet, "Temporal")
	if err != nil {
		log.Fatalln("Unable to execute workflow", err)
	}

	var result string
	if err := run.Get(context.Background(), &result); err != nil {
		log.Fatalln("Unable to get workflow result", err)
	}

	fmt.Println(result)
}
