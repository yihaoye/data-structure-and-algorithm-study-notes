// https://zhuanlan.zhihu.com/p/174810030
public class SegmentTree<E> {
    private E[] tree; // 线段树 - nodes，存的是 val
    private E[] data; // 数据
    private E[] marks; // 每个树节点的懒标记
    private Merger<E> merger; // 融合器 - 求值方程

    public SegmentTree(E[] arr, Merger<E> merger) {
        this.merger = merger;
        data = (E[]) new Object[arr.length];
        tree = (E[]) new Object[arr.length * 4]; // 大小为 4 * n
        for (int i = 0; i < arr.length; i++) data[i] = arr[i];
        buildSegmentTree(0, 0, data.length - 1); // 构建线段树
    }

    // 返回数组元素个数
    public int getSize() {
        return data.length;
    }

    // 根据索引获取数据
    public E get(int index) {
        if (index < 0 || index > data.length) throw new IllegalArgumentException("Index is illegal");
        return data[index];
    }

    //根据一个节点的索引 index，返回这个节点的左孩子的索引
    private int leftChild(int index) {
        return 2 * index + 1;
    }

    //根据一个节点的索引 index，返回这个节点的右孩子的索引
    private int rightChild(int index) {
        return 2 * index + 2;
    }

    // 在 treeIndex 的位置创建表示区间 [l, r] 的线段树
    private void buildSegmentTree(int treeIndex, int l, int r) {
        // base case：递归到叶子节点了
        if (l == r) {
            tree[treeIndex] = data[l];
            return;
        }

        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);
        // 划分区间
        int mid = l + (r - l) / 2;
        // 求（左孩子）左区间的统计值/求值
        buildSegmentTree(leftTreeIndex, l, mid);
        // 求（右孩子）右区间的统计值/求值
        buildSegmentTree(rightTreeIndex, mid + 1, r);
        // 求当前节点 [左区间+右区间] 的统计值/求值
        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    // 查询区间，返回区间 [queryL, queryR] 的统计值
    public E query(int queryL, int queryR) {
        // 首先进行边界检查
        if (queryL < 0 || queryL > data.length || queryR < 0 || queryR > data.length || queryL > queryR) {
            throw new IllegalArgumentException("Index is illegal");
        }
        return query(0, 0, data.length - 1, queryL, queryR);
    }

    // 在以 treeIndex 为根的线段树中 [l, r] 的范围里，搜索区间 [queryL, queryR]
    private E query(int treeIndex, int l, int r, int queryL, int queryR) {
        if (l == queryL && r == queryR) return tree[treeIndex];

        // pushDown(treeIndex, r - l + 1);
        int mid = l + (r - l) / 2;
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);
        // 如果左边界大于中间节点，则查询右区间
        if (queryL > mid) return query(rightTreeIndex, mid + 1, r, queryL, queryR);
        // 如果右边界小于等于中间节点，则查询左区间
        if (queryR <= mid) return query(leftTreeIndex, l, mid, queryL, queryR);
        // 如果上述两种情况都不是，则根据中间节点，拆分为两个查询区间
        E leftResult = query(leftTreeIndex, l, mid, queryL, mid);
        E rightResult = query(rightTreeIndex, mid + 1, r, mid + 1, queryR);
        // 合并左右区间的查询结果
        return merger.merge(leftResult, rightResult);
    }

    // 将 index 位置的值，更新为 e
    public void update(int index, E e) {
        if (index < 0 || index >= data.length) throw new IllegalArgumentException("Index is illegal");
        
        data[index] = e;
        // 更新线段树相应的节点
        updateTree(0, 0, data.length - 1, index, e);
    }

    // 在以 treeIndex 为根的线段树中，更新 index 的值为 e
    private void updateTree(int treeIndex, int l, int r, int index, E e) {
        // 递归终止条件
        if (l == r) {
            tree[treeIndex] = e;
            return;
        }
        int mid = l + (r - l) / 2;
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);
        if (index > mid) updateTree(rightTreeIndex, mid + 1, r, index, e);
        else updateTree(leftTreeIndex, l, mid, index, e); // index <= mid

        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]); // 更新当前节点
    }
    
    public String toString() {
        StringBuffer res = new StringBuffer();
        res.append('[');
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null) res.append(tree[i]);
            else res.append("null");

            if (i != tree.length - 1) res.append(", ");
        }
        res.append(']');
        return res.toString();
    }
}



// 使用例子
// 定义一个求区间的最大值的线段树，代码如下：
public class Main {
    public static void main(String[] args) {
        Integer[] nums = new Integer[]{34, 65, 8, 10, 21, 86, 79, 30};
        SegmentTree<Integer> segTree = new SegmentTree<>(nums, new Merger<Integer>() {
            @Override
            public Integer merge(Integer a, Integer b) {
                // 返回 a 和 b 的最大值
                return Math.max(a, b);
            }
        });
        // 查询区间 [2,5] 的最大值
        System.out.println(segTree.query(4, 7));
    }
}

// 当然，也可以定义一个求区间内元素的和的线段树，只需要修改 merge() 方法的实现即可：
public class Main {
    public static void main(String[] args) {
        Integer[] nums = new Integer[]{34, 65, 8, 10, 21, 86, 79, 30};
        SegmentTree<Integer> segTree = new SegmentTree<>(nums, new Merger<Integer>() {
            @Override
            public Integer merge(Integer a, Integer b) {
                // 返回 a 和 b 的和
                return a + b;
            }
        });
        // 查询区间 [2,5] 的和
        System.out.println(segTree.query(4, 7));
    }
}
