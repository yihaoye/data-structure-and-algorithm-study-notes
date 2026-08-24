package temporal_app

import (
	"time"

	"go.temporal.io/sdk/workflow"
)

type GreetingWorkflow struct{}

func (w *GreetingWorkflow) Greet(ctx workflow.Context, name string) (string, error) {
	ao := workflow.ActivityOptions{StartToCloseTimeout: 5 * time.Second}
	ctx = workflow.WithActivityOptions(ctx, ao)

	var result string
	err := workflow.ExecuteActivity(ctx, CreateGreeting, name).Get(ctx, &result)
	return result, err
}
