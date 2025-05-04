package main

import "fmt"

type Logger struct{}

func (l Logger) Log(msg string) {
	fmt.Println("LOG:", msg)
}

type Server struct {
	Logger  // 组合，而非继承
	Address string
}

func main() {
	s := Server{Address: "localhost"}
	s.Log("Server started") // 直接调用组合进来的方法
}
