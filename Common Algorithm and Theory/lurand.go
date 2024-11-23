package lurand

import (
	"math/rand"
	"sync"
	"time"
)

// 支持高并发的高性能大规模唯一随机数生成器

const defaultMax = 1_000_000 // 默认 100 万

// 使用一个 map[int]int 实现大规模唯一随机数生成，比如一开始随机 0-1000000 之间的数，
// 假设先随机到 999，如果 map 没这个数的键，则返回这个数，同时把当前最后一个可用的数假设 1000000 存入这个键值，
// 即 <999:1000000>，那么下次随机数应取 0-999999（max 自减一）。
// 如此类推，如果下次随机数的键存在则返回其值并同样从最后拿一个数覆写值，且 max 自减一
// 类似 Fisher–Yates Shuffle 洗牌算法
type LUR struct {
	mapping map[int]int // 用于映射随机数到当前可用数
	max     int         // 当前随机数的最大范围
	rnd     *rand.Rand
	mu      sync.Mutex
}

// New 初始化时间复杂度 O(1)
func New() *LUR {
	return &LUR{
		mapping: make(map[int]int),
		max:     defaultMax,
		rnd:     rand.New(rand.NewSource(time.Now().UnixNano())),
	}
}

func New_(max int) *LUR {
	return &LUR{
		mapping: make(map[int]int),
		max:     max,
		rnd:     rand.New(rand.NewSource(time.Now().UnixNano())),
	}
}

// Intn 时间复杂度 O(1)，空间复杂度 O(N)
func (r *LUR) Intn() int {
	r.mu.Lock()
	defer r.mu.Unlock()

	if r.max <= 0 {
		panic("No more numbers available")
	}
	delete(r.mapping, r.max) // 优化空间利用

	key := r.rnd.Intn(r.max)
	val, ok1 := r.mapping[key]
	rep, ok2 := r.mapping[r.max-1] // replace
	if !ok2 {
		rep = r.max - 1
	}
	r.mapping[key] = rep
	r.max--
	if !ok1 {
		val = key
	}
	return val
}
