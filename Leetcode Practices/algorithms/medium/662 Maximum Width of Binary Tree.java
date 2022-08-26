/**
Given the root of a binary tree, return the maximum width of the given tree.

The maximum width of a tree is the maximum width among all levels.

The width of one level is defined as the length between the end-nodes (the leftmost and rightmost non-null nodes), where the null nodes between the end-nodes that would be present in a complete binary tree extending down to that level are also counted into the length calculation.

It is guaranteed that the answer will in the range of a 32-bit signed integer.

 

Example 1:
https://assets.leetcode.com/uploads/2021/05/03/width1-tree.jpg

Input: root = [1,3,2,5,3,null,9]
Output: 4
Explanation: The maximum width exists in the third level with length 4 (5,3,null,9).

Example 2:
https://assets.leetcode.com/uploads/2022/03/14/maximum-width-of-binary-tree-v3.jpg

Input: root = [1,3,2,5,null,null,9,6,null,7]
Output: 7
Explanation: The maximum width exists in the fourth level with length 7 (6,null,null,null,null,null,7).

Example 3:
https://assets.leetcode.com/uploads/2021/05/03/width3-tree.jpg

Input: root = [1,3,2,5]
Output: 2
Explanation: The maximum width exists in the second level with length 2 (3,2).
 

Constraints:

The number of nodes in the tree is in the range [1, 3000].
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
    public int widthOfBinaryTree(TreeNode root) {
        // BFS
        int res = 0;
        Deque<TreeNodeWrapper> queue = new LinkedList<>();
        if (root != null) queue.offer(new TreeNodeWrapper(1, root));
        
        while (!queue.isEmpty()) {
            int qSize = queue.size();
            res = Math.max(res, queue.peekLast().index - queue.peekFirst().index + 1);
            while (qSize-- > 0) {
                TreeNodeWrapper p = queue.poll();
                if (p.node.left != null) queue.offer(new TreeNodeWrapper(p.index * 2 - 1, p.node.left));
                if (p.node.right != null) queue.offer(new TreeNodeWrapper(p.index * 2, p.node.right));
            }
        }
        
        return res;
    }
    
    class TreeNodeWrapper {
        Integer index;
        TreeNode node;
        
        public TreeNodeWrapper(Integer index, TreeNode node) {
            this.index = index;
            this.node = node;
        }
    }
}
