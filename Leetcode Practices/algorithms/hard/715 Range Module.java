/**
A Range Module is a module that tracks ranges of numbers. Design a data structure to track the ranges represented as half-open intervals and query about them.

A half-open interval [left, right) denotes all the real numbers x where left <= x < right.

Implement the RangeModule class:

RangeModule() Initializes the object of the data structure.
void addRange(int left, int right) Adds the half-open interval [left, right), tracking every real number in that interval. Adding an interval that partially overlaps with currently tracked numbers should add any numbers in the interval [left, right) that are not already tracked.
boolean queryRange(int left, int right) Returns true if every real number in the interval [left, right) is currently being tracked, and false otherwise.
void removeRange(int left, int right) Stops tracking every real number currently being tracked in the half-open interval [left, right).
 

Example 1:

Input
["RangeModule", "addRange", "removeRange", "queryRange", "queryRange", "queryRange"]
[[], [10, 20], [14, 16], [10, 14], [13, 15], [16, 17]]
Output
[null, null, null, true, false, true]

Explanation
RangeModule rangeModule = new RangeModule();
rangeModule.addRange(10, 20);
rangeModule.removeRange(14, 16);
rangeModule.queryRange(10, 14); // return True,(Every number in [10, 14) is being tracked)
rangeModule.queryRange(13, 15); // return False,(Numbers like 14, 14.03, 14.17 in [13, 15) are not being tracked)
rangeModule.queryRange(16, 17); // return True, (The number 16 in [16, 17) is still being tracked, despite the remove operation)
 

Constraints:

1 <= left < right <= 10^9
At most 10^4 calls will be made to addRange, queryRange, and removeRange.
 */



// My Solution:
class RangeModule {
    
    private Node root;

    public RangeModule() {
        // 线段树 - 求范围内最小值
        root = new Node(0, (int) (1e9));
    }
    
    public void addRange(int left, int right) {
        update(root, left, right - 1, 1);
    }
    
    public boolean queryRange(int left, int right) {
        int minValue = query(root, left, right - 1);
        if (minValue == Integer.MAX_VALUE || minValue < 1) return false;
        return true;
    }
    
    public void removeRange(int left, int right) {
        update(root, left, right - 1, 0);
    }
    
    /**
     * 线段树的结点
     */
    class Node {
        private int leftX; // 左范围
        private int rightX; // 右范围
        private int minValue; // 范围内的最值
        private Integer lazy; // 懒标记
        Node leftChild, rightChild; // 左子树和右子树

        public Node(int leftX, int rightX) {
            this.leftX = leftX;
            this.rightX = rightX;
        }
    }
    
    // 区间求值
    public int query(Node root, int left, int right) {
        // 不在范围内 直接返回
        if (root.rightX < left || right < root.leftX) {
            return Integer.MAX_VALUE;
        }
        // 查询的区间包含当前结点
        if (left <= root.leftX && root.rightX <= right) {
            return root.minValue;
        } else {
            lazyCreate(root); // 动态开点
            pushDown(root); // 下传 lazy
            int l = query(root.leftChild, left, right); // 求左子树
            int r = query(root.rightChild, left, right); // 求右子树
            return Math.min(l, r);
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
    public void update(Node root, int left, int right, int value) {
        // 不在范围内 直接返回
        if (root.rightX < left || right < root.leftX) {
            return;
        }
        // 修改的区间包含当前结点
        if (left <= root.leftX && root.rightX <= right) {
            root.minValue = value;
            root.lazy = value;
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
        if (root.lazy != null) {
            root.leftChild.minValue = root.lazy;
            root.leftChild.lazy = root.lazy;
            root.rightChild.minValue = root.lazy;
            root.rightChild.lazy = root.lazy;
            root.lazy = null;
        }
    }

    /**
     * 上传结果
     *
     * @param root
     */
    public void pushUp(Node root) {
        root.minValue = Math.min(root.leftChild.minValue, root.rightChild.minValue);
    }
}
/**
 * Your RangeModule object will be instantiated and called as such:
 * RangeModule obj = new RangeModule();
 * obj.addRange(left,right);
 * boolean param_2 = obj.queryRange(left,right);
 * obj.removeRange(left,right);
 */
