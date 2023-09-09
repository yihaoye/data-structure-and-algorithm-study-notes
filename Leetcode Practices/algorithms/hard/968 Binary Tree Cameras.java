/*
You are given the root of a binary tree. We install cameras on the tree nodes where each camera at a node can monitor its parent, itself, and its immediate children.

Return the minimum number of cameras needed to monitor all nodes of the tree.

 

Example 1:


Input: root = [0,0,null,0,0]
Output: 1
Explanation: One camera is enough to monitor all nodes if placed as shown.
Example 2:


Input: root = [0,0,null,0,null,0,null,null,0]
Output: 2
Explanation: At least two cameras are needed to monitor all nodes of the tree. The above image shows one of the valid configurations of camera placement.
 

Constraints:

The number of nodes in the tree is in the range [1, 1000].
Node.val == 0
*/



// My Solution:
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    Map<TreeNode, int[]> dp; // <node, <nodeRes if node.val = 1, nodeRes if node.val = 2>>

    public int minCameraCover(TreeNode root) {
        // 递归/回溯法（从根节点开始，假设当前节点如果设为 camera 或不设为 camera 时，左右子节点最少需要设多少才能满足条件，设为 camera 时把 val 设为 2，已被监控覆盖则设置为 1）+ 记忆化搜索/动态规划
        dp = new HashMap<>();
        return minCameraCover(new TreeNode(1), root);
    }

    public int minCameraCover(TreeNode parent, TreeNode node) {
        dp.putIfAbsent(node, new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE});
        if (node.val == 2) { // 若已被设置为 camera，直接跳过后续判断
            if (dp.get(node)[1] == Integer.MAX_VALUE) dp.get(node)[1] = calSubRes(node) + 1;
            return dp.get(node)[1];
        }

        int res = Integer.MAX_VALUE;
        if (parent.val == 2) {
            // 把 node 设为 camera
            node.val = 2;
            if (dp.get(node)[1] == Integer.MAX_VALUE) dp.get(node)[1] = calSubRes(node) + 1;
            // 不把 node 设为 camera
            node.val = 1;
            if (dp.get(node)[0] == Integer.MAX_VALUE) dp.get(node)[0] = calSubRes(node);
            node.val = 0;

            res = Math.min(dp.get(node)[0], dp.get(node)[1]);
        } else { // parent.val == 1，逻辑控制不允许为 0（这里没有应用 DP 只是因为代码较为麻烦，因为有前面 DP 的优化足够了所以即使这里不实现也不会降低太多性能）
            // 把 node 设为 camera
            node.val = 2;
            res = Math.min(res, calSubRes(node) + 1);
            node.val = 0;

            // 不把 node 设为 camera，但是至少一个子节点需要设为 camera
            if (node.left == null && node.right == null) return res; // 没有子节点，所以不符合条件
            node.val = 1;
            if (node.left != null && node.right != null) {
                node.left.val = 2; node.right.val = 2;
                res = Math.min(res, calSubRes(node));
                node.left.val = 0; node.right.val = 0;
            }
            if (node.left != null) {
                node.left.val = 2;
                res = Math.min(res, calSubRes(node));
                node.left.val = 0;
            }
            if (node.right != null) {
                node.right.val = 2;
                res = Math.min(res, calSubRes(node));
                node.right.val = 0;
            }
            node.val = 0;
        }

        return res;
    }

    public int calSubRes(TreeNode node) {
        return (node.left != null ? minCameraCover(node, node.left) : 0) + (node.right != null ? minCameraCover(node, node.right) : 0);
    }
}
