/**
Given the root of a Binary Search Tree and a target number k, return true if there exist two elements in the BST such that their sum is equal to the given target.

 

Example 1:
https://assets.leetcode.com/uploads/2020/09/21/sum_tree_1.jpg

Input: root = [5,3,6,2,4,null,7], k = 9
Output: true

Example 2:
https://assets.leetcode.com/uploads/2020/09/21/sum_tree_2.jpg

Input: root = [5,3,6,2,4,null,7], k = 28
Output: false
 

Constraints:

The number of nodes in the tree is in the range [1, 10^4].
-10^4 <= Node.val <= 10^4
root is guaranteed to be a valid binary search tree.
-10^5 <= k <= 10^5
 */



// Other's Solution:
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
    public boolean findTarget(TreeNode root, int k) {
        // BST - https://leetcode.com/problems/two-sum-iv-input-is-a-bst/discuss/106059/JavaC%2B%2B-Three-simple-methods-choose-one-you-like
        // For each node, we check if k - node.val exists in this BST.
        // Time Complexity: O(n*h), Space Complexity: O(h). h is the height of the tree, which is logN at best case, and N at worst case.
        return dfs(root, root, k);
    }
    
    public boolean dfs(TreeNode root, TreeNode cur, int k) {
        if (cur == null) return false;
        return search(root, cur, k - cur.val) || dfs(root, cur.left, k) || dfs(root, cur.right, k);
    }
    
    public boolean search(TreeNode root, TreeNode cur, int value) {
        if (root == null) return false;
        return ((root.val == value) && (root != cur)) || ((root.val < value) && search(root.right, cur, value)) || ((root.val > value) && search(root.left, cur, value));
    }
}
