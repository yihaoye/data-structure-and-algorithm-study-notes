package main

import (
	"fmt"
	"sync"
	"time"
)

// ObserverFunc 观察者函数类型（也可以是接口，只是会冗长些，视具体应用场景选择）
type ObserverFunc func(data interface{})

// Subject 主题结构体
type Subject struct {
	mu        sync.Mutex
	observers []ObserverFunc
	data      interface{}
}

// RegisterObserver 注册观察者
func (s *Subject) RegisterObserver(observer ObserverFunc) {
	s.mu.Lock()
	defer s.mu.Unlock()
	s.observers = append(s.observers, observer)
}

// NotifyObservers 通知所有观察者
func (s *Subject) NotifyObservers() {
	s.mu.Lock()
	defer s.mu.Unlock()
	for _, observer := range s.observers {
		observer(s.data)
	}
}

// SetData 设置数据并通知观察者
func (s *Subject) SetData(data interface{}) {
	s.mu.Lock()
	defer s.mu.Unlock()
	s.data = data
	s.NotifyObservers()
}

func main() {
	subject := &Subject{}

	// 注册观察者1
	subject.RegisterObserver(func(data interface{}) {
		fmt.Printf("Observer 1 received update: %v\n", data)
	})

	// 注册观察者2
	subject.RegisterObserver(func(data interface{}) {
		fmt.Printf("Observer 2 received update: %v\n", data)
	})

	// 模拟数据变化
	for i := 0; i < 5; i++ {
		time.Sleep(time.Second)
		subject.SetData(i)
	}
}

/* 无 chan 简易版
// Subject 观察者主题
type Subject struct {
	observers []Observer
	mutex     sync.Mutex
}

func (s *Subject) Attach(observer Observer) {
	s.mutex.Lock()
	defer s.mutex.Unlock()
	s.observers = append(s.observers, observer)
}

func (s *Subject) Notify(message string) {
	s.mutex.Lock()
	defer s.mutex.Unlock()
	for _, observer := range s.observers {
		observer.Update(message)
	}
}

// Observer 观察者接口
type Observer interface {
	Update(message string)
}

// ConcreteObserver 具体观察者
type ConcreteObserver struct {
	Name string
}

func (o *ConcreteObserver) Update(message string) {
	fmt.Printf("%s received: %s\n", o.Name, message)
}

func main() {
	subject := &Subject{}
	observer1 := &ConcreteObserver{Name: "Observer 1"}
	observer2 := &ConcreteObserver{Name: "Observer 2"}

	subject.Attach(observer1)
	subject.Attach(observer2)

	subject.Notify("Hello observers!")
}
*/
