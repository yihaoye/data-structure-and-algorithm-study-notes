/**
Given the root of a binary tree, collect a tree's nodes as if you were doing this:

Collect all the leaf nodes.
Remove all the leaf nodes.
Repeat until the tree is empty.
 

Example 1:
https://assets.leetcode.com/uploads/2021/03/16/remleaves-tree.jpg

Input: root = [1,2,3,4,5]
Output: [[4,5,3],[2],[1]]
Explanation:
[[3,5,4],[2],[1]] and [[3,4,5],[2],[1]] are also considered correct answers since per each level it does not matter the order on which elements are returned.

Example 2:

Input: root = [1]
Output: [[1]]
 

Constraints:

The number of nodes in the tree is in the range [1, 100].
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
    List<List<Integer>> res = new ArrayList<>();
    
    public List<List<Integer>> findLeaves(TreeNode root) {
        // 中序遍历，如果 dfs 返回 0 则说明是最底叶子节点，越往上一层加 1
        // Time: O(N), Space: O(h)
        if (root != null) dfs(root);
        
        return res;
    }
    
    public int dfs(TreeNode root) {
        int layer = 0;                
        if (root.left != null) layer = Math.max(layer, dfs(root.left) + 1);
        if (root.right != null) layer = Math.max(layer, dfs(root.right) + 1);
        
        if (res.size()-1 < layer) res.add(new ArrayList<>());
        res.get(layer).add(root.val);
        return layer;
    }
}
