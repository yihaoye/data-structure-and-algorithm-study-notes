/*
Given a binary tree, find the length of the longest path where each node in the path has the same value. This path may or may not pass through the root.

The length of path between two nodes is represented by the number of edges between them.

 

Example 1:

Input:

              5
             / \
            4   5
           / \   \
          1   1   5
Output: 2

 

Example 2:

Input:

              1
             / \
            4   5
           / \   \
          4   4   5
Output: 2

 

Note: The given binary tree has not more than 10000 nodes. The height of the tree is not more than 1000.
*/



// My Solution:
class Solution {
    // This solution's Big O should be: logN*logN
    public int max = 0;
    
    public int longestUnivaluePath(TreeNode root) {
        traversal(root);
        return max;
    }
    
    // DFS (Pre-Order Traversal)
    public void traversal(TreeNode root) {
        if (root == null) return;
        int count = longestRootvalueSinglepath(root.left, root.val) + longestRootvalueSinglepath(root.right, root.val); // longestUnivaluePath for each node is always equals its left and right logest single path sum up
        if (count >= max) max = count;
        traversal(root.left);
        traversal(root.right);
    }
    
    // Get logest single path's count for the same root value
    public int longestRootvalueSinglepath(TreeNode node, int rootVal) {
        int count = 0;
        if (node != null && node.val == rootVal) {
            count++;
            int countL = longestRootvalueSinglepath(node.left, rootVal);
            int countR = longestRootvalueSinglepath(node.right, rootVal);
            count += (countL >= countR) ? countL : countR;
        }
        return count;
    }
}