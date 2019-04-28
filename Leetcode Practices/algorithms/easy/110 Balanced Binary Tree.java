/*
Given a binary tree, determine if it is height-balanced.

For this problem, a height-balanced binary tree is defined as:

a binary tree in which the depth of the two subtrees of every node never differ by more than 1.

Example 1:

Given the following tree [3,9,20,null,null,15,7]:

    3
   / \
  9  20
    /  \
   15   7
Return true.

Example 2:

Given the following tree [1,2,2,3,3,null,null,4,4]:

       1
      / \
     2   2
    / \
   3   3
  / \
 4   4
Return false.
*/



// Other's Solution:
class Solution {
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        return height(root) != -1;
        
    }

    public int height(TreeNode node){
        if (node == null) return 0;

        int lH = height(node.left);
        if (lH == -1) return -1;

        int rH = height(node.right);
        if (rH == -1) return -1;
        
        if (lH - rH < -1 || lH - rH > 1) return -1;
        return Math.max(lH, rH) + 1;
    }
}



// My Solution:
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
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        if (!isBalanced(root.left) || !isBalanced(root.right)) return false;
        
        int lDepth = getNodeDepth(root.left);
        int rDepth = getNodeDepth(root.right);
        if (lDepth - rDepth > 1 || rDepth - lDepth > 1) return false;
        
        return true;
    }
    
    public int getNodeDepth(TreeNode root) {
        if (root == null) return 0;
        int lDepth = getNodeDepth(root.left);
        int rDepth = getNodeDepth(root.right);
        return (lDepth >= rDepth ? lDepth : rDepth) + 1;
    }
}