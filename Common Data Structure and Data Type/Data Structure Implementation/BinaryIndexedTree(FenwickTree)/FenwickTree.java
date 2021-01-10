// 详细请看：
// https://www.acwing.com/blog/content/80/
// https://jojozhuang.github.io/algorithm/data-structure-fenwick-tree
public class FenwickTree {

    /**
     * 预处理数组
     */
    private int[] tree; // index 0 不使用，所以总长为 len + 1
    private int len;

    public FenwickTree(int n) {
        this.len = n;
        tree = new int[this.len + 1];
    }

    /**
     * 树状数组的初始化
     */
    public FenwickTree(int[] nums) {
        this.len = nums.length;
        tree = new int[this.len + 1];
        for (int i = 0; i < this.len; i++) {
            update(i, nums[i]);
        }
    }

    /**
     * 单点更新
     *
     * @param i     原始数组索引 i
     * @param delta 变化值 = 更新以后的值 - 原始值
     */
    public void update(int i, int delta) {
        i += 1;
        // 从下到上更新，注意，预处理数组，比原始数组的 len 大 1，故预处理索引的最大值为 len
        while (i <= this.len) {
            tree[i] += delta;
            i += lowbit(i);
        }
    }

    /**
     * 查询前缀和
     *
     * @param i 前缀的最大索引，即查询区间 [0, i] 的所有元素之和
     */
    public int query(int i) {
        i += 1;
        // 从右到左查询
        int sum = 0;
        while (i > 0) {
            sum += tree[i];
            i -= lowbit(i);
        }
        return sum;
    }

    /**
     * 区间查询
     * 即查询区间 [i, j] 的所有元素之和
     * 
     * @param i 前缀的最小索引
     * @param j 前缀的最大索引
     */
    public int query(int i, int j) {
        if (i >= 0 && j >= 0 && j >= i) {
            return query(j) - query(i - 1);
        } else {
            return -1;
        }
    }

    /**
     * lowbit 函数/方法
     */
    public static int lowbit(int x) {
        return x & (-x);
    }
}
