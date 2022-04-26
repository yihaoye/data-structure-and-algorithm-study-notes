/**
Given two integer arrays inorder and postorder where inorder is the inorder traversal of a binary tree and postorder is the postorder traversal of the same tree, construct and return the binary tree.

 

Example 1:
https://assets.leetcode.com/uploads/2021/02/19/tree.jpg

Input: inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
Output: [3,9,20,null,null,15,7]

Example 2:

Input: inorder = [-1], postorder = [-1]
Output: [-1]
 

Constraints:

1 <= inorder.length <= 3000
postorder.length == inorder.length
-3000 <= inorder[i], postorder[i] <= 3000
inorder and postorder consist of unique values.
Each value of postorder also appears in inorder.
inorder is guaranteed to be the inorder traversal of the tree.
postorder is guaranteed to be the postorder traversal of the tree.
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
    Map<Integer, Integer> inMap = new HashMap<>();

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        // 后序遍历中最右为根，以此定位其在中序遍历中的索引，在该索引的左边皆是根左子树的节点，右边皆是根右子树的节点，后面以此类推递归求解
        // helper 里的 inorder[inStart...inEnd] 与 postorder[postStart...postEnd] 即是同一子树的中序与后序遍历
        for (int i=0; i<inorder.length; i++) inMap.put(inorder[i], i);
        return helper(inorder, postorder, 0, inorder.length-1, 0, postorder.length-1);
    }

    public TreeNode helper(int[] inorder, int[] postorder, int inStart, int inEnd, int postStart, int postEnd){
        if (postStart > postEnd) return null;

        TreeNode node = new TreeNode(postorder[postEnd]);
        int inIndex = inMap.get(postorder[postEnd]);

        node.left = helper(inorder, postorder, inStart, inIndex-1, postStart, postStart-1+inIndex-inStart);
        node.right = helper(inorder, postorder, inIndex+1, inEnd, postEnd-(inEnd-inIndex), postEnd-1);
        return node;
    }
}
