package main

import "fmt"

// Service 是一个服务接口
type Service interface {
	DoSomething()
}

// ConcreteService 是 Service 接口的具体实现
type ConcreteService struct{}

func (cs *ConcreteService) DoSomething() {
	fmt.Println("Doing something")
}

// Client 是一个客户端，依赖于 Service 接口
type Client struct {
	Service Service
}

func (c *Client) DoClientJob() {
	// 使用注入的服务
	c.Service.DoSomething()
}

func main() {
	// 创建具体的服务实例
	service := &ConcreteService{}

	// 创建客户端并注入服务
	client := &Client{Service: service}

	// 客户端执行操作
	client.DoClientJob()
}
