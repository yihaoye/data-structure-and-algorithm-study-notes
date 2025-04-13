package main

import (
	"flag"
	"fmt"
	"log"
	_ "net/http/pprof"
	"os"
	"os/exec"
	"path/filepath"
	"runtime"
	"runtime/pprof"
	"sync"
	"sync/atomic"
	"time"
)

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

/*
默认 GOGC=100
GOGC 是控制垃圾回收触发的阈值，它是一个百分比。
表示当堆内存增长到上一次 GC 后大小的 100%（即翻倍）时，Go 运行时就会触发下一次 GC。
假设上次 GC 后堆用了 50MB：当堆增长到 50MB + 100% = 100MB，就会触发下一次 GC。

% go run mem_escape.go --type=escape

运行 30 秒结果
Total heap allocations: 7394792
Times of new data: 29546
Number of GC runs: 2

% go run mem_escape.go --type=noescape

运行 30 秒结果
Total heap allocations: 281592
Times of new data: 29535
Number of GC runs: 0

Go 的 GC（垃圾回收）采用的是并发三色标记清除算法，所以 不会长时间 Stop-The-World（STW），但确实每次 GC 都有短暂的 STW 阶段，通常发生在：
标记开始前（STW #1）
清理结束后（STW #2）
根据 官方 Go runtime 团队的数据:
"Typical stop-the-world pause times are well under 1 millisecond, even for large heaps."
所以基本不用担心
主要需关注的是 GC 对 CPU、内存的浪费、不必要地增加常规使用量
*/
func main() {
	qps_ := flag.Int("qps", 1000, "每秒请求数量")
	duration_ := flag.Duration("duration", 30*time.Second, "测试持续时间")
	type_ := flag.String("type", "escape", "处理器类型 (escape/noescape)")
	flag.Parse()

	ticker := time.NewTicker(time.Second)
	defer ticker.Stop()

	var wg sync.WaitGroup
	endTime := time.Now().Add(*duration_)
	var count int64
	var p *Data
	var v Data

	var memStats runtime.MemStats
	runtime.ReadMemStats(&memStats)
	f, err := os.Create("cpu.prof")
	if err != nil {
		log.Fatal(err)
	}
	if err := pprof.StartCPUProfile(f); err != nil {
		log.Fatal(err)
	}
	defer func() {
		runtime.ReadMemStats(&memStats)
		fmt.Println("Total heap allocations:", memStats.TotalAlloc)
		fmt.Println("Times of new data:", count)
		fmt.Println("Number of GC runs:", memStats.NumGC)

		pprof.StopCPUProfile()
		f.Close()

		path, _ := filepath.Abs(f.Name())
		cmd := exec.Command("go", "tool", "pprof", path)
		cmd.Stdout = os.Stdout
		cmd.Stderr = os.Stderr
		cmd.Run()
	}()

	for time.Now().Before(endTime) {
		<-ticker.C
		for i := 0; i < *qps_; i++ {
			wg.Add(1)
			go func() {
				defer wg.Done()
				if *type_ == "escape" {
					p = NewPointer()
					_ = p // 使用结果防止编译器优化掉
				} else {
					v = NewValue()
					_ = v
				}
				atomic.AddInt64(&count, 1)
			}()
		}
	}
}
