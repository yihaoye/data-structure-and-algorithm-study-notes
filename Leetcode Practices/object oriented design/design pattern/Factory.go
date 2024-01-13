package main

import "fmt"

// Product 产品接口
type Product interface {
	Show()
}

// ConcreteProductA 具体产品 A
type ConcreteProductA struct{}

func (p *ConcreteProductA) Show() {
	fmt.Println("Product A")
}

// ConcreteProductB 具体产品 B
type ConcreteProductB struct{}

func (p *ConcreteProductB) Show() {
	fmt.Println("Product B")
}

// Factory 工厂接口
type Factory interface {
	CreateProduct() Product
}

// ConcreteFactoryA 具体工厂 A
type ConcreteFactoryA struct{}

func (f *ConcreteFactoryA) CreateProduct() Product {
	return &ConcreteProductA{}
}

// ConcreteFactoryB 具体工厂 B
type ConcreteFactoryB struct{}

func (f *ConcreteFactoryB) CreateProduct() Product {
	return &ConcreteProductB{}
}

func main() {
	factoryA := &ConcreteFactoryA{}
	productA := factoryA.CreateProduct()
	productA.Show()

	factoryB := &ConcreteFactoryB{}
	productB := factoryB.CreateProduct()
	productB.Show()
}
