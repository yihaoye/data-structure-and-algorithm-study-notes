/**
Given the root of a binary tree, return the length of the longest consecutive path in the tree.

A consecutive path is a path where the values of the consecutive nodes in the path differ by one. This path can be either increasing or decreasing.

For example, [1,2,3,4] and [4,3,2,1] are both considered valid, but the path [1,2,4,3] is not valid.
On the other hand, the path can be in the child-Parent-child order, where not necessarily be parent-child order.

 

Example 1:
https://assets.leetcode.com/uploads/2021/03/14/consec2-1-tree.jpg

Input: root = [1,2,3]
Output: 2
Explanation: The longest consecutive path is [1, 2] or [2, 1].

Example 2:
https://assets.leetcode.com/uploads/2021/03/14/consec2-2-tree.jpg

Input: root = [2,1,3]
Output: 3
Explanation: The longest consecutive path is [1, 2, 3] or [3, 2, 1].
 

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
    public int longest = 0;
    
    public int longestConsecutive(TreeNode root) {
        // DFS
        if (root != null) dfs(root);
        
        return longest;
    }
    
    public int[] dfs(TreeNode node) {
        int l2rInc = 1, l2rDec = 1;
        int[] curLongest = new int[]{0, 0}; // {d2u_inc_longest, d2u_dec_longest}
        int[] leftLongest = new int[]{0, 0};
        int[] rightLongest = new int[]{0, 0};
        if (node.left != null) {
            int[] tmpLongest = dfs(node.left);
            if (Math.abs(node.val - node.left.val) == 1) {
                leftLongest = tmpLongest;
                if (node.val - node.left.val == 1) {
                    l2rInc += leftLongest[0];
                    curLongest[0] = Math.max(curLongest[0], leftLongest[0]);
                }
                if (node.val - node.left.val == -1) {
                    l2rDec += leftLongest[1];
                    curLongest[1] = Math.max(curLongest[1], leftLongest[1]);
                }
            }
        }
        if (node.right != null) {
            int[] tmpLongest = dfs(node.right);
            if (Math.abs(node.val - node.right.val) == 1) {
                rightLongest = tmpLongest;
                if (node.val - node.right.val == 1) {
                    l2rDec += rightLongest[0];
                    curLongest[0] = Math.max(curLongest[0], rightLongest[0]);
                }
                if (node.val - node.right.val == -1) {
                    l2rInc += rightLongest[1];
                    curLongest[1] = Math.max(curLongest[1], rightLongest[1]);
                }
            }
        }
        longest = Math.max(longest, l2rInc);
        longest = Math.max(longest, l2rDec);
        
        curLongest[0] += 1;
        curLongest[1] += 1;
        
        return curLongest;
    }
}
