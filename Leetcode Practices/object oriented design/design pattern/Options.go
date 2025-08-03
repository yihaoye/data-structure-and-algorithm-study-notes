package main

import (
	"log"
	"time"
)

// https://golang.cafe/blog/golang-functional-options-pattern.html
// https://dev.to/kittipat1413/understanding-the-options-pattern-in-go-390c

type Server struct { // could also apply to Client
	host    string
	port    int
	timeout time.Duration
	maxConn int
}

type Option func(*Server)

func New(options ...Option) *Server {
	svr := &Server{
		// Basic configs...
	}

	for _, opt := range options {
		opt(svr)
	}
	return svr
}

func (s *Server) Start() error {
	// todo
	return nil
}

func (s *Server) Stop() error {
	// todo
	return nil
}

func WithHost(host string) Option {
	return func(s *Server) {
		s.host = host
	}
}

func WithPort(port int) Option {
	return func(s *Server) {
		s.port = port
	}
}

func WithTimeout(timeout time.Duration) Option {
	return func(s *Server) {
		s.timeout = timeout
	}
}

func WithMaxConn(maxConn int) Option {
	return func(s *Server) {
		s.maxConn = maxConn
	}
}

func main() {
	svr := New(
		WithHost("localhost"),
		WithPort(8080),
		WithTimeout(time.Minute),
		WithMaxConn(120),
	)

	if err := svr.Start(); err != nil {
		log.Fatal(err)
	}
	defer svr.Stop()

	// ...
}
