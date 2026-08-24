package main

import (
	"log"

	internal "temporal_app/internal"

	"go.temporal.io/sdk/client"
	"go.temporal.io/sdk/worker"
)

func main() {
	c, err := client.Dial(client.Options{})
	if err != nil {
		log.Fatalln("Unable to create Temporal client", err)
	}
	defer c.Close()

	w := worker.New(c, internal.GreetingTaskQueue, worker.Options{})
	w.RegisterWorkflow((&internal.GreetingWorkflow{}).Greet)
	w.RegisterActivity(internal.CreateGreeting)

	if err := w.Run(worker.InterruptCh()); err != nil {
		log.Fatalln("Unable to start worker", err)
	}
}
