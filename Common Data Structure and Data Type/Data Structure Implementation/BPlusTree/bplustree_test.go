package bplustree

import (
	"fmt"
	"math/rand"
	"slices"
	"testing"
)

// check 检查 B+ 树的所有不变量：键数上下限、键有序且在分隔键范围内、
// 所有叶子同一层、叶子链表按顺序覆盖全部键。
func (t *Tree[K, V]) check(tb testing.TB) {
	tb.Helper()
	if t.root == nil {
		if t.size != 0 {
			tb.Fatalf("空树但 size = %d", t.size)
		}
		return
	}
	leafDepth := -1
	var leaves []*node[K, V]
	var walk func(n *node[K, V], lo, hi *K, depth int)
	walk = func(n *node[K, V], lo, hi *K, depth int) {
		isRoot := n == t.root
		if len(n.keys) > t.maxKeys {
			tb.Fatalf("节点溢出：%v", n.keys)
		}
		if !slices.IsSorted(n.keys) {
			tb.Fatalf("节点内键无序：%v", n.keys)
		}
		for _, k := range n.keys {
			if (lo != nil && k < *lo) || (hi != nil && k >= *hi) {
				tb.Fatalf("键 %v 超出分隔范围", k)
			}
		}
		if n.leaf {
			if !isRoot && len(n.keys) < t.minLeaf {
				tb.Fatalf("叶子下溢：%v", n.keys)
			}
			if len(n.values) != len(n.keys) {
				tb.Fatalf("叶子键值数量不一致")
			}
			if leafDepth == -1 {
				leafDepth = depth
			} else if leafDepth != depth {
				tb.Fatalf("叶子不在同一层")
			}
			leaves = append(leaves, n)
			return
		}
		if len(n.keys) == 0 || (!isRoot && len(n.keys) < t.minInternal) {
			tb.Fatalf("内部节点下溢：%v", n.keys)
		}
		if len(n.children) != len(n.keys)+1 {
			tb.Fatalf("孩子数应为键数加一")
		}
		for i, c := range n.children {
			clo, chi := lo, hi
			if i > 0 {
				clo = &n.keys[i-1]
			}
			if i < len(n.keys) {
				chi = &n.keys[i]
			}
			walk(c, clo, chi, depth+1)
		}
	}
	walk(t.root, nil, nil, 0)

	// 叶子链表必须和中序遍历得到的叶子顺序一致。
	count := 0
	for i, l := range leaves {
		count += len(l.keys)
		var want *node[K, V]
		if i+1 < len(leaves) {
			want = leaves[i+1]
		}
		if l.next != want {
			tb.Fatalf("叶子链表断了")
		}
	}
	if count != t.size {
		tb.Fatalf("叶子里有 %d 个键，size = %d", count, t.size)
	}
}

// 随机插入、删除，和 map 对照结果，每一步都检查不变量。
func TestRandomAgainstMap(t *testing.T) {
	for _, order := range []int{3, 4, 5, 8, 32} {
		t.Run(fmt.Sprintf("order=%d", order), func(t *testing.T) {
			r := rand.New(rand.NewSource(int64(order)))
			tr := New[int, int](order)
			ref := map[int]int{}
			for step := 0; step < 20000; step++ {
				k := r.Intn(500)
				switch r.Intn(3) {
				case 0, 1:
					_, existed := ref[k]
					if got := tr.Put(k, step); got != existed {
						t.Fatalf("Put(%d) replaced = %v，期望 %v", k, got, existed)
					}
					ref[k] = step
				case 2:
					_, existed := ref[k]
					if got := tr.Delete(k); got != existed {
						t.Fatalf("Delete(%d) = %v，期望 %v", k, got, existed)
					}
					delete(ref, k)
				}
				tr.check(t)
				if tr.Len() != len(ref) {
					t.Fatalf("Len = %d，期望 %d", tr.Len(), len(ref))
				}
			}
			for k, v := range ref {
				if got, ok := tr.Get(k); !ok || got != v {
					t.Fatalf("Get(%d) = %v, %v，期望 %v", k, got, ok, v)
				}
			}
			// 范围查询和 map 对照。
			var got, want []int
			tr.Range(100, 300, func(k, _ int) bool { got = append(got, k); return true })
			for k := range ref {
				if k >= 100 && k < 300 {
					want = append(want, k)
				}
			}
			slices.Sort(want)
			if !slices.Equal(got, want) {
				t.Fatalf("Range 结果不对")
			}
			// 全部删光。
			for k := range ref {
				tr.Delete(k)
				tr.check(t)
			}
			if tr.Len() != 0 || tr.Height() != 0 {
				t.Fatalf("删光后不为空")
			}
		})
	}
}

func ExampleTree() {
	tr := New[int, string](4)
	for _, k := range []int{10, 20, 30, 40, 50, 60, 70, 80, 90, 25, 15} {
		tr.Put(k, fmt.Sprintf("v%d", k))
	}
	fmt.Println("len:", tr.Len(), "height:", tr.Height())

	v, ok := tr.Get(25)
	fmt.Println("get 25:", v, ok)

	var keys []int
	tr.Range(20, 60, func(k int, v string) bool {
		keys = append(keys, k)
		return true
	})
	fmt.Println(keys)

	for _, k := range []int{90, 80, 70, 25, 30, 60} {
		tr.Delete(k)
	}
	fmt.Println("len:", tr.Len(), "height:", tr.Height())

	// Output:
	// len: 11 height: 3
	// get 25: v25 true
	// [20 25 30 40 50]
	// len: 5 height: 2
}
