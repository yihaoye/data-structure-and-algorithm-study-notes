/**
Given the root of a binary tree and two integers val and depth, add a row of nodes with value val at the given depth depth.

Note that the root node is at depth 1.

The adding rule is:

Given the integer depth, for each not null tree node cur at the depth depth - 1, create two tree nodes with value val as cur's left subtree root and right subtree root.
cur's original left subtree should be the left subtree of the new left subtree root.
cur's original right subtree should be the right subtree of the new right subtree root.
If depth == 1 that means there is no depth depth - 1 at all, then create a tree node with value val as the new root of the whole original tree, and the original tree is the new root's left subtree.
 

Example 1:
https://assets.leetcode.com/uploads/2021/03/15/addrow-tree.jpg

Input: root = [4,2,6,3,1,5], val = 1, depth = 2
Output: [4,1,1,2,null,null,6,3,1,5]

Example 2:
https://assets.leetcode.com/uploads/2021/03/11/add2-tree.jpg

Input: root = [4,2,null,3,1], val = 1, depth = 3
Output: [4,2,null,1,1,3,null,null,1]
 

Constraints:

The number of nodes in the tree is in the range [1, 10^4].
The depth of the tree is in the range [1, 10^4].
-100 <= Node.val <= 100
-10^5 <= val <= 10^5
1 <= depth <= the depth of tree + 1
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
    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        // BFS
        if (depth == 1) return new TreeNode(val, root, null);
        
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) queue.offer(root);
        int lvl = 1;
        while (!queue.isEmpty()) {
            if (lvl == depth-1) {
                while (!queue.isEmpty()) {
                    TreeNode curNode = queue.poll();
                    TreeNode insertLeft = new TreeNode(val, curNode.left, null);
                    TreeNode insertRight = new TreeNode(val, null, curNode.right);
                    curNode.left = insertLeft;
                    curNode.right = insertRight;
                }
                break;
            }
            
            int currSize = queue.size();
            while (currSize-- > 0) {
                TreeNode curNode = queue.poll();
                if (curNode.left != null) queue.offer(curNode.left);
                if (curNode.right != null) queue.offer(curNode.right);
            }
            lvl++;
        }
        
        return root;
    }
}
