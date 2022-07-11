/**
Given the root of a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.

 

Example 1:
https://assets.leetcode.com/uploads/2021/02/14/tree.jpg

Input: root = [1,2,3,null,5,null,4]
Output: [1,3,4]

Example 2:

Input: root = [1,null,3]
Output: [1,3]

Example 3:

Input: root = []
Output: []
 

Constraints:

The number of nodes in the tree is in the range [0, 100].
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
    public List<Integer> rightSideView(TreeNode root) {
        // BFS + 队列替换与垃圾收集
        // Time: O(N), Space: O(N)
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> q = new LinkedList<>();
        if (root != null) q.offer(root);
        while (!q.isEmpty()) {
            res.add(q.peekLast().val);
            Deque<TreeNode> tmpQ = new LinkedList<>();
            while (!q.isEmpty()) {
                TreeNode preNode = q.poll();
                if (preNode.left != null) tmpQ.offer(preNode.left);
                if (preNode.right != null) tmpQ.offer(preNode.right);
            }
            q = tmpQ;
        }
        return res;
    }
}
