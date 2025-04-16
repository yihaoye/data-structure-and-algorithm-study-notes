package main

import (
	"testing"
)

/*
预期
返回指针的函数会有明显的内存分配（堆内存字节/操作 B/op 不为 0，堆内存分配次数/操作 allocs/op 不为 0）
返回值的函数通常不会有堆内存分配（B/op 为 0，allocs/op 为 0，因为是堆内存不是栈，所以这里才可能是 0）
返回指针的函数可能会比返回值的函数慢，特别是在大型结构体的情况下

$ go test -bench=BenchmarkValueStruct -benchmem -cpuprofile=cpu.prof mem_escape_benchmark_test.go
$ go tool pprof cpu.prof
---
goos: darwin
goarch: amd64
cpu: VirtualApple @ 2.50GHz
BenchmarkValueStruct-8          25582422                46.63 ns/op            0 B/op          0 allocs/op
PASS
ok      command-line-arguments  2.110s
File: main.test
Type: cpu
Time: Apr 15, 2025 at 10:42pm (AEST)
Duration: 1.41s, Total samples = 1.14s (80.58%)


$ go test -bench=BenchmarkPointerStruct -benchmem -cpuprofile=cpu.prof mem_escape_benchmark_test.go
$ go tool pprof cpu.prof
---
goos: darwin
goarch: amd64
cpu: VirtualApple @ 2.50GHz
BenchmarkPointerStruct-8         6289774               204.2 ns/op           896 B/op          1 allocs/op
PASS
ok      command-line-arguments  1.994s
File: main.test
Type: cpu
Time: Apr 15, 2025 at 10:42pm (AEST)
Duration: 1.61s, Total samples = 2.08s (129.55%)


以下是并发场景基准测试
$ go test -bench=BenchmarkConcurrentValueStruct -benchmem -cpuprofile=cpu.prof mem_escape_benchmark_test.go
$ go tool pprof cpu.prof
---
goos: darwin
goarch: amd64
cpu: VirtualApple @ 2.50GHz
BenchmarkConcurrentValueStruct-8        142114825               10.53 ns/op            0 B/op          0 allocs/op
PASS
ok      command-line-arguments  3.193s
File: main.test
Type: cpu
Time: Apr 16, 2025 at 3:38pm (AEST)
Duration: 2.51s, Total samples = 11s (438.56%)


$ go test -bench=BenchmarkConcurrentPointerStruct -benchmem -cpuprofile=cpu.prof mem_escape_benchmark_test.go
$ go tool pprof cpu.prof
---
goos: darwin
goarch: amd64
cpu: VirtualApple @ 2.50GHz
BenchmarkConcurrentPointerStruct-8       4003012               301.7 ns/op           896 B/op          1 allocs/op
PASS
ok      command-line-arguments  2.376s
File: main.test
Type: cpu
Time: Apr 16, 2025 at 3:39pm (AEST)
Duration: 1.71s, Total samples = 5.08s (296.65%)
注意: ns/op 相比单线程版本 反而增加了 (从 ~204ns 增加到 ~301ns)。B/op 和 allocs/op 保持不变，说明 单次操作 的内存分配行为没变。但 时间成本 却增加了。这强烈暗示了 并发冲突。多个 goroutine 同时请求在堆上分配内存 (NewPointer 内部)，会竞争内存分配器的锁，并且增加了垃圾收集器 (GC) 的压力和工作量（需要扫描和回收更多堆对象），导致整体效率下降，单次操作的平均耗时增加。


非逃逸版本 (ConcurrentValueStruct) 的 pprof CPU 使用率（Total samples 百分比）高于逃逸版本 (ConcurrentPointerStruct)
主要是因为它在测试期间执行了远超逃逸版本的操作次数 (b.N)

ConcurrentValueStruct (b.N ≈ 1.42 亿次) 在约 3.19 秒内完成了巨量操作，CPU 在这段时间自然高度繁忙，累积的 CPU 时间（Total samples = 11s）就多。
ConcurrentPointerStruct (b.N ≈ 400 万次) 在约 2.38 秒内完成的操作少得多，虽然单次操作更耗时且有并发冲突，但总的 CPU 累积时间（Total samples = 5.08s）反而可能更少。
直接比较 pprof 输出头部的总 CPU 时间百分比（如 438.56% vs 296.65%）来判断哪个“CPU 使用率高”是没有意义的，因为它没有考虑效率，即完成单位操作所需的 CPU 资源。

如何公平地对比并发基准测试的 CPU 使用效率？
推荐】直接使用 ns/op (纳秒/操作):
这是 go test -bench 提供的核心指标，代表完成单次操作平均所需的墙上时间 (wall-clock time)。它已经隐式包含了 CPU 计算时间、内存分配/GC 开销、锁等待等所有因素。
公平性: ns/op 直接反映了哪个版本能更快地完成一个操作。数值越低，效率越高，吞吐量越大。
数据:
ConcurrentValueStruct: 10.53 ns/op
ConcurrentPointerStruct: 301.7 ns/op
结论: 值传递版本完成单次操作的平均时间远低于指针传递版本（快约 28 倍），这直接表明了其在 CPU 资源利用（以及内存管理效率）上的巨大优势。这是最常用且足够说明问题的对比方式。
*/

/*
go test -gcflags="-m -l" -bench=. -benchmem mem_escape_benchmark_test.go
-l 这里意味着禁止了内联优化，也就是说最差情况下下面的速度还是提升了 4 倍

# command-line-arguments [command-line-arguments.test]
./mem_escape_benchmark_test.go:77:2: moved to heap: d
./mem_escape_benchmark_test.go:129:27: b does not escape
./mem_escape_benchmark_test.go:139:29: b does not escape
# command-line-arguments.test
_testmain.go:45:42: testdeps.TestDeps{} escapes to heap
goos: darwin
goarch: amd64
cpu: VirtualApple @ 2.50GHz
BenchmarkValueStruct-8          22627419                46.95 ns/op            0 B/op          0 allocs/op
BenchmarkPointerStruct-8         7246881               187.3 ns/op           896 B/op          1 allocs/op
PASS
ok      command-line-arguments  3.263s
*/

// 其他测评 https://huizhou92.com/p/go-high-performance-programming-ep9-escape-analysis/

// 复杂的结构体示例
type Data struct {
	F0, F1, F2, F3, F4, F5, F6, F7, F8, F9           float64
	F10, F11, F12, F13, F14, F15, F16, F17, F18, F19 float64
	F20, F21, F22, F23, F24, F25, F26, F27, F28, F29 float64
	F30, F31, F32, F33, F34, F35, F36, F37, F38, F39 float64
	F40, F41, F42, F43, F44, F45, F46, F47, F48, F49 float64
	F50, F51, F52, F53, F54, F55, F56, F57, F58, F59 float64
	F60, F61, F62, F63, F64, F65, F66, F67, F68, F69 float64
	F70, F71, F72, F73, F74, F75, F76, F77, F78, F79 float64
	F80, F81, F82, F83, F84, F85, F86, F87, F88, F89 float64
	F90, F91, F92, F93, F94, F95, F96, F97, F98, F99 float64
}

func NewPointer() *Data {
	d := Data{
		F0: 3.14, F1: 3.14, F2: 3.14, F3: 3.14, F4: 3.14,
		F5: 3.14, F6: 3.14, F7: 3.14, F8: 3.14, F9: 3.14,
		F10: 3.14, F11: 3.14, F12: 3.14, F13: 3.14, F14: 3.14,
		F15: 3.14, F16: 3.14, F17: 3.14, F18: 3.14, F19: 3.14,
		F20: 3.14, F21: 3.14, F22: 3.14, F23: 3.14, F24: 3.14,
		F25: 3.14, F26: 3.14, F27: 3.14, F28: 3.14, F29: 3.14,
		F30: 3.14, F31: 3.14, F32: 3.14, F33: 3.14, F34: 3.14,
		F35: 3.14, F36: 3.14, F37: 3.14, F38: 3.14, F39: 3.14,
		F40: 3.14, F41: 3.14, F42: 3.14, F43: 3.14, F44: 3.14,
		F45: 3.14, F46: 3.14, F47: 3.14, F48: 3.14, F49: 3.14,
		F50: 3.14, F51: 3.14, F52: 3.14, F53: 3.14, F54: 3.14,
		F55: 3.14, F56: 3.14, F57: 3.14, F58: 3.14, F59: 3.14,
		F60: 3.14, F61: 3.14, F62: 3.14, F63: 3.14, F64: 3.14,
		F65: 3.14, F66: 3.14, F67: 3.14, F68: 3.14, F69: 3.14,
		F70: 3.14, F71: 3.14, F72: 3.14, F73: 3.14, F74: 3.14,
		F75: 3.14, F76: 3.14, F77: 3.14, F78: 3.14, F79: 3.14,
		F80: 3.14, F81: 3.14, F82: 3.14, F83: 3.14, F84: 3.14,
		F85: 3.14, F86: 3.14, F87: 3.14, F88: 3.14, F89: 3.14,
		F90: 3.14, F91: 3.14, F92: 3.14, F93: 3.14, F94: 3.14,
		F95: 3.14, F96: 3.14, F97: 3.14, F98: 3.14, F99: 3.14,
	}
	return &d // 会逃逸
}

func NewValue() Data {
	d := Data{
		F0: 3.14, F1: 3.14, F2: 3.14, F3: 3.14, F4: 3.14,
		F5: 3.14, F6: 3.14, F7: 3.14, F8: 3.14, F9: 3.14,
		F10: 3.14, F11: 3.14, F12: 3.14, F13: 3.14, F14: 3.14,
		F15: 3.14, F16: 3.14, F17: 3.14, F18: 3.14, F19: 3.14,
		F20: 3.14, F21: 3.14, F22: 3.14, F23: 3.14, F24: 3.14,
		F25: 3.14, F26: 3.14, F27: 3.14, F28: 3.14, F29: 3.14,
		F30: 3.14, F31: 3.14, F32: 3.14, F33: 3.14, F34: 3.14,
		F35: 3.14, F36: 3.14, F37: 3.14, F38: 3.14, F39: 3.14,
		F40: 3.14, F41: 3.14, F42: 3.14, F43: 3.14, F44: 3.14,
		F45: 3.14, F46: 3.14, F47: 3.14, F48: 3.14, F49: 3.14,
		F50: 3.14, F51: 3.14, F52: 3.14, F53: 3.14, F54: 3.14,
		F55: 3.14, F56: 3.14, F57: 3.14, F58: 3.14, F59: 3.14,
		F60: 3.14, F61: 3.14, F62: 3.14, F63: 3.14, F64: 3.14,
		F65: 3.14, F66: 3.14, F67: 3.14, F68: 3.14, F69: 3.14,
		F70: 3.14, F71: 3.14, F72: 3.14, F73: 3.14, F74: 3.14,
		F75: 3.14, F76: 3.14, F77: 3.14, F78: 3.14, F79: 3.14,
		F80: 3.14, F81: 3.14, F82: 3.14, F83: 3.14, F84: 3.14,
		F85: 3.14, F86: 3.14, F87: 3.14, F88: 3.14, F89: 3.14,
		F90: 3.14, F91: 3.14, F92: 3.14, F93: 3.14, F94: 3.14,
		F95: 3.14, F96: 3.14, F97: 3.14, F98: 3.14, F99: 3.14,
	}
	return d // 不会逃逸
}

// BenchmarkValueStruct 测试返回结构体值的函数
func BenchmarkValueStruct(b *testing.B) {
	var v Data
	for i := 0; i < b.N; i++ {
		v = NewValue()
	}
	// 防止编译器优化掉结果
	_ = v
}

// BenchmarkPointerStruct 测试返回结构体指针的函数
func BenchmarkPointerStruct(b *testing.B) {
	var p *Data
	for i := 0; i < b.N; i++ {
		p = NewPointer()
	}
	// 防止编译器优化掉结果
	_ = p
}

// 并发版本：多个 goroutine 运行返回值函数
func BenchmarkConcurrentValueStruct(b *testing.B) {
	b.SetParallelism(4) // 默认 GOMAXPROCS
	b.RunParallel(func(pb *testing.PB) {
		var v Data
		for pb.Next() {
			v = NewValue()
		}
		_ = v
	})
}

// 并发版本：多个 goroutine 运行返回指针函数
func BenchmarkConcurrentPointerStruct(b *testing.B) {
	b.SetParallelism(4)
	b.RunParallel(func(pb *testing.PB) {
		var p *Data
		for pb.Next() {
			p = NewPointer()
		}
		_ = p
	})
}
