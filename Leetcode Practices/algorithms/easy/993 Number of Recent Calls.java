/**
You have a RecentCounter class which counts the number of recent requests within a certain time frame.

Implement the RecentCounter class:

RecentCounter() Initializes the counter with zero recent requests.
int ping(int t) Adds a new request at time t, where t represents some time in milliseconds, and returns the number of requests that has happened in the past 3000 milliseconds (including the new request). Specifically, return the number of requests that have happened in the inclusive range [t - 3000, t].
It is guaranteed that every call to ping uses a strictly larger value of t than the previous call.

 

Example 1:

Input
["RecentCounter", "ping", "ping", "ping", "ping"]
[[], [1], [100], [3001], [3002]]
Output
[null, 1, 2, 3, 3]

Explanation
RecentCounter recentCounter = new RecentCounter();
recentCounter.ping(1);     // requests = [1], range is [-2999,1], return 1
recentCounter.ping(100);   // requests = [1, 100], range is [-2900,100], return 2
recentCounter.ping(3001);  // requests = [1, 100, 3001], range is [1,3001], return 3
recentCounter.ping(3002);  // requests = [1, 100, 3001, 3002], range is [2,3002], return 3
 

Constraints:

1 <= t <= 10^9
Each test case will call ping with strictly increasing values of t.
At most 10^4 calls will be made to ping.
 */



// My Solution:
class RecentCounter {
    
    private Node root;

    public RecentCounter() {
        // 线段树
        root = new Node(0, (int) (1e9));
    }
    
    public int ping(int t) {
        update(root, t, t, 1);
        return query(root, t - 3000 < 0 ? 0 : t - 3000, t);
    }
    
    /**
     * 线段树的结点
     */
    static class Node {
        private int leftX; // 左范围
        private int rightX; // 右范围
        private int sum; // 范围内的总和
        private int lazy; // 懒标记
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
            return 0;
        }
        // 修改的区间包含当前结点
        if (left <= root.leftX && root.rightX <= right) {
            return root.sum;
        } else {
            lazyCreate(root); // 动态开点
            pushDown(root); // 下传 lazy
            int l = query(root.leftChild, left, right); // 求左子树
            int r = query(root.rightChild, left, right); // 求右子树
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
    public void update(Node root, int left, int right, int value) {
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

/**
 * Your RecentCounter object will be instantiated and called as such:
 * RecentCounter obj = new RecentCounter();
 * int param_1 = obj.ping(t);
 */
