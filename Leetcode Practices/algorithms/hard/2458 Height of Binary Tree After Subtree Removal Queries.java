/**
You are given the root of a binary tree with n nodes. Each node is assigned a unique value from 1 to n. You are also given an array queries of size m.

You have to perform m independent queries on the tree where in the ith query you do the following:

Remove the subtree rooted at the node with the value queries[i] from the tree. It is guaranteed that queries[i] will not be equal to the value of the root.
Return an array answer of size m where answer[i] is the height of the tree after performing the ith query.

Note:

The queries are independent, so the tree returns to its initial state after each query.
The height of a tree is the number of edges in the longest simple path from the root to some node in the tree.
 

Example 1:


Input: root = [1,3,4,2,null,6,5,null,null,null,null,null,7], queries = [4]
Output: [2]
Explanation: The diagram above shows the tree after removing the subtree rooted at node with value 4.
The height of the tree is 2 (The path 1 -> 3 -> 2).
Example 2:


Input: root = [5,8,9,2,1,3,7,4,6], queries = [3,2,4,8]
Output: [3,2,3,2]
Explanation: We have the following queries:
- Removing the subtree rooted at node with value 3. The height of the tree becomes 3 (The path 5 -> 8 -> 2 -> 4).
- Removing the subtree rooted at node with value 2. The height of the tree becomes 2 (The path 5 -> 8 -> 1).
- Removing the subtree rooted at node with value 4. The height of the tree becomes 3 (The path 5 -> 8 -> 2 -> 6).
- Removing the subtree rooted at node with value 8. The height of the tree becomes 2 (The path 5 -> 9 -> 3).
 

Constraints:

The number of nodes in the tree is n.
2 <= n <= 10^5
1 <= Node.val <= n
All the values in the tree are unique.
m == queries.length
1 <= m <= min(n, 10^4)
1 <= queries[i] <= n
queries[i] != root.val
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
    // https://leetcode.com/problems/height-of-binary-tree-after-subtree-removal-queries/discuss/2759353/C%2B%2BPython-Preoder-and-Postorder-DFS
    /*
        Pre-order dfs the tree (node, left, right),
        update res[i] to the max height before node i in preorder,
        can cover all nodes on the left of node i in the tree.

        Another pre-order dfs is similar but from right to left (node, right, left)

        Time: O(n), Space: O(n)
    */
    int[] maxHeightL2R = new int[100001]; // maxHeightL2R[i] means maxHeight found by L2R pre-order until node i and exclude node i，可以用哈希表替代
    int[] maxHeightR2L = new int[100001]; // maxHeightR2L[i] means maxHeight found by R2L pre-order until node i and exclude node i
    int maxH;
    
    public int[] treeQueries(TreeNode root, int[] queries) {
        maxH = 0; preorderL2R(root, 0);
        maxH = 0; preorderR2L(root, 0);
        
        int[] res = new int[queries.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = Math.max(maxHeightL2R[queries[i]], maxHeightR2L[queries[i]]);
        }
        return res;
    }
    
    private void preorderL2R(TreeNode root, int h) {
        if (root == null) return;
        maxHeightL2R[root.val] = maxH;
        maxH = Math.max(maxH, h);
        preorderL2R(root.left, h + 1);
        preorderL2R(root.right, h + 1);
    }
    
    private void preorderR2L(TreeNode root, int h) {
        if (root == null) return;
        maxHeightR2L[root.val] = maxH;
        maxH = Math.max(maxH, h);
        preorderR2L(root.right, h + 1);
        preorderR2L(root.left, h + 1);
    }
}
