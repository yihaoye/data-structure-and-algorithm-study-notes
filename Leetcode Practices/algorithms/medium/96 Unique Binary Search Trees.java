/**
Given an integer n, return the number of structurally unique BST's (binary search trees) which has exactly n nodes of unique values from 1 to n.

 

Example 1:
https://assets.leetcode.com/uploads/2021/01/18/uniquebstn3.jpg

Input: n = 3
Output: 5

Example 2:

Input: n = 1
Output: 1
 

Constraints:

1 <= n <= 19
 */



// My Solution:
class Solution {
    public int numTrees(int n) {
        // 中序遍历的组成方式，可以递归计算
        // 即中序遍历总是一样的，但是选择的根节点可以不一样，假设如下
        // 1 2 3 [4] 5 6 7 为 以 4 为根节点中序遍历有多少种组成方式？此时递归选择左右子树根节点
        // [1] 2 3 [[4]] 5 [6] 7 即其中一种可能，左子树以 1 为根节点，右子树以 6 为根节点
        // 计算总共有多少种这种组合即可（从 1 到 7 为根节点，每次左子树组合数 * 右子树组合数的总和）
        // 除了自上而下的递归之外，其实更优化的方法可以是用自下而上的 DP，类似斐波那契数列计算
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i=2; i<=n; i++) {
            for (int j=0; j<i; j++) {
                dp[i] += dp[j] * dp[i-1-j];
            }
        }
        return dp[n];
    }
}
