// Package bplustree 是一个内存中的 B+ 树示例实现，用来学习原理。By Claude
//
// 规则（阶数 order = m，表示一个节点最多有 m 个孩子）：
//   - 内部节点：最多 m-1 个键、m 个孩子；非根节点至少 ⌈m/2⌉-1 个键。
//     内部节点只存键，用来指路，不存值。
//   - 叶子节点：最多 m-1 个键值对；非根叶子至少 ⌈(m-1)/2⌉ 个。
//     所有值都存在叶子里，叶子之间用 next 指针串成有序链表。
//   - 分隔键约定：内部节点第 i 个键 keys[i] 满足
//     children[i] 里的键 < keys[i] <= children[i+1] 里的键。
//
// 这个实现不是并发安全的，也没有做持久化，重点是把插入、删除时的
// 分裂、借键、合并写清楚。
package bplustree

import (
	"cmp"
	"slices"
	"sort"
)

// node 同时表示内部节点和叶子节点，用 leaf 区分。
type node[K cmp.Ordered, V any] struct {
	leaf     bool
	keys     []K
	children []*node[K, V] // 只有内部节点使用，len(children) == len(keys)+1
	values   []V           // 只有叶子使用，len(values) == len(keys)
	next     *node[K, V]   // 只有叶子使用，指向右边的兄弟叶子
}

// Tree 是一棵 B+ 树。零值不可用，请用 New 创建。
type Tree[K cmp.Ordered, V any] struct {
	root        *node[K, V]
	size        int
	maxKeys     int // m-1
	minInternal int // ⌈m/2⌉-1
	minLeaf     int // ⌈(m-1)/2⌉
}

// New 创建一棵阶数为 order 的 B+ 树，order 至少为 3。
// 真实场景里 order 通常取几十到几百；学习时可以用 3 或 4 方便观察分裂。
func New[K cmp.Ordered, V any](order int) *Tree[K, V] {
	if order < 3 {
		panic("bplustree: order must be >= 3")
	}
	return &Tree[K, V]{
		maxKeys:     order - 1,
		minInternal: (order+1)/2 - 1,
		minLeaf:     order / 2, // 等于 ⌈(m-1)/2⌉
	}
}

// Len 返回键值对的数量。
func (t *Tree[K, V]) Len() int { return t.size }

// childIndex 返回在内部节点 n 中查找 k 时应该走的孩子下标：
// 即 keys 中 <= k 的键的个数。
func childIndex[K cmp.Ordered, V any](n *node[K, V], k K) int {
	return sort.Search(len(n.keys), func(i int) bool { return k < n.keys[i] })
}

// pathEntry 记录从根往下走时经过的内部节点，以及走的是第几个孩子。
// 插入和删除需要沿着这条路径往上回溯，做分裂或再平衡。
type pathEntry[K cmp.Ordered, V any] struct {
	n   *node[K, V]
	idx int
}

// findLeaf 从根走到 k 所在的叶子，同时返回经过的路径。
func (t *Tree[K, V]) findLeaf(k K) (*node[K, V], []pathEntry[K, V]) {
	var path []pathEntry[K, V]
	n := t.root
	for !n.leaf {
		i := childIndex(n, k)
		path = append(path, pathEntry[K, V]{n, i})
		n = n.children[i]
	}
	return n, path
}

// Get 查找 k。无论 k 在哪，都要一直走到叶子，所以每次查找的路径长度相同。
func (t *Tree[K, V]) Get(k K) (V, bool) {
	var zero V
	if t.root == nil {
		return zero, false
	}
	leaf, _ := t.findLeaf(k)
	if i, ok := slices.BinarySearch(leaf.keys, k); ok {
		return leaf.values[i], true
	}
	return zero, false
}

// Put 插入或更新 k。如果 k 已存在，更新它的值并返回 true。
func (t *Tree[K, V]) Put(k K, v V) (replaced bool) {
	if t.root == nil {
		t.root = &node[K, V]{leaf: true, keys: []K{k}, values: []V{v}}
		t.size = 1
		return false
	}

	leaf, path := t.findLeaf(k)
	i, found := slices.BinarySearch(leaf.keys, k)
	if found {
		leaf.values[i] = v
		return true
	}
	leaf.keys = slices.Insert(leaf.keys, i, k)
	leaf.values = slices.Insert(leaf.values, i, v)
	t.size++

	// 沿路径向上处理溢出：每次分裂会往父节点插入一个分隔键，
	// 父节点可能因此也溢出，于是继续向上。
	n := leaf
	for len(n.keys) > t.maxKeys {
		var sep K
		var right *node[K, V]
		if n.leaf {
			sep, right = t.splitLeaf(n)
		} else {
			sep, right = t.splitInternal(n)
		}

		if len(path) == 0 {
			// 分裂到了根：新建一个根，树长高一层。
			t.root = &node[K, V]{
				keys:     []K{sep},
				children: []*node[K, V]{n, right},
			}
			return false
		}

		p := path[len(path)-1]
		path = path[:len(path)-1]
		p.n.keys = slices.Insert(p.n.keys, p.idx, sep)
		p.n.children = slices.Insert(p.n.children, p.idx+1, right)
		n = p.n
	}
	return false
}

// splitLeaf 把溢出的叶子分成两半。
// 右半边的第一个键被"复制"到父节点作为分隔键，叶子里仍然保留它。
func (t *Tree[K, V]) splitLeaf(n *node[K, V]) (K, *node[K, V]) {
	mid := (len(n.keys) + 1) / 2 // 左边多放一个
	right := &node[K, V]{
		leaf:   true,
		keys:   slices.Clone(n.keys[mid:]),
		values: slices.Clone(n.values[mid:]),
		next:   n.next,
	}
	n.keys = slices.Clip(n.keys[:mid])
	n.values = slices.Clip(n.values[:mid])
	n.next = right // 维护叶子链表
	return right.keys[0], right
}

// splitInternal 把溢出的内部节点分成两半。
// 中间的键被"移动"到父节点，左右两边都不保留它。
func (t *Tree[K, V]) splitInternal(n *node[K, V]) (K, *node[K, V]) {
	mid := len(n.keys) / 2
	sep := n.keys[mid]
	right := &node[K, V]{
		keys:     slices.Clone(n.keys[mid+1:]),
		children: slices.Clone(n.children[mid+1:]),
	}
	n.keys = slices.Clip(n.keys[:mid])
	n.children = slices.Clip(n.children[:mid+1])
	return sep, right
}

// Delete 删除 k，返回 k 是否存在。
func (t *Tree[K, V]) Delete(k K) bool {
	if t.root == nil {
		return false
	}
	leaf, path := t.findLeaf(k)
	i, found := slices.BinarySearch(leaf.keys, k)
	if !found {
		return false
	}
	leaf.keys = slices.Delete(leaf.keys, i, i+1)
	leaf.values = slices.Delete(leaf.values, i, i+1)
	t.size--

	// 注意：如果删掉的是叶子的第一个键，父节点里可能还留着它作为分隔键。
	// 这不影响正确性（右子树的键仍然都 >= 这个分隔键），所以不必更新。

	n := leaf
	for {
		if n == t.root {
			if n.leaf && len(n.keys) == 0 {
				t.root = nil // 树空了
			} else if !n.leaf && len(n.keys) == 0 {
				t.root = n.children[0] // 根只剩一个孩子，树高减一
			}
			return true
		}

		min := t.minInternal
		if n.leaf {
			min = t.minLeaf
		}
		if len(n.keys) >= min {
			return true // 没有下溢，结束
		}

		p := path[len(path)-1]
		path = path[:len(path)-1]
		if n.leaf {
			t.rebalanceLeaf(p.n, p.idx)
		} else {
			t.rebalanceInternal(p.n, p.idx)
		}
		n = p.n // 合并会让父节点少一个键，父节点可能也下溢，继续向上检查
	}
}

// rebalanceLeaf 处理 parent.children[i] 这个叶子的下溢。
// 优先向兄弟借，借不到就合并。
func (t *Tree[K, V]) rebalanceLeaf(parent *node[K, V], i int) {
	n := parent.children[i]

	// 1. 左兄弟有富余：借它的最后一个键值对。
	if i > 0 {
		left := parent.children[i-1]
		if len(left.keys) > t.minLeaf {
			last := len(left.keys) - 1
			n.keys = slices.Insert(n.keys, 0, left.keys[last])
			n.values = slices.Insert(n.values, 0, left.values[last])
			left.keys = left.keys[:last]
			left.values = left.values[:last]
			parent.keys[i-1] = n.keys[0] // 分隔键变成 n 的新最小键
			return
		}
	}

	// 2. 右兄弟有富余：借它的第一个键值对。
	if i < len(parent.children)-1 {
		right := parent.children[i+1]
		if len(right.keys) > t.minLeaf {
			n.keys = append(n.keys, right.keys[0])
			n.values = append(n.values, right.values[0])
			right.keys = slices.Delete(right.keys, 0, 1)
			right.values = slices.Delete(right.values, 0, 1)
			parent.keys[i] = right.keys[0] // 分隔键变成右兄弟的新最小键
			return
		}
	}

	// 3. 都借不到：和一个兄弟合并，并删掉父节点里它们之间的分隔键。
	if i > 0 {
		t.mergeLeaves(parent, i-1) // n 并入左兄弟
	} else {
		t.mergeLeaves(parent, i) // 右兄弟并入 n
	}
}

// mergeLeaves 把 parent.children[i+1] 合并进 parent.children[i]。
func (t *Tree[K, V]) mergeLeaves(parent *node[K, V], i int) {
	left, right := parent.children[i], parent.children[i+1]
	left.keys = append(left.keys, right.keys...)
	left.values = append(left.values, right.values...)
	left.next = right.next // 把 right 从叶子链表里摘掉
	parent.keys = slices.Delete(parent.keys, i, i+1)
	parent.children = slices.Delete(parent.children, i+1, i+2)
}

// rebalanceInternal 处理 parent.children[i] 这个内部节点的下溢。
// 内部节点借键要经过父节点"旋转"：父节点的分隔键下移，兄弟的键上移。
func (t *Tree[K, V]) rebalanceInternal(parent *node[K, V], i int) {
	n := parent.children[i]

	// 1. 向左兄弟借（右旋）。
	if i > 0 {
		left := parent.children[i-1]
		if len(left.keys) > t.minInternal {
			lastK := len(left.keys) - 1
			lastC := len(left.children) - 1
			n.keys = slices.Insert(n.keys, 0, parent.keys[i-1])
			n.children = slices.Insert(n.children, 0, left.children[lastC])
			parent.keys[i-1] = left.keys[lastK]
			left.keys = left.keys[:lastK]
			left.children = left.children[:lastC]
			return
		}
	}

	// 2. 向右兄弟借（左旋）。
	// 实际挪走的是 right.children[0] 这整棵子树，它的键范围是 [parent.keys[i], right.keys[0])
	// 子树挪到 n 的最右边后，n 需要一个新键把它和 n 原来的最后一个孩子分开，这个键必须是子树的下界 parent.keys[i]（下移）。上界 right.keys[0] 则上移到父节点，作为 n 和 right 之间新的分隔键。
	//
	// 注意不能把 right.keys[0] 放进 n：那样 [parent.keys[i], right.keys[0]) 之间的键会被导向 n 原来的最后一个孩子，查找就找不到了。
	// 叶子借键时写的是 parent.keys[i] = right.keys[0]，那是在更新父节点，而且叶子借的是单个键值对，不涉及子树，和这里不一样。
	if i < len(parent.children)-1 {
		right := parent.children[i+1]
		if len(right.keys) > t.minInternal {
			n.keys = append(n.keys, parent.keys[i])
			n.children = append(n.children, right.children[0])
			parent.keys[i] = right.keys[0]
			right.keys = slices.Delete(right.keys, 0, 1)
			right.children = slices.Delete(right.children, 0, 1)
			return
		}
	}

	// 3. 合并：父节点的分隔键下移，夹在两个节点的键中间。
	if i > 0 {
		t.mergeInternal(parent, i-1)
	} else {
		t.mergeInternal(parent, i)
	}
}

// mergeInternal 把 parent.children[i+1] 合并进 parent.children[i]。
func (t *Tree[K, V]) mergeInternal(parent *node[K, V], i int) {
	left, right := parent.children[i], parent.children[i+1]
	left.keys = append(left.keys, parent.keys[i])
	left.keys = append(left.keys, right.keys...)
	left.children = append(left.children, right.children...)
	parent.keys = slices.Delete(parent.keys, i, i+1)
	parent.children = slices.Delete(parent.children, i+1, i+2)
}

// Range 按升序遍历 [lo, hi) 范围内的键值对，fn 返回 false 时停止。
// 先像 Get 一样找到 lo 所在的叶子，然后沿着叶子链表往右扫，
// 不需要再回到上层节点，这是 B+ 树做范围查询快的原因。
func (t *Tree[K, V]) Range(lo, hi K, fn func(k K, v V) bool) {
	if t.root == nil {
		return
	}
	leaf, _ := t.findLeaf(lo)
	i, _ := slices.BinarySearch(leaf.keys, lo)
	for leaf != nil {
		for ; i < len(leaf.keys); i++ {
			if leaf.keys[i] >= hi {
				return
			}
			if !fn(leaf.keys[i], leaf.values[i]) {
				return
			}
		}
		leaf, i = leaf.next, 0
	}
}

// Ascend 按升序遍历所有键值对，fn 返回 false 时停止。
func (t *Tree[K, V]) Ascend(fn func(k K, v V) bool) {
	if t.root == nil {
		return
	}
	n := t.root
	for !n.leaf {
		n = n.children[0] // 一直往左走到最左边的叶子
	}
	for ; n != nil; n = n.next {
		for i := range n.keys {
			if !fn(n.keys[i], n.values[i]) {
				return
			}
		}
	}
}

// Height 返回树高（只有一个叶子时为 1，空树为 0）。
func (t *Tree[K, V]) Height() int {
	h := 0
	for n := t.root; n != nil; h++ {
		if n.leaf {
			return h + 1
		}
		n = n.children[0]
	}
	return h
}
