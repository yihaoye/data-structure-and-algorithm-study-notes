/*
Given the root of a binary tree, return the zigzag level order traversal of its nodes' values. (i.e., from left to right, then right to left for the next level and alternate between).

 

Example 1:
https://assets.leetcode.com/uploads/2021/02/19/tree1.jpg

Input: root = [3,9,20,null,null,15,7]
Output: [[3],[20,9],[15,7]]
Example 2:

Input: root = [1]
Output: [[1]]
Example 3:

Input: root = []
Output: []
 

Constraints:

The number of nodes in the tree is in the range [0, 2000].
-100 <= Node.val <= 100

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
    /*
        思路 - BFS，但是因为需要区分父节点层与子节点层，所以不能使用典型的一个队列，而是使用两个（一个放父节点层、一个放子节点层），每次遍历完父节点层就将其反转 reverse，然后再求子节点层，直到所求子节点层为空则退出 while 循环
        时间复杂度 O(N)，空间复杂度 O(1) ?
    */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        
        List<TreeNode> list1 = new ArrayList<>();
        List<TreeNode> list2 = new ArrayList<>();
        list1.add(root);
        boolean reverse = false; // reverse false means from left to right
        while (!list1.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            for (TreeNode node : list1) list.add(node.val);
            res.add(list);
            
            Collections.reverse(list1);
            reverse = !reverse;
            for (TreeNode node : list1) {
                if (reverse) {
                    if (node.right != null) list2.add(node.right);
                    if (node.left != null) list2.add(node.left);
                } else {
                    if (node.left != null) list2.add(node.left);
                    if (node.right != null) list2.add(node.right);
                }
            }
            list1.clear();
            list1.addAll(list2);
            list2.clear();
        }
        
        return res;
    }
}
