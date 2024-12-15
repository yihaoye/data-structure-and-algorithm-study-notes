// https://refactoringguru.cn/design-patterns/strategy/go/example
package main

import "fmt"

/* 函数闭包 */
type Strategy func()

func UseStrategy(strategy Strategy) {
	strategy()
}

func StrategyA() {
	fmt.Println("Executing Strategy A")
}

func StrategyB() {
	fmt.Println("Executing Strategy B")
}

func main() {
	UseStrategy(StrategyA)
	UseStrategy(StrategyB)
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
