package main

import "fmt"

type Person struct {
	Name string
	Age  int
}

func NewPerson() *Person {
	return &Person{}
}

func (p *Person) SetName(name string) *Person {
	p.Name = name
	return p
}

func (p *Person) SetAge(age int) *Person {
	p.Age = age
	return p
}

func main() {
	// 使用 builder 构建对象
	p := NewPerson().SetName("Alice").SetAge(30)
	fmt.Printf("%+v\n", p)
}
