/**
Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return the root node reference (possibly updated) of the BST.

Basically, the deletion can be divided into two stages:

1. Search for a node to remove.
2. If the node is found, delete the node.
 

Example 1:
https://assets.leetcode.com/uploads/2020/09/04/del_node_1.jpg

Input: root = [5,3,6,2,4,null,7], key = 3
Output: [5,4,6,2,null,null,7]
Explanation: Given key to delete is 3. So we find the node with value 3 and delete it.
One valid answer is [5,4,6,2,null,null,7], shown in the above BST.
Please notice that another valid answer is [5,2,6,null,4,null,7] and it's also accepted.
https://assets.leetcode.com/uploads/2020/09/04/del_node_supp.jpg

Example 2:

Input: root = [5,3,6,2,4,null,7], key = 0
Output: [5,3,6,2,4,null,7]
Explanation: The tree does not contain a node with value = 0.
Example 3:

Input: root = [], key = 0
Output: []
 

Constraints:

The number of nodes in the tree is in the range [0, 104].
-105 <= Node.val <= 105
Each node has a unique value.
root is a valid binary search tree.
-105 <= key <= 105
 

Follow up: Could you solve it with time complexity O(height of tree)?
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
    public TreeNode deleteNode(TreeNode root, int key) {
        // 中序遍历（前驱节点和后继节点）+ 递归 - https://leetcode.cn/problems/delete-node-in-a-bst/solution/shan-chu-er-cha-sou-suo-shu-zhong-de-jie-dian-by-l/
        // 时间复杂度：O(logN)，空间复杂度：O(H)
        if (root == null) return null;

        if (key > root.val) {
            root.right = deleteNode(root.right, key); // 从右子树中删除
        } else if (key < root.val) {
            root.left = deleteNode(root.left, key); // 从左子树中删除
        } else { // 删除当前节点
            if (root.left == null && root.right == null) { // 如果当前节点为叶子节点
                return null;
            } else if (root.right != null) { // 当前节点有右子节点
                root.val = inOrderNext(root);
                root.right = deleteNode(root.right, root.val);
            } else { // 当前节点只有左子节点
                root.val = inOrderLast(root);
                root.left = deleteNode(root.left, root.val);
            }
        }
        return root;
    }
    
    public int inOrderNext(TreeNode root) { // 后继节点值
        root = root.right;
        while (root.left != null) root = root.left;
        return root.val;
    }

    public int inOrderLast(TreeNode root) { // 前驱节点值
        root = root.left;
        while (root.right != null) root = root.right;
        return root.val;
    }
}
