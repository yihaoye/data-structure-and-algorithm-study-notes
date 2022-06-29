/**
Given the root of a binary tree, the value of a target node target, and an integer k, return an array of the values of all nodes that have a distance k from the target node.

You can return the answer in any order.

 

Example 1:
https://s3-lc-upload.s3.amazonaws.com/uploads/2018/06/28/sketch0.png

Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2
Output: [7,4,1]
Explanation: The nodes that are a distance 2 from the target node (with value 5) have values 7, 4, and 1.

Example 2:

Input: root = [1], target = 1, k = 3
Output: []
 

Constraints:

The number of nodes in the tree is in the range [1, 500].
0 <= Node.val <= 500
All the values Node.val are unique.
target is the value of one of the nodes in the tree.
0 <= k <= 1000
 */



// Other's Solution:
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
    Map<Integer, TreeNode> parents = new HashMap<Integer, TreeNode>();
    List<Integer> res = new ArrayList<Integer>();

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        // DFS + HashMap - https://leetcode.cn/problems/all-nodes-distance-k-in-binary-tree/solution/er-cha-shu-zhong-suo-you-ju-chi-wei-k-de-qbla/
        // Time: O(N), Space: O(N)
        recordParents(root); // 从 root 出发 DFS，记录每个结点的父结点
        findRes(target, null, 0, k); // 从 target 出发 DFS，寻找所有深度为 k 的结点

        return res;
    }

    public void recordParents(TreeNode node) {
        if (node.left != null) {
            parents.put(node.left.val, node);
            recordParents(node.left);
        }
        if (node.right != null) {
            parents.put(node.right.val, node);
            recordParents(node.right);
        }
    }

    public void findRes(TreeNode node, TreeNode from, int depth, int k) {
        if (node == null) return;

        if (depth == k) {
            res.add(node.val);
            return;
        }
        if (node.left != from) findRes(node.left, node, depth + 1, k);
        if (node.right != from) findRes(node.right, node, depth + 1, k);
        if (parents.get(node.val) != from) findRes(parents.get(node.val), node, depth + 1, k);
    }
}
