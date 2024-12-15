// https://refactoringguru.cn/design-patterns/strategy/go/example
package main

import "fmt"

/* 函数闭包 */
type Strategy func()

func NewStrategyA() Strategy {
	return func() {
		fmt.Println("Executing Strategy A")
	}
}

func NewStrategyB() Strategy {
	return func() {
		fmt.Println("Executing Strategy B")
	}
}

type MyStruct struct {
	strategy Strategy
}

func (s *MyStruct) Execute() {
	if s.strategy == nil {
		// log error
		return
	}
	s.strategy()
}

func main() {
	s := &MyStruct{
		strategy: NewStrategyA(),
	}
	s.Execute()

	s.strategy = NewStrategyB()
	s.Execute()
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
