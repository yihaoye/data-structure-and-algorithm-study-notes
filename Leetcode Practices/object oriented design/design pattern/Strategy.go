// https://refactoringguru.cn/design-patterns/strategy/go/example
package main

import (
	"errors"
	"fmt"
)

/* 函数闭包 */
var (
	StrategyA = func(text string) (string, error) {
		fmt.Println("Executing Strategy A")
		return text, nil
	}

	StrategyB = func(text string) (string, error) {
		fmt.Println("Executing Strategy B")
		return "", nil
	}
)

type Entity struct {
	strategy func(string) (string, error)
}

func (en *Entity) Execute() {
	if en.strategy == nil {
		panic(errors.New("strategy is nil"))
	}
	text, _ := en.strategy("test")
	fmt.Println("Result: " + text)
}

func main() {
	en := Entity{StrategyA}
	en.Execute()

	en.strategy = StrategyB
	en.Execute()
}

/* 使用接口组合 */
/*
type Strategy interface {
	Execute()
}

type ConcreteStrategyA struct{}

func (s *ConcreteStrategyA) Execute() {
	fmt.Println("Executing Strategy A")
}

type ConcreteStrategyB struct{}

func (s *ConcreteStrategyB) Execute() {
	fmt.Println("Executing Strategy B")
}

type Context struct {
	strategy Strategy
}

func (c *Context) SetStrategy(strategy Strategy) {
	c.strategy = strategy
}

func (c *Context) ExecuteStrategy() {
	c.strategy.Execute()
}

func main() {
	strategyA := &ConcreteStrategyA{}
	strategyB := &ConcreteStrategyB{}

	context := &Context{}

	context.SetStrategy(strategyA)
	context.ExecuteStrategy()

	context.SetStrategy(strategyB)
	context.ExecuteStrategy()
}
*/
