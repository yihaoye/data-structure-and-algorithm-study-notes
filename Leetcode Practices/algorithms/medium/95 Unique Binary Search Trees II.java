/**
Given an integer n, return all the structurally unique BST's (binary search trees), which has exactly n nodes of unique values from 1 to n. Return the answer in any order.

 

Example 1:
https://assets.leetcode.com/uploads/2021/01/18/uniquebstn3.jpg

Input: n = 3
Output: [[1,null,2,null,3],[1,null,3,2],[2,1,3],[3,1,null,null,2],[3,2,null,1]]

Example 2:

Input: n = 1
Output: [[1]]
 

Constraints:

1 <= n <= 8

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
    public List<TreeNode> generateTrees(int n) {
        /*
            递归 - https://leetcode-cn.com/problems/unique-binary-search-trees-ii/solution/bu-tong-de-er-cha-sou-suo-shu-ii-by-leetcode-solut/
            时间复杂度：整个算法的时间复杂度取决于「可行二叉搜索树的个数」，而对于 n 个点生成的二叉搜索树数量等价于数学上第 n 个「卡特兰数」，用 Gn 表示。卡特兰数具体的细节请读者自行查询，这里不再赘述，只给出结论。生成一棵二叉搜索树需要 O(n) 的时间复杂度，一共有 Gn 棵二叉搜索树，也就是 O(n*Gn)。而卡特兰数以 (4^n)/(n^(3/2)) 增长，因此总时间复杂度为 O((4^n)/(n^(1/2)))。
            空间复杂度：n 个点生成的二叉搜索树有 Gn 棵，每棵有 n 个节点，因此存储的空间需要 O(n*Gn) = O((4^n)/(n^(1/2)))，递归函数需要 O(n) 的栈空间，因此总空间复杂度为 O((4^n)/(n^(1/2)))。
        */
        if (n == 0) {
            return new LinkedList<TreeNode>();
        }
        return generateTrees(1, n);
    }

    public List<TreeNode> generateTrees(int start, int end) {
        List<TreeNode> allTrees = new LinkedList<TreeNode>();
        if (start > end) {
            allTrees.add(null);
            return allTrees;
        }

        // 枚举可行根节点
        for (int i=start; i<=end; i++) {
            // 获得所有可行的左子树集合
            List<TreeNode> leftTrees = generateTrees(start, i-1);

            // 获得所有可行的右子树集合
            List<TreeNode> rightTrees = generateTrees(i+1, end);

            // 从左子树集合中选出一棵左子树，从右子树集合中选出一棵右子树，拼接到根节点上
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    TreeNode currTree = new TreeNode(i);
                    currTree.left = left;
                    currTree.right = right;
                    allTrees.add(currTree);
                }
            }
        }
        return allTrees;
    }
}
