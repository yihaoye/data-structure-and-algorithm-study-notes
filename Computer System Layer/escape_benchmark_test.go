package main

import (
	"testing"
)

/*
go test -bench=. -benchmem escape_benchmark_test.go

预期
返回指针的函数会有明显的内存分配（堆内存字节/操作 B/op 不为 0，堆内存分配次数/操作 allocs/op 不为 0）
返回值的函数通常不会有堆内存分配（B/op 为 0，allocs/op 为 0，因为是堆内存不是栈，所以这里才可能是 0）
返回指针的函数可能会比返回值的函数慢，特别是在大型结构体的情况下

goos: darwin
goarch: amd64
cpu: VirtualApple @ 2.50GHz
BenchmarkValueStruct-8          1000000000               0.3216 ns/op          0 B/op          0 allocs/op
BenchmarkPointerStruct-8        29099667                35.39 ns/op           80 B/op          1 allocs/op
PASS
ok      command-line-arguments  2.228s
*/

/*
go test -gcflags="-m -l" -bench=. -benchmem escape_benchmark_test.go
-l 这里意味着禁止了内联优化，也就是说最差情况下下面的速度还是提升了 8 倍

# command-line-arguments [command-line-arguments.test]
./escape_benchmark_test.go:65:2: moved to heap: p
./escape_benchmark_test.go:76:27: b does not escape
./escape_benchmark_test.go:86:29: b does not escape
# command-line-arguments.test
_testmain.go:45:42: testdeps.TestDeps{} escapes to heap
goos: darwin
goarch: amd64
cpu: VirtualApple @ 2.50GHz
BenchmarkValueStruct-8          253894794                4.669 ns/op           0 B/op          0 allocs/op
BenchmarkPointerStruct-8        29400474                36.94 ns/op           80 B/op          1 allocs/op
PASS
ok      command-line-arguments  3.362s
*/

// 复杂的结构体示例
type Person struct {
	Name    string
	Age     int
	Address string
	Phone   string
	Email   string
}

// NewPersonValue 返回一个 Person 结构体的值
func NewPersonValue() Person {
	p := Person{
		Name:    "John Doe",
		Age:     30,
		Address: "123 Main St, Anytown, AT 12345",
		Phone:   "555-123-4567",
		Email:   "john.doe@example.com",
	}
	return p
}

// NewPersonPointer 返回一个 Person 结构体的指针
func NewPersonPointer() *Person {
	p := Person{
		Name:    "John Doe",
		Age:     30,
		Address: "123 Main St, Anytown, AT 12345",
		Phone:   "555-123-4567",
		Email:   "john.doe@example.com",
	}
	return &p // 这里会导致内存逃逸
}

// BenchmarkValueStruct 测试返回结构体值的函数
func BenchmarkValueStruct(b *testing.B) {
	var p Person
	for i := 0; i < b.N; i++ {
		p = NewPersonValue()
	}
	// 防止编译器优化掉结果
	_ = p
}

// BenchmarkPointerStruct 测试返回结构体指针的函数
func BenchmarkPointerStruct(b *testing.B) {
	var p *Person
	for i := 0; i < b.N; i++ {
		p = NewPersonPointer()
	}
	// 防止编译器优化掉结果
	_ = p
}
