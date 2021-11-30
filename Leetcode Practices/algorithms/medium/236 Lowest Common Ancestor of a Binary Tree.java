/*
Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
 

Example 1:
https://assets.leetcode.com/uploads/2018/12/14/binarytree.png

Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.

Example 2:
https://assets.leetcode.com/uploads/2018/12/14/binarytree.png

Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.

Example 3:

Input: root = [1,2], p = 1, q = 2
Output: 1
 

Constraints:

The number of nodes in the tree is in the range [2, 105].
-109 <= Node.val <= 109
All Node.val are unique.
p != q
p and q will exist in the tree.
*/



// My Solution:
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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        /*
            思路 - DFS，各自搜索 p 和 q 并返回完整顺序路径，一一对比直到发现节点不相等
            时间复杂度 O(logN)，空间复杂度 O(logN)
        */
        List<TreeNode> pList = dfs(root, p);
        List<TreeNode> qList = dfs(root, q);
        Collections.reverse(pList);
        Collections.reverse(qList);
        int len = Math.min(pList.size(), qList.size()), resIndex = -1;
        for (int i=0; i<len; i++) {
            if (pList.get(i) != qList.get(i)) {
                resIndex = i-1;
                break;
            }
        }
        if (resIndex == -1) resIndex = len-1;
        return pList.get(resIndex);
    }
    
    public List<TreeNode> dfs(TreeNode root, TreeNode target) {
        List<TreeNode> res = new ArrayList<>();
        if (root == null) return res;
        if (root == target) {
            res.add(root);
            return res;
        }
        List<TreeNode> left = dfs(root.left, target);
        if (!left.isEmpty()) {
            res.addAll(left);
            res.add(root);
            return res;
        }
        List<TreeNode> right = dfs(root.right, target);
        if (!right.isEmpty()) {
            res.addAll(right);
            res.add(root);
            return res;
        }
        return res;
    }
}
