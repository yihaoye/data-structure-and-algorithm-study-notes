/*
We are playing the Guessing Game. The game will work as follows:

I pick a number between 1 and n.
You guess a number.
If you guess the right number, you win the game.
If you guess the wrong number, then I will tell you whether the number I picked is higher or lower, and you will continue guessing.
Every time you guess a wrong number x, you will pay x dollars. If you run out of money, you lose the game.
Given a particular n, return the minimum amount of money you need to guarantee a win regardless of what number I pick.

 

Example 1:
https://assets.leetcode.com/uploads/2020/09/10/graph.png

Input: n = 10
Output: 16
Explanation: The winning strategy is as follows:
- The range is [1,10]. Guess 7.
    - If this is my number, your total is $0. Otherwise, you pay $7.
    - If my number is higher, the range is [8,10]. Guess 9.
        - If this is my number, your total is $7. Otherwise, you pay $9.
        - If my number is higher, it must be 10. Guess 10. Your total is $7 + $9 = $16.
        - If my number is lower, it must be 8. Guess 8. Your total is $7 + $9 = $16.
    - If my number is lower, the range is [1,6]. Guess 3.
        - If this is my number, your total is $7. Otherwise, you pay $3.
        - If my number is higher, the range is [4,6]. Guess 5.
            - If this is my number, your total is $7 + $3 = $10. Otherwise, you pay $5.
            - If my number is higher, it must be 6. Guess 6. Your total is $7 + $3 + $5 = $15.
            - If my number is lower, it must be 4. Guess 4. Your total is $7 + $3 + $5 = $15.
        - If my number is lower, the range is [1,2]. Guess 1.
            - If this is my number, your total is $7 + $3 = $10. Otherwise, you pay $1.
            - If my number is higher, it must be 2. Guess 2. Your total is $7 + $3 + $1 = $11.
The worst case in all these scenarios is that you pay $16. Hence, you only need $16 to guarantee a win.
Example 2:

Input: n = 1
Output: 0
Explanation: There is only one possible number, so you can guess 1 and not have to pay anything.
Example 3:

Input: n = 2
Output: 1
Explanation: There are two possible numbers, 1 and 2.
- Guess 1.
    - If this is my number, your total is $0. Otherwise, you pay $1.
    - If my number is higher, it must be 2. Guess 2. Your total is $1.
The worst case is that you pay $1.
 

Constraints:

1 <= n <= 200
*/



// My Solution (DP):
class Solution {
    public int getMoneyAmount(int n) {
        /*
            思路 - DP，dp[i][j] 代表子数组 [i..j] 的胜利所需最小现金数，要求解的答案设为 dp[1][n]，dp[i][j] = min(max(dp[i][i+k-1], dp[i+k+1][j]) + i+k) 这里注意必须 i <= j，dp[i][i] = 0，dp[i][i+1] = i，dp[i][i+2] = min(max(dp[i][i+k-1], dp[i+k+1][i+2]) + i+k)，这里带来了另一个问题 -- dp[i+k][i+2] 还未计算得出，此时若去计算 dp[i+k][i+2] 则变成了递归，因此一个办法是先计算 j-i = 1 的所有情况，然后 j-i = 2 -> j-i = 3 -> ...如是类推，因为通过这样，计算 j-i 较高差值的情况时，其依赖的 dp 数据已准备好。
			时间复杂度 O(N^3)（3 重嵌套 for 循环，当最外循环 k 值较小/较大时，最内 min 内循环较小/较大，这两循环的时间复杂度即经典和公式 1+2+3+...+N = (N^2)/2，然后再乘以中间的循环 N，总时间复杂度为 (N^3)/2 = N^3，
            空间复杂度 O(N^2)
        */
        int[][] dp = new int[n+2][n+2];
        for (int k=1; k<=n-1; k++) {
            for (int i=1; i+k<=n; i++) {
                int j = i + k;
                if (k == 1) dp[i][j] = i;
                else dp[i][j] = min(dp, i, j);
            }
        }
        return dp[1][n];
    }
    
    public int min(int[][] dp, int i, int j) {
        int n = dp.length - 2;
        int res = n * n / 2; // (n-1+1)*n/2 = 1+2+...+n-1
        for (int k=0; k<=j-i; k++) { // select i+k
            res = Math.min(res, Math.max(dp[i][i+k-1], dp[i+k+1][j]) + (i+k));
        }
        return res;
    }
}
