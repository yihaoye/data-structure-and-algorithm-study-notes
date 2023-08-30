/*
Given a binary tree root, return the maximum sum of all keys of any sub-tree which is also a Binary Search Tree (BST).

Assume a BST is defined as follows:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
 

Example 1:



Input: root = [1,4,3,2,4,2,5,null,null,null,null,null,null,4,6]
Output: 20
Explanation: Maximum sum in a valid Binary search tree is obtained in root node with key equal to 3.
Example 2:



Input: root = [4,3,null,1,2]
Output: 2
Explanation: Maximum sum in a valid Binary search tree is obtained in a single root node with key equal to 2.
Example 3:

Input: root = [-4,-2,-5]
Output: 0
Explanation: All values are negatives. Return an empty BST.
 

Constraints:

The number of nodes in the tree is in the range [1, 4 * 10^4].
-4 * 10^4 <= Node.val <= 4 * 10^4
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
    private int max = 0;

    public int maxSumBST(TreeNode root) {
        // DFS + BST
        dfs(root);
        return max;
    }

    public int[] dfs(TreeNode node) { // [isBST, sumOfBST, maxVal, minVal] - isBST == 1 means yes else no
        int[] leftRes = new int[]{1, 0, Integer.MIN_VALUE, Integer.MAX_VALUE}; // if node.left is null
        int[] rightRes = new int[]{1, 0, Integer.MIN_VALUE, Integer.MAX_VALUE}; // if node.right is null
        if (node.left != null) leftRes = dfs(node.left);
        if (node.right != null) rightRes = dfs(node.right);
        int[] res = new int[]{0, node.val + leftRes[1] + rightRes[1], node.val, node.val};
        if (leftRes[0] == 1 && rightRes[0] == 1 && leftRes[2] < node.val && rightRes[3] > node.val) {
            res[0] = 1;
            max = Math.max(max, res[1]);
        }
        int childMax = Math.max(leftRes[2], rightRes[2]);
        res[2] = Math.max(res[2], childMax);
        int childMin = Math.min(leftRes[3], rightRes[3]);
        res[3] = Math.min(res[3], childMin);
        return res;
    }
}
