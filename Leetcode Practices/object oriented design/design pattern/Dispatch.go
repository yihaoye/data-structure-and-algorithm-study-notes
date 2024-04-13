package main

import (
	"fmt"
)

type Request struct {
	Type string
	Data interface{}
}

type Handler func(request Request)

var handlers = make(map[string]Handler)

func addHandler(reqType string, handler Handler) {
	handlers[reqType] = handler
}

func dispatch(request Request, handlers map[string]Handler) {
	if handler, ok := handlers[request.Type]; ok {
		handler(request)
	} else {
		fmt.Println("Unknown request type:", request.Type)
	}
}

func main() {
	addHandler("add", func(request Request) {
		fmt.Println("Adding:", request.Data)
	})
	addHandler("subtract", func(request Request) {
		fmt.Println("Subtracting:", request.Data)
	})

	dispatch(Request{Type: "add", Data: 1}, handlers)
	dispatch(Request{Type: "subtract", Data: 2}, handlers)
	dispatch(Request{Type: "unknown", Data: 3}, handlers)
}
