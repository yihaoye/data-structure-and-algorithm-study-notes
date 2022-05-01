/**
Given the root of a binary tree, turn the tree upside down and return the new root.

You can turn a binary tree upside down with the following steps:

1. The original left child becomes the new root.
2. The original root becomes the new right child.
3. The original right child becomes the new left child.

https://assets.leetcode.com/uploads/2020/08/29/main.jpg
The mentioned steps are done level by level. It is guaranteed that every right node has a sibling (a left node with the same parent) and has no children.
 

Example 1:
https://assets.leetcode.com/uploads/2020/08/29/updown.jpg

Input: root = [1,2,3,4,5]
Output: [4,5,2,null,null,3,1]

Example 2:

Input: root = []
Output: []

Example 3:

Input: root = [1]
Output: [1]
 

Constraints:

The number of nodes in the tree will be in the range [0, 10].
1 <= Node.val <= 10
Every right node in the tree has a sibling (a left node that shares the same parent).
Every right node in the tree has no children.
 */



// Other's Solution:
class Solution {
    TreeNode head = null; // 记录反转后的头节点
    
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        // 规律：对于 root, l = root.left, r = root.right 来说，经过变换之后，l.left = r, l.right = root , root.left = null, root.right = null。对于此规律，使用前序遍历，自底向上处理左子节点，在处理过程中，记录父亲节点以及该字节点的右节点方便进行持续遍历。整棵树遍历完成后，翻转成功 - https://leetcode-cn.com/problems/binary-tree-upside-down/solution/java-qian-xu-bian-li-zi-di-xiang-ding-di-e2ps/
        // Time: O(N), Space: O(logN)
        dfs(root, null); // 对于 root 来说，父节点为 null
        return head;
    }

    public void dfs(TreeNode root, TreeNode pre) {
        if (root == null) return;
        dfs(root.left, root); // 前序遍历，先往左走（右节点没必要遍历，题目保证了每个右节点都没有子结点）
        if (head == null) head = root; // head 置为最左边的节点

        if (pre == null) return;
        pre.left = null; // 父节点的 left 置为 null（为防止 Error - Found cycle in the TreeNode 报错），不会对遍历过程造成影响，因为左边已经全部遍历完成了
        root.left = pre.right; // 当前节点左节点置为父节点的右节点
        pre.right = null; // 父节点的 right 置为 null（为防止 Error - Found cycle in the TreeNode 报错），不会对遍历过程造成影响，因为上一行已使用了 pre.right、将 pre.right 给 root.left，而 pre.right 将在上层 dfs 中赋予新值
        root.right = pre; // 当前节点右节点置为父节点
    }
}
