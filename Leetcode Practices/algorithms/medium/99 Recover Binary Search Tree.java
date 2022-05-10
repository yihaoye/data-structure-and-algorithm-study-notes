/**
You are given the root of a binary search tree (BST), where the values of exactly two nodes of the tree were swapped by mistake. Recover the tree without changing its structure.

 

Example 1:
https://assets.leetcode.com/uploads/2020/10/28/recover1.jpg

Input: root = [1,3,null,null,2]
Output: [3,1,null,null,2]
Explanation: 3 cannot be a left child of 1 because 3 > 1. Swapping 1 and 3 makes the BST valid.

Example 2:
https://assets.leetcode.com/uploads/2020/10/28/recover2.jpg

Input: root = [3,1,4,null,null,2]
Output: [2,1,4,null,null,3]
Explanation: 2 cannot be in the right subtree of 3 because 2 < 3. Swapping 2 and 3 makes the BST valid.
 

Constraints:

The number of nodes in the tree is in the range [2, 1000].
-231 <= Node.val <= 231 - 1
 

Follow up: A solution using O(n) space is pretty straight-forward. Could you devise a constant O(1) space solution?
 */



// Other's Solution:
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
    TreeNode x, y, pred; // 维护当前中序遍历到的最后一个节点 pred，然后在遍历到下一个节点的时候，看两个节点的值是否满足前者小于后者即可，如果不满足说明找到了一个交换的节点，且在找到两次以后就可以终止遍历。这样就可以在中序遍历中直接找到被错误交换的两个节点 x 和 y。
    
    public void recoverTree(TreeNode root) {
        // Morris 中序遍历 - https://leetcode-cn.com/problems/recover-binary-search-tree/solution/hui-fu-er-cha-sou-suo-shu-by-leetcode-solution/
        // 本方法内基本就是 Morris 中序遍历的模版，除了 swap(x, y) 是模版之外的（process(root) 内的逻辑则根据情况自定义即可）。
        // Time: O(2*N) = O(N), Space: O(1)
        TreeNode tmp = null;

        while (root != null) {
            if (root.left != null) {
                tmp = root.left; // tmp 节点就是当前 root 节点向左走一步，然后一直向右走至无法走为止（后面的 while 要找到以 root.left 为根节点的左子树的最右节点）
                while (tmp.right != null && tmp.right != root) {
                    tmp = tmp.right;
                }
                
                if (tmp.right == null) { // 为空说明是第一次访问，让 tmp 的右指针指向 root，继续遍历左子树
                    tmp.right = root;
                    root = root.left;
                } else { // 最右节点不空，说明访问过了（且以 root.left 为根节点的左子树已经访问完了），要断开，并 process 当前 root 节点
                    process(root);
                    tmp.right = null;
                    root = root.right;
                }
            } else { // 如果没有左孩子，则直接访问右孩子
                process(root);
                root = root.right;
            }
        }
        
        swap(x, y);
    }
    
    public void process(TreeNode curRoot) {
        if (pred != null && curRoot.val < pred.val) {
            y = curRoot;
            if (x == null) x = pred;
        }
        pred = curRoot;
    }

    public void swap(TreeNode x, TreeNode y) {
        int tmp = x.val;
        x.val = y.val;
        y.val = tmp;
    }
}
