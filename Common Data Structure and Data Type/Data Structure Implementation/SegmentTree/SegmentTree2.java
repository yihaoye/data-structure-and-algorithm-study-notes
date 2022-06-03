// https://zhuanlan.zhihu.com/p/174810030  

// 处理器 - 求值方程、更新方程
// 包含 merge() pushDown() update()，注意 pushDown() 逻辑基于 merge() 和 update() 逻辑。而 update() 除了调用了 merge() pushDown()，其计算逻辑也基于 merge() 逻辑
public interface Handler<E> {
    // a 表示左区间的统计值/求值，b 表示有区间的统计值/求值
    // 表示如何合并两个区间的统计值，返回整个 [左区间+右区间] 的统计值/求值
    E merge(E a, E b);

    // 对懒标记进行 pushDown，需要定义如何对
    void pushDown(E[] tree, E[] data, E[] marks, int treeIndex, int l, int r);

    // 对区间 [updateL, updateR] 进行更新，
    // 可以是对区间所有元素直接赋值，也可以是区间元素均根据给定 val 重新计算（自增、自减、自乘等等）
    void update(E[] tree, E[] data, E[] marks, int treeIndex, int l, int r, int updateL, int updateR, E val);
}

public class SegmentTree<E> {
    private E[] tree; // 线段树 - SegmentTreeNodes，存的是 val
    private E[] data; // 数据
    private E[] marks; // 每个树节点的懒标记
    private Handler<E> handler; // 处理器 - 求值方程、更新方程

    public SegmentTree(E[] arr, Handler<E> handler) {
        this.handler = handler;
        data = (E[]) new Object[arr.length];
        tree = (E[]) new Object[arr.length * 4]; // 大小为 4 * n
        marks = (E[]) new Object[arr.length * 4];
        for (int i = 0; i < arr.length; i++) data[i] = arr[i];
        buildSegmentTree(0, 0, data.length - 1); // 构建线段树
    }

    // 返回数组元素个数
    public int getSize() {
        return data.length;
    }

    // 根据一个节点的索引 index，返回这个节点的左孩子的索引
    private int leftChild(int index) {
        return 2 * index + 1;
    }

    // 根据一个节点的索引 index，返回这个节点的右孩子的索引
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
        tree[treeIndex] = handler.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    // 查询区间，返回区间 [queryL, queryR] 的统计值/求值。查询单点时只需使得 queryL == queryR，这里移除了之前的根据索引获取数据方法，因为需要层层遍历线段树完成 pushDown 否则直接从 data 中读是错误的
    public E query(int queryL, int queryR) {
        // 首先进行边界检查
        if (queryL < 0 || queryL > data.length || queryR < 0 || queryR > data.length || queryL > queryR) {
            throw new IllegalArgumentException("Index is illegal");
        }
        return query(0, 0, data.length - 1, queryL, queryR);
    }

    // 在以 treeIndex 为根的线段树中 [l, r] 的范围里，搜索区间 [queryL, queryR]
    private E query(int treeIndex, int l, int r, int queryL, int queryR) {
        if (marks[treeIndex] != null) handler.pushDown(tree, marks, data, treeIndex, l, r);
        if (l == queryL && r == queryR) return tree[treeIndex];
        int mid = l + (r - l) / 2;
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);
        // 如果右边界小于等于中间节点，则查询左区间
        if (queryR <= mid) return query(leftTreeIndex, l, mid, queryL, queryR);
        // 如果左边界大于中间节点，则查询右区间
        if (queryL > mid) return query(rightTreeIndex, mid + 1, r, queryL, queryR);
        // 如果上述两种情况都不是，则根据中间节点，拆分为两个查询区间
        E leftResult = query(leftTreeIndex, l, mid, queryL, mid);
        E rightResult = query(rightTreeIndex, mid + 1, r, mid + 1, queryR);
        // 合并左右区间的查询结果
        return handler.merge(leftResult, rightResult);
    }

    // 将 [updateL, updateR] 位置的值，更新为 val
    public void update(int updateL, int updateR, E val) {
        // 首先进行边界检查
        if (updateL < 0 || updateL > data.length || updateR < 0 || updateR > data.length || updateL > updateR) {
            throw new IllegalArgumentException("Index is illegal");
        }
        
        handler.update(tree, data, marks, 0, 0, data.length - 1, updateL, updateR, val);
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
        SegmentTree<Integer> segTree = new SegmentTree<>(nums, new Handler<Integer>() {
            @Override
            public Integer merge(Integer a, Integer b) {
                // 返回 a 和 b 的最大值
                return Math.max(a, b);
            }

            @Override
            public void pushDown(Integer[] tree, Integer[] data, Integer[] marks, int treeIndex, int l, int r) {
                if (l > r) return;

                if (l == r) {
                    data[l] = tree[treeIndex];
                } else {
                    int leftTreeIndex = 2 * treeIndex + 1;
                    int rightTreeIndex = 2 * treeIndex + 2;
                    tree[leftTreeIndex] = marks[treeIndex];
                    marks[leftTreeIndex] = marks[treeIndex];
                    tree[rightTreeIndex] = marks[treeIndex];
                    marks[rightTreeIndex] = marks[treeIndex];
                }
                marks[treeIndex] = null;
            }

            @Override
            public void update(Integer[] tree, Integer[] data, Integer[] marks, int treeIndex, int l, int r, int updateL, int updateR, Integer val) { // 区间 [l, r] 全部赋值为 val
                if (l >= updateL && r <= updateR) {
                    tree[treeIndex] = val;
                    marks[treeIndex] = val;
                    return;
                }
                if (marks[treeIndex] != null) pushDown(tree, marks, data, treeIndex, l, r);
                int leftTreeIndex = 2 * treeIndex + 1;
                int rightTreeIndex = 2 * treeIndex + 2;
                int mid = l + (r - l) / 2;
                if (updateR <= mid) update(ree, data, marks, leftTreeIndex, l, mid, updateL, updateR, val);
                if (updateL > mid) update(tree, data, marks, rightTreeIndex, mid + 1, r, updateL, updateR, val);
                
                tree[treeIndex] = merge(tree[leftTreeIndex], tree[rightTreeIndex]);
            }
        });

        // 查询区间 [2,5] 的最大值
        System.out.println(segTree.query(4, 7));
    }
}

// 当然，也可以定义一个求区间内元素的和的线段树，需要修改 merge() pushDown() update() 3 个方法的实现：
public class Main {
    public static void main(String[] args) {
        Integer[] nums = new Integer[]{34, 65, 8, 10, 21, 86, 79, 30};
        SegmentTree<Integer> segTree = new SegmentTree<>(nums, new Handler<Integer>() {
            @Override
            public Integer merge(Integer a, Integer b) {
                // 返回 a 和 b 的和
                return a + b;
            }

            @Override
            public void pushDown(Integer[] tree, Integer[] data, Integer[] marks, int treeIndex, int l, int r) {
                if (l > r) return;

                if (l == r) {
                    data[l] = tree[treeIndex];
                } else {
                    int leftTreeIndex = 2 * treeIndex + 1;
                    int rightTreeIndex = 2 * treeIndex + 2;
                    int mid = l + (r - l) / 2;
                    tree[leftTreeIndex] += marks[treeIndex] * (mid - l + 1);
                    marks[leftTreeIndex] += marks[treeIndex];
                    tree[rightTreeIndex] += marks[treeIndex] * (r - mid);
                    marks[rightTreeIndex] += marks[treeIndex];
                }
                marks[treeIndex] = null;
            }

            @Override
            public void update(Integer[] tree, Integer[] data, Integer[] marks, int treeIndex, int l, int r, int updateL, int updateR, Integer val) { // 区间 [l, r] 全部自增 val
                if (l >= updateL && r <= updateR) {
                    tree[treeIndex] += val * (r - l + 1); // 注意：此处计算基于 merge() 逻辑
                    marks[treeIndex] += val;
                    return;
                }
                if (marks[treeIndex] != null) pushDown(tree, marks, data, treeIndex, l, r);
                int leftTreeIndex = 2 * treeIndex + 1;
                int rightTreeIndex = 2 * treeIndex + 2;
                int mid = l + (r - l) / 2;
                if (updateR <= mid) update(ree, data, marks, leftTreeIndex, l, mid, updateL, updateR, val);
                if (updateL > mid) update(tree, data, marks, rightTreeIndex, mid + 1, r, updateL, updateR, val);
                
                tree[treeIndex] = merge(tree[leftTreeIndex], tree[rightTreeIndex]);
            }
        });
        
        // 查询区间 [2,5] 的和
        System.out.println(segTree.query(4, 7));
    }
}
