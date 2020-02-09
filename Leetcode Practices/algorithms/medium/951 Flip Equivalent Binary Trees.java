/*
For a binary tree T, we can define a flip operation as follows: choose any node, and swap the left and right child subtrees.

A binary tree X is flip equivalent to a binary tree Y if and only if we can make X equal to Y after some number of flip operations.

Write a function that determines whether two binary trees are flip equivalent.  The trees are given by root nodes root1 and root2.

 

Example 1:

Input: root1 = [1,2,3,4,5,6,null,null,null,7,8], root2 = [1,3,2,null,6,4,5,null,null,null,null,8,7]
Output: true
Explanation: We flipped at nodes with values 1, 3, and 5.
![](https://assets.leetcode.com/uploads/2018/11/29/tree_ex.png)
 
Note:
1. Each tree will have at most 100 nodes.
2. Each value in each tree will be a unique integer in the range [0, 99].
*/



// My Solution 1:
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return true;
        if (root1 == null || root2 == null || root1.val != root2.val) return false;
        boolean lr = flipEquiv(root1.left, root2.right);
        boolean ll = flipEquiv(root1.left, root2.left);
        boolean rl = flipEquiv(root1.right, root2.left);
        boolean rr = flipEquiv(root1.right, root2.right);
        return (lr || ll) && (rl || rr) && (root1.val == root2.val);
    }
}