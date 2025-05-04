package main

import "fmt"

// Adaptee 被适配的接口
type Adaptee interface {
	SpecificRequest() string
}

// AdapteeImpl Adaptee 的实现
type AdapteeImpl struct {
}

func (a *AdapteeImpl) SpecificRequest() string {
	return "Adaptee's specific request"
}

// Target 接口
type Target interface {
	Request() string
}

// Adapter 适配器 - Target 的实现
type Adapter struct {
	Adaptee
}

func (a *Adapter) Request() string {
	return a.SpecificRequest()
}

func main() {
	adaptee := &AdapteeImpl{}
	adapter := &Adapter{Adaptee: adaptee}

	client := func(target Target) {
		fmt.Println(target.Request())
	}

	// 使用适配器
	client(adapter)
}
