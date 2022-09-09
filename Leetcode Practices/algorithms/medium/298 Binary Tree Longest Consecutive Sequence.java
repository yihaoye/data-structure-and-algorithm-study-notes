/**
Given the root of a binary tree, return the length of the longest consecutive sequence path.

A consecutive sequence path is a path where the values increase by one along the path.

Note that the path can start at any node in the tree, and you cannot go from a node to its parent in the path.

 

Example 1:
https://assets.leetcode.com/uploads/2021/03/14/consec1-1-tree.jpg

Input: root = [1,null,3,2,4,null,null,null,5]
Output: 3
Explanation: Longest consecutive sequence path is 3-4-5, so return 3.

Example 2:
https://assets.leetcode.com/uploads/2021/03/14/consec1-2-tree.jpg

Input: root = [2,null,3,2,null,1]
Output: 2
Explanation: Longest consecutive sequence path is 2-3, not 3-2-1, so return 2.
 

Constraints:

The number of nodes in the tree is in the range [1, 3 * 10^4].
-3 * 10^4 <= Node.val <= 3 * 10^4
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
    public int res = 0;
    
    public int longestConsecutive(TreeNode root) {
        if (root != null) dfs(root, 1);
        return res;
    }
    
    public void dfs(TreeNode node, int maxLen) {
        res = Math.max(res, maxLen);
        if (node.left != null) {
            if (node.left.val == node.val + 1) dfs(node.left, maxLen + 1);
            else dfs(node.left, 1);
        }
        if (node.right != null) {
            if (node.right.val == node.val + 1) dfs(node.right, maxLen + 1);
            else dfs(node.right, 1);
        }
    }
}
