package main

import (
	"time"
)

type Server struct {
	host string
	port int
}

type CallConfigs struct {
	timeout time.Duration
	// more...
}

type CallOption struct {
	f func(c *CallConfigs)
}

func NewServer(host string, port int) *Server {
	return &Server{
		host: host,
		port: port,
	}
}

func (s *Server) Call(options ...CallOption) error {
	c := &CallConfigs{}
	for _, opt := range options {
		opt.f(c)
	}
	// for example: use c.timeout...
	return nil
}

func WithTimeout(timeout time.Duration) CallOption {
	return CallOption{func(c *CallConfigs) {
		c.timeout = timeout
	}}
}

type Client struct {
	svr *Server
}

func NewClient() *Client {
	return &Client{
		svr: NewServer("localhost", 8080),
	}
}

func (c *Client) Send(options ...CallOption) error {
	c.svr.Call(options...)
	return nil
}

func main() {
	cli := NewClient()
	cli.Send(
		WithTimeout(time.Second),
	)
}
