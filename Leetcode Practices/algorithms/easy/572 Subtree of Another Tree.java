/*
Given two non-empty binary trees s and t, check whether tree t has exactly the same structure and node values with a subtree of s. A subtree of s is a tree consists of a node in s and all of this node's descendants. The tree s could also be considered as a subtree of itself.

Example 1:
Given tree s:

     3
    / \
   4   5
  / \
 1   2
Given tree t:
   4 
  / \
 1   2
Return true, because t has the same structure and node values with a subtree of s.
 

Example 2:
Given tree s:

     3
    / \
   4   5
  / \
 1   2
    /
   0
Given tree t:
   4
  / \
 1   2
Return false.
*/



// Other's Solution:
class Solution {
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        // 默克尔树 - https://leetcode.com/problems/subtree-of-another-tree/solutions/1676456/merkle-tree-on/
        if (root == null) return false;
        addHash(root); addHash(subRoot);
        return rec(root, subRoot);
    }

    private int addHash(TreeNode node) {
        if (node == null) return "#".hashCode();
        node.val = node.val + addHash(node.left) + Integer.hashCode(node.val) + addHash(node.right);
        return node.val;
    }

    private boolean rec(TreeNode root, TreeNode subRoot) {
        if (root == null) return false;
        if (root.val == subRoot.val && isEqual(root, subRoot)) return true;
        return rec(root.left, subRoot) || rec(root.right, subRoot);
    }

    private boolean isEqual(TreeNode subroot1, TreeNode subroot2) {
        if (subroot1 == null || subroot2 == null) return subroot1 == null && subroot2 == null;
        else return subroot1.val == subroot2.val && isEqual(subroot1.left, subroot2.left) && isEqual(subroot1.right, subroot2.right);
    }
}



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
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null) return false;
        if (isSame(s, t)) return true;
        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }
    
    private boolean isSame(TreeNode s, TreeNode t) {
        if (s == null && t == null) return true;
        if (s == null || t == null) return false;
        
        if (s.val != t.val) return false;
        
        return isSame(s.left, t.left) && isSame(s.right, t.right);
    }
}