package main

import (
	"fmt"
	"sync"
)

type Singleton struct {
	Data string
}

var instance *Singleton
var once sync.Once

func GetInstance() *Singleton {
	once.Do(func() {
		instance = &Singleton{"Singleton Instance"}
	})
	return instance
}

func main() {
	instance1 := GetInstance()
	instance2 := GetInstance()

	fmt.Println(instance1 == instance2) // true
}
