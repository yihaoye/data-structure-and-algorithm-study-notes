/*
Given the root of a binary tree, each node in the tree has a distinct value.

After deleting all nodes with a value in to_delete, we are left with a forest (a disjoint union of trees).

Return the roots of the trees in the remaining forest.  You may return the result in any order.


Example 1:
![](https://assets.leetcode.com/uploads/2019/07/01/screen-shot-2019-07-01-at-53836-pm.png)

Input: root = [1,2,3,4,5,6,7], to_delete = [3,5]
Output: [[1,2,null,4],[6],[7]]
 

Constraints:
The number of nodes in the given tree is at most 1000.
Each node has a distinct value between 1 and 1000.
to_delete.length <= 1000
to_delete contains distinct values between 1 and 1000.
*/



// My Solution 1:
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
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        List<TreeNode> list = new ArrayList<TreeNode>();
        if (!traversal(list, root, to_delete)) list.add(root);
        
        return list;
    }
    
    public boolean traversal(List<TreeNode> list, TreeNode root, int[] to_delete) {
        if (root == null) return false;
        if (traversal(list, root.left, to_delete)) root.left = null;
        if (traversal(list, root.right, to_delete)) root.right = null;
        return delete(list, root, to_delete);
    }
    
    public boolean delete(List<TreeNode> list, TreeNode root, int[] to_delete) {
        for (int i=0; i<to_delete.length; i++) {
            if (root.val == to_delete[i]) {
                to_delete[i] = -1;
                if (root.left != null) {
                    list.add(root.left);
                    root.left = null;
                }
                if (root.right != null) {
                    list.add(root.right);
                    root.right = null;
                }
                return true;
            }
        }
        return false;
    }
}