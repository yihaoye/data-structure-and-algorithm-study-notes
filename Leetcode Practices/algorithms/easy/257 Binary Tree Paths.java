/**
Given the root of a binary tree, return all root-to-leaf paths in any order.

A leaf is a node with no children.

 

Example 1:
https://assets.leetcode.com/uploads/2021/03/12/paths-tree.jpg

Input: root = [1,2,3,null,5]
Output: ["1->2->5","1->3"]
Example 2:

Input: root = [1]
Output: ["1"]
 

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
    public List<String> binaryTreePaths(TreeNode root) {
        // DFS, when no children, add to list
        // Time: O(N^2), Space: O(logN)
        List<String> res = new ArrayList<>();
        dfs(res, root, new ArrayList<>());

        return res;
    }

    private void dfs(List<String> res, TreeNode root, List<Integer> tmp) {
        tmp.add(root.val);
        if (root.left == null && root.right == null) {
            String str = convert(tmp);
            res.add(str);
            tmp.remove(tmp.size()-1);
            return;
        } else {
            if (root.left != null) {
                dfs(res, root.left, tmp);
            }
            if (root.right != null) {
                dfs(res, root.right, tmp);
            }
            tmp.remove(tmp.size()-1);
        }
    }

    private String convert(List<Integer> tmp) {
        StringBuilder strB = new StringBuilder();
        strB.append(tmp.get(0));
        for (int i=1; i<tmp.size(); i++) {
            strB.append("->");
            strB.append(tmp.get(i));
        }
        return strB.toString();
    }
}
