/*
Given two integer arrays preorder and inorder where preorder is the preorder traversal of a binary tree and inorder is the inorder traversal of the same tree, construct and return the binary tree.

 

Example 1:
https://assets.leetcode.com/uploads/2021/02/19/tree.jpg

Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
Output: [3,9,20,null,null,15,7]
Example 2:

Input: preorder = [-1], inorder = [-1]
Output: [-1]
 

Constraints:

1 <= preorder.length <= 3000
inorder.length == preorder.length
-3000 <= preorder[i], inorder[i] <= 3000
preorder and inorder consist of unique values.
Each value of inorder also appears in preorder.
preorder is guaranteed to be the preorder traversal of the tree.
inorder is guaranteed to be the inorder traversal of the tree.
*/



// Other's Solution:
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        /*
            没思路看题解 - 前序遍历中最左为根，以此定位其在中序遍历中的索引，在该索引的左边皆是根左子树的节点，右边皆是根右子树的节点，后面以此类推递归求解（https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/discuss/34538/My-Accepted-Java-Solution）
            时间复杂度 O(N)，空间复杂度 O(N) - 其中栈部分最理想是 O(logN)
        */
        return helper(0, 0, inorder.length - 1, preorder, inorder);
    }

    public TreeNode helper(int preStart, int inStart, int inEnd, int[] preorder, int[] inorder) {
        if (preStart > preorder.length - 1 || inStart > inEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[preStart]);
        int inIndex = 0; // Index of current root in inorder
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == root.val) {
                inIndex = i;
            }
        }
        root.left = helper(preStart + 1, inStart, inIndex - 1, preorder, inorder);
        root.right = helper(preStart + inIndex - inStart + 1, inIndex + 1, inEnd, preorder, inorder); // 关于右子树的 preStart 解释：https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/discuss/34538/My-Accepted-Java-Solution/32854
        return root;
    }
}

// 上面的解法可以用哈希表对 helper 里的 for 循环优化
class Solution {
    Map<Integer, Integer> inMap = new HashMap<>();
    
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for (int i=0; i<inorder.length; i++) inMap.put(inorder[i], i);
        return helper(0, 0, inorder.length - 1, preorder, inorder);
    }

    public TreeNode helper(int preStart, int inStart, int inEnd, int[] preorder, int[] inorder) {
        if (preStart > preorder.length - 1 || inStart > inEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[preStart]);
        int inIndex = inMap.get(root.val); // Index of current root in inorder

        root.left = helper(preStart + 1, inStart, inIndex - 1, preorder, inorder);
        root.right = helper(preStart + inIndex - inStart + 1, inIndex + 1, inEnd, preorder, inorder);
        return root;
    }
}

// 递归复制子数组写法，性能没那么好但更直白少出错
class Solution {    
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0) return null;
        TreeNode root = new TreeNode(preorder[0]);
        
        int inIndex = -1;
        for (int i=0; i<inorder.length; i++) if (inorder[i] == preorder[0]) inIndex = i;
        
        root.left = buildTree(Arrays.copyOfRange(preorder, 1, inIndex+1), Arrays.copyOfRange(inorder, 0, inIndex));
        root.right = buildTree(Arrays.copyOfRange(preorder, inIndex+1, preorder.length), Arrays.copyOfRange(inorder, inIndex+1, inorder.length));
        return root;
    }
}

// Follow Up
// 给定一个二叉树的前序遍历和中序遍历，要求返回反转以后的前序遍历，可以在不重构树的情况下达成（即之前递归构建树时，不是返回构建子树节点，而是返回子树的反转的前序遍历，并且是右边数组为左子树的反转前序遍历，另一边也一样）
class Solution {    
    public List<Integer> reversePreOrder(int[] preorder, int[] inorder) {
        List<Integer> res = new ArrayList<>();
        if (preorder.length == 0) return res;
        res.add(preorder[0]);
        
        int inIndex = -1;
        for (int i=0; i<inorder.length; i++) if (inorder[i] == preorder[0]) inIndex = i;
        
        res.addAll(reversePreOrder(Arrays.copyOfRange(preorder, inIndex+1, preorder.length), Arrays.copyOfRange(inorder, inIndex+1, inorder.length))); // new left（先加的就是新左）use old right subtree's preorder and inorder
        res.addAll(reversePreOrder(Arrays.copyOfRange(preorder, 1, inIndex+1), Arrays.copyOfRange(inorder, 0, inIndex))); // new right（后加的就是新右）use old left subtree's preorder and inorder
        return res;
    }
}
