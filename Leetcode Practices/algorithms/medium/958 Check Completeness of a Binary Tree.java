/**
Given the root of a binary tree, determine if it is a complete binary tree.

In a complete binary tree, every level, except possibly the last, is completely filled, and all nodes in the last level are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.

 

Example 1:
https://assets.leetcode.com/uploads/2018/12/15/complete-binary-tree-1.png

Input: root = [1,2,3,4,5,6]
Output: true
Explanation: Every level before the last is full (ie. levels with node-values {1} and {2, 3}), and all nodes in the last level ({4, 5, 6}) are as far left as possible.

Example 2:
https://assets.leetcode.com/uploads/2018/12/15/complete-binary-tree-2.png

Input: root = [1,2,3,4,5,null,7]
Output: false
Explanation: The node with value 7 isn't as far left as possible.
 

Constraints:

The number of nodes in the tree is in the range [1, 100].
1 <= Node.val <= 1000
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
    public boolean isCompleteTree(TreeNode root) {
        // BFS
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) queue.offer(root);
        boolean shouldEnd = false;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    if (shouldEnd) return false;
                    queue.offer(node.left);
                } else {
                    shouldEnd = true;
                }

                if (node.right != null) {
                    if (shouldEnd) return false;
                    queue.offer(node.right);
                } else {
                    shouldEnd = true;
                }
            }
        }
        return true;
    }
}
