/**
There are several squares being dropped onto the X-axis of a 2D plane.

You are given a 2D integer array positions where positions[i] = [lefti, sideLengthi] represents the ith square with a side length of sideLengthi that is dropped with its left edge aligned with X-coordinate lefti.

Each square is dropped one at a time from a height above any landed squares. It then falls downward (negative Y direction) until it either lands on the top side of another square or on the X-axis. A square brushing the left/right side of another square does not count as landing on it. Once it lands, it freezes in place and cannot be moved.

After each square is dropped, you must record the height of the current tallest stack of squares.

Return an integer array ans where ans[i] represents the height described above after dropping the ith square.

 

Example 1:
https://assets.leetcode.com/uploads/2021/04/28/fallingsq1-plane.jpg

Input: positions = [[1,2],[2,3],[6,1]]
Output: [2,5,5]
Explanation:
After the first drop, the tallest stack is square 1 with a height of 2.
After the second drop, the tallest stack is squares 1 and 2 with a height of 5.
After the third drop, the tallest stack is still squares 1 and 2 with a height of 5.
Thus, we return an answer of [2, 5, 5].

Example 2:

Input: positions = [[100,100],[200,100]]
Output: [100,100]
Explanation:
After the first drop, the tallest stack is square 1 with a height of 100.
After the second drop, the tallest stack is either square 1 or square 2, both with heights of 100.
Thus, we return an answer of [100, 100].
Note that square 2 only brushes the right side of square 1, which does not count as landing on it.
 

Constraints:

1 <= positions.length <= 1000
1 <= lefti <= 10^8
1 <= sideLengthi <= 10^6
 */



// My Solution:
class Solution {
    
    private Node root;
    
    public List<Integer> fallingSquares(int[][] positions) {
        // 线段树
        int max = (int) (1e9);
        root = new Node(0, max);
        List<Integer> res = new ArrayList<>();
        for (int[] position : positions) {
            int rangeMaxValue = query(root, position[0], position[0] + position[1] - 1);
            update(root, position[0], position[0] + position[1] - 1, rangeMaxValue + position[1]);
            int curGlobalMaxValue = query(root, 0, max);
            res.add(curGlobalMaxValue);
        }
        return res;
    }
    
    /**
     * 线段树的结点
     */
    class Node {
        private int leftX; // 左范围
        private int rightX; // 右范围
        private int maxValue; // 范围内的最大值
        private int lazy; // 懒标记
        Node leftChild, rightChild; // 左子树和右子树

        public Node(int leftX, int rightX) {
            this.leftX = leftX;
            this.rightX = rightX;
        }
    }

    /**
     * 区间求值
     *
     * @param root  树的根
     * @param left  左边界
     * @param right 右边界
     */
    public int query(Node root, int left, int right) {
        // 不在范围内 直接返回
        if (root.rightX < left || right < root.leftX) {
            return 0;
        }
        // 查询的区间包含当前结点
        if (left <= root.leftX && root.rightX <= right) {
            return root.maxValue;
        } else {
            lazyCreate(root); // 动态开点
            pushDown(root); // 下传 lazy
            int l = query(root.leftChild, left, right); // 求左子树
            int r = query(root.rightChild, left, right); // 求右子树
            return Math.max(l, r);
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
            root.maxValue = value;
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
        if (root.lazy > 0) {
            root.leftChild.maxValue = root.lazy;
            root.leftChild.lazy = root.lazy;
            root.rightChild.maxValue = root.lazy;
            root.rightChild.lazy = root.lazy;
            root.lazy = 0;
        }
    }

    /**
     * 上传结果
     *
     * @param root
     */
    public void pushUp(Node root) {
        root.maxValue = Math.max(root.leftChild.maxValue, root.rightChild.maxValue);
    }
}
