/*
Given the root of a binary tree and an integer targetSum, return all root-to-leaf paths where the sum of the node values in the path equals targetSum. Each path should be returned as a list of the node values, not node references.

A root-to-leaf path is a path starting from the root and ending at any leaf node. A leaf is a node with no children.

 

Example 1:
https://assets.leetcode.com/uploads/2021/01/18/pathsumii1.jpg

Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
Output: [[5,4,11,2],[5,8,4,5]]
Explanation: There are two paths whose sum equals targetSum:
5 + 4 + 11 + 2 = 22
5 + 8 + 4 + 5 = 22

Example 2:
https://assets.leetcode.com/uploads/2021/01/18/pathsum2.jpg

Input: root = [1,2,3], targetSum = 5
Output: []

Example 3:

Input: root = [1,2], targetSum = 0
Output: []
 

Constraints:

The number of nodes in the tree is in the range [0, 5000].
-1000 <= Node.val <= 1000
-1000 <= targetSum <= 1000
*/





// My Solution:
class Solution {
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        /*
            回溯法 - 遍历所有可能的路径，若为叶节点且剩余 targetSum 等于当前 val 则 res 将添加当前路径
            时间复杂度 O(N)，空间复杂度 O(N)
        */
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        List<Integer> path = new ArrayList<>();
        path.add(root.val);
        if (root.val == targetSum && root.left == null && root.right == null) {
            res.add(new ArrayList<>(path));
        }
        if (root.left != null) pathSum(root.left, targetSum-root.val, path, res);
        if (root.right != null) pathSum(root.right, targetSum-root.val, path, res);
        return res;
    }
    
    public void pathSum(TreeNode root, int targetSum, List<Integer> path, List<List<Integer>> res) {
        path.add(root.val);
        if (root.val == targetSum && root.left == null && root.right == null) {
            res.add(new ArrayList<>(path));
        }
        if (root.left != null) pathSum(root.left, targetSum-root.val, path, res);
        if (root.right != null) pathSum(root.right, targetSum-root.val, path, res);
        path.remove(path.size()-1);
        return;
    }
}
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
