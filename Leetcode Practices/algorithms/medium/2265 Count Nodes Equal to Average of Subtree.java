/**
Given the root of a binary tree, return the number of nodes where the value of the node is equal to the average of the values in its subtree.

Note:

The average of n elements is the sum of the n elements divided by n and rounded down to the nearest integer.
A subtree of root is a tree consisting of root and all of its descendants.
 

Example 1:
https://assets.leetcode.com/uploads/2022/03/15/image-20220315203925-1.png

Input: root = [4,8,5,0,1,null,6]
Output: 5
Explanation: 
For the node with value 4: The average of its subtree is (4 + 8 + 5 + 0 + 1 + 6) / 6 = 24 / 6 = 4.
For the node with value 5: The average of its subtree is (5 + 6) / 2 = 11 / 2 = 5.
For the node with value 0: The average of its subtree is 0 / 1 = 0.
For the node with value 1: The average of its subtree is 1 / 1 = 1.
For the node with value 6: The average of its subtree is 6 / 1 = 6.

Example 2:
https://assets.leetcode.com/uploads/2022/03/26/image-20220326133920-1.png

Input: root = [1]
Output: 1
Explanation: For the node with value 1: The average of its subtree is 1 / 1 = 1.
 

Constraints:

The number of nodes in the tree is in the range [1, 1000].
0 <= Node.val <= 1000
 */



// My Solution: (after read hint)
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
    int res = 0;
    
    public int averageOfSubtree(TreeNode root) {
        // Recursive
        // Time: O(N), Space: O(logN) ~ O(N)
        if (root != null) recursive(root);
        
        return res;
    }
    
    public int[] recursive(TreeNode node) {
        int sum = node.val, count = 1;
        if (node.left != null) {
            int[] leftSubData = recursive(node.left);
            sum += leftSubData[0];
            count += leftSubData[1];
        }
        if (node.right != null) {
            int[] rightSubData = recursive(node.right);
            sum += rightSubData[0];
            count += rightSubData[1];
        }
        
        if (node.val == (sum / count)) res++;
        return new int[]{sum, count};
    }
}
