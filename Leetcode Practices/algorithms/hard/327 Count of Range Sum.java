/**
Given an integer array nums and two integers lower and upper, return the number of range sums that lie in [lower, upper] inclusive.

Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j inclusive, where i <= j.

 

Example 1:

Input: nums = [-2,5,-1], lower = -2, upper = 2
Output: 3
Explanation: The three ranges are: [0,0], [2,2], and [0,2] and their respective sums are: -2, -1, 2.
Example 2:

Input: nums = [0], lower = 0, upper = 0
Output: 1
 

Constraints:

1 <= nums.length <= 10^5
-2^31 <= nums[i] <= 2^31 - 1
-10^5 <= lower <= upper <= 10^5
The answer is guaranteed to fit in a 32-bit integer.
 */



// My Solution 3:
class Solution {
    long shift = (long) 1e10 / 2; // offset
    
    public int countRangeSum(int[] nums, int lower, int upper) {
        // prefix sum + segment tree
        int n = nums.length, res = 0;
        
        long sum = 0L;
        SegmentTree sgt = new SegmentTree();
        
        for (int i=0; i<n; i++) {
            sum += nums[i];
            if (lower <= sum && sum <= upper) res++;
            res += sgt.query(sgt.root, sum-upper+shift, sum-lower+shift); // dp[i] - dp[j] >= lower, dp[i] - dp[j] <= upper
            sgt.update(sgt.root, sum+shift, sum+shift, 1);
        }
        
        return res;
    }
}

public class SegmentTree { // 求范围内和 - 在这里其实也就是统计
    
    public Node root;

    public SegmentTree() {
        // 线段树
        root = new Node(0L, (long) 1e10);
    }
    
    /**
     * 线段树的结点
     */
    class Node {
        private long leftX; // 左范围
        private long rightX; // 右范围
        private long sum; // 范围内的总和
        private long lazy; // 懒标记
        Node leftChild, rightChild; // 左子树和右子树

        public Node(long leftX, long rightX) {
            this.leftX = leftX;
            this.rightX = rightX;
        }
    }
    
    // 区间求值
    public long query(Node root, long left, long right) {
        // 不在范围内 直接返回
        long rightX = root.rightX; long leftX = root.leftX;
        if (root.rightX < left || right < root.leftX) {
            return 0L;
        }
        // 查询的区间包含当前结点
        if (left <= root.leftX && root.rightX <= right) {
            return root.sum;
        } else {
            lazyCreate(root); // 动态开点
            pushDown(root); // 下传 lazy
            long l = query(root.leftChild, left, right); // 求左子树
            long r = query(root.rightChild, left, right); // 求右子树
            return l + r;
        }
    }

    /**
     * 区间更新
     *
     * @param root  树的根
     * @param left  左边界
     * @param right 右边界
     * @param value 更新值
     */
    public void update(Node root, long left, long right, int value) {
        // 不在范围内 直接返回
        if (root.rightX < left || right < root.leftX) {
            return;
        }
        // 修改的区间包含当前结点
        if (left <= root.leftX && root.rightX <= right) {
            root.sum += value * (root.rightX - root.leftX + 1);
            root.lazy += value;
        } else {
            lazyCreate(root); // 动态开点
            pushDown(root); // 下传 lazy
            update(root.leftChild, left, right, value); // 更新左子树
            update(root.rightChild, left, right, value); // 更新右子树
            pushUp(root); // 上传结果
        }
    }

    /**
     * 创建左右子树
     *
     * @param root
     */
    public void lazyCreate(Node root) {
        if (root.leftChild == null) {
            root.leftChild = new Node(root.leftX, root.leftX + (root.rightX - root.leftX) / 2);
        }
        if (root.rightChild == null) {
            root.rightChild = new Node(root.leftX + (root.rightX - root.leftX) / 2 + 1, root.rightX);
        }
    }

    /**
     * 下传 lazy
     *
     * @param root
     */
    public void pushDown(Node root) {
        if (root.lazy > 0) {
            root.leftChild.sum += root.lazy * (root.leftChild.rightX - root.leftChild.leftX + 1);
            root.leftChild.lazy += root.lazy;
            root.rightChild.sum += root.lazy * (root.rightChild.rightX - root.rightChild.leftX + 1);
            root.rightChild.lazy += root.lazy;
            root.lazy = 0;
        }
    }

    /**
     * 上传结果
     *
     * @param root
     */
    public void pushUp(Node root) {
        root.sum = root.leftChild.sum + root.rightChild.sum;
    }
}



// My Solution 1: (TLE)
class Solution {
    public int countRangeSum(int[] nums, int lower, int upper) {
        // prefix sum + customized red black tree
        int n = nums.length, res = 0;
        
        long sum = 0L;
        RBTree<Long> rbt = new RBTree<>();
        
        for (int i=0; i<n; i++) {
            sum += nums[i];
            if (lower <= sum && sum <= upper) res++;
            res += rbt.dfs(sum-upper, sum-lower); // dp[i] - dp[j] >= lower, dp[i] - dp[j] <= upper
            rbt.insert(sum);
        }
        
        return res;
    }
}

public class RBTree<T extends Comparable<T>> {

    private RBTNode<T> mRoot;    // 根结点

    private static final boolean RED   = false;
    private static final boolean BLACK = true;

    public class RBTNode<T extends Comparable<T>> {
        boolean color;        // 颜色
        T key;                // 关键字(键值)
        int cnt;
        RBTNode<T> left;      // 左孩子
        RBTNode<T> right;     // 右孩子
        RBTNode<T> parent;    // 父结点

        public RBTNode(T key, boolean color, RBTNode<T> parent, RBTNode<T> left, RBTNode<T> right) {
            this.key = key;
            this.cnt = 1;
            this.color = color;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public T getKey() {
            return key;
        }
    }

    public RBTree() {
        mRoot = null;
    }

    /* 核心部分 */
    private void leftRotate(RBTNode<T> x) {
        RBTNode<T> y = x.right;

        x.right = y.left;
        if (y.left != null) y.left.parent = x;

        y.parent = x.parent;

        if (x.parent == null) {
            this.mRoot = y;
        } else {
            if (x.parent.left == x) x.parent.left = y;
            else x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    private void rightRotate(RBTNode<T> y) {
        RBTNode<T> x = y.left;

        y.left = x.right;
        if (x.right != null) x.right.parent = y;

        x.parent = y.parent;

        if (y.parent == null) {
            this.mRoot = x;
        } else {
            if (y == y.parent.right) y.parent.right = x;
            else y.parent.left = x;
        }

        x.right = y;
        y.parent = x;
    }

    private void insertFixUp(RBTNode<T> node) {
        RBTNode<T> parent, gparent;

        while (((parent = parentOf(node)) != null) && isRed(parent)) {
            gparent = parentOf(parent);

            if (parent == gparent.left) {
                RBTNode<T> uncle = gparent.right;
                if ((uncle != null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                if (parent.right == node) {
                    leftRotate(parent);
                    RBTNode<T> tmp = parent;
                    parent = node;
                    node = tmp;
                }

                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);
            } else {
                RBTNode<T> uncle = gparent.left;
                if ((uncle != null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                if (parent.left == node) {
                    rightRotate(parent);
                    RBTNode<T> tmp = parent;
                    parent = node;
                    node = tmp;
                }
                
                setBlack(parent);
                setRed(gparent);
                leftRotate(gparent);
            }
        }

        setBlack(this.mRoot);
    }

    private void insert(RBTNode<T> node) {
        int cmp;
        RBTNode<T> y = null;
        RBTNode<T> x = this.mRoot;

        while (x != null) {
            y = x;
            cmp = node.key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else { x.cnt++; return; }
        }

        node.parent = y;
        if (y != null) {
            cmp = node.key.compareTo(y.key);
            if (cmp < 0) y.left = node;
            else y.right = node;
        } else {
            this.mRoot = node;
        }

        node.color = RED;
        insertFixUp(node);
    }

    public void insert(T key) {
        RBTNode<T> node = new RBTNode<T>(key, BLACK, null, null, null);
        insert(node);
    }

    

    /* 次核心部分 */
    private RBTNode<T> parentOf(RBTNode<T> node) {
        return node != null ? node.parent : null;
    }
    private boolean colorOf(RBTNode<T> node) {
        return node != null ? node.color : BLACK;
    }
    private boolean isRed(RBTNode<T> node) {
        return ((node != null) && (node.color == RED)) ? true : false;
    }
    private boolean isBlack(RBTNode<T> node) {
        return !isRed(node);
    }
    private void setBlack(RBTNode<T> node) {
        if (node != null) node.color = BLACK;
    }
    private void setRed(RBTNode<T> node) {
        if (node != null) node.color = RED;
    }
    private void setParent(RBTNode<T> node, RBTNode<T> parent) {
        if (node != null) node.parent = parent;
    }
    private void setColor(RBTNode<T> node, boolean color) {
        if (node != null) node.color = color;
    }
    
    private int dfs(RBTNode<T> x, T fromKey, T toKey) {
        if (x == null) return 0;

        int res = 0;
        int cmpF = x.key.compareTo(fromKey);
        int cmpT = x.key.compareTo(toKey);
        if (cmpF >= 0 && cmpT <= 0) {
            res += x.cnt;
            res += dfs(x.right, fromKey, toKey);
            res += dfs(x.left, fromKey, toKey);
        }
        if (cmpF < 0) res += dfs(x.right, fromKey, toKey);
        if (cmpT > 0) res += dfs(x.left, fromKey, toKey);
        
        return res;
    }

    public int dfs(T fromKey, T toKey) {
        return dfs(mRoot, fromKey, toKey);
    }
}



// My Solution 2: (TLE)
class Solution {
    public int countRangeSum(int[] nums, int lower, int upper) {
        // prefix sum + treeset + object
        int n = nums.length, res = 0;
        
        long sum = 0L;
        TreeSet<Num> tSet = new TreeSet<>();
        
        for (int i=0; i<n; i++) {
            sum += nums[i];
            if (lower <= sum && sum <= upper) res++;
            res += tSet.subSet(new Num(sum-upper, 0), true, new Num(sum-lower, n), true).size(); // dp[i] - dp[j] >= lower, dp[i] - dp[j] <= upper
            tSet.add(new Num(sum, i));
        }
        
        return res;
    }
    
    class Num implements Comparable<Num> {
        long val;
        int idx;
        
        public Num(long val, int idx) {
            this.val = val;
            this.idx = idx;
        }
        
        @Override
        public int compareTo(Num otherNum) {
            return Long.compare(this.val, otherNum.val) == 0 ? this.idx - otherNum.idx : Long.compare(this.val, otherNum.val);
        }
    }
}
