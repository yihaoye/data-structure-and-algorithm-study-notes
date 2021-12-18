/*
Given a binary tree, determine if it is a valid binary search tree (BST).

Assume a BST is defined as follows:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
 

Example 1:

    2
   / \
  1   3

Input: [2,1,3]
Output: true
Example 2:

    5
   / \
  1   4
     / \
    3   6

Input: [5,1,4,null,null,3,6]
Output: false
Explanation: The root node's value is 5 but its right child's value is 4.
*/



// Other's Solution (中序遍历):
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
    private TreeNode prev;
    public boolean isValidBST(TreeNode root) {
        prev = null;
        return inOrder(root);
    }
    
    private boolean inOrder(TreeNode root) {
        if (root == null) return true;
        if (!inOrder(root.left)) return false;
        if (prev != null && root.val <= prev.val) return false;
        prev = root;
        return inOrder(root.right);
    }
}
// 参考：http://zxi.mytechroad.com/blog/tree/leetcode-98-validate-binary-search-tree/



// My Solution:
class Solution {
    public boolean isValidBST(TreeNode root) {
        /*
            中序遍历 DFS，然后检查 List 是否顺序即可
            时间复杂度 O(N)，空间复杂度 O(h) 栈
        */
        List<Integer> inorderList = new ArrayList<>();
        if (root != null) inorder(root, inorderList);
        if (inorderList.size() <= 1) return true;
        for (int i=1; i<inorderList.size(); i++) {
            if (inorderList.get(i) <= inorderList.get(i-1)) {
                return false;
            }
        }
        
        return true;
    }
    
    public void inorder(TreeNode node, List<Integer> inorderList) {
        if (node.left != null) inorder(node.left, inorderList);
        inorderList.add(node.val);
        if (node.right != null) inorder(node.right, inorderList);
        return;
    }
}
