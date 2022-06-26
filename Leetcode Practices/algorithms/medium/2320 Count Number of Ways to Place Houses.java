/**
There is a street with n * 2 plots, where there are n plots on each side of the street. The plots on each side are numbered from 1 to n. On each plot, a house can be placed.

Return the number of ways houses can be placed such that no two houses are adjacent to each other on the same side of the street. Since the answer may be very large, return it modulo 109 + 7.

Note that if a house is placed on the ith plot on one side of the street, a house can also be placed on the ith plot on the other side of the street.

 

Example 1:

Input: n = 1
Output: 4
Explanation: 
Possible arrangements:
1. All plots are empty.
2. A house is placed on one side of the street.
3. A house is placed on the other side of the street.
4. Two houses are placed, one on each side of the street.

Example 2:
https://assets.leetcode.com/uploads/2022/05/12/arrangements.png

Input: n = 2
Output: 9
Explanation: The 9 possible arrangements are shown in the diagram above.
 

Constraints:

1 <= n <= 104
 */



// My Solution:
class Solution {
    long MOD = 1000000007L;
    
    public int countHousePlacements(int n) {
        // 数学+DP（街一边的可能数^2 即可）
        // dp0[i] 意味着 第 i 位有多少种为 valid 0（即不摆放）的可能性，因为不摆放总是安全的，因此 dp0[i] = dp0[i-1] + dp1[i-1]
        // dp1[i] 意味着 第 i 位有多少种为 valid 1（即摆放）的可能性，因为摆放总是需要之前为不摆放，因此 dp1[i] = dp0[i-1]
        // 最后，一边的总数为 dp0[n-1] + dp1[n-1]
        long[] dp0 = new long[n];
        long[] dp1 = new long[n];
        dp0[0] = 1L;
        dp1[0] = 1L;
        for (int i=1; i<n; i++) {
            dp0[i] = (dp0[i-1] + dp1[i-1]) % MOD;
            dp1[i] = dp0[i-1];
        }
        
        long perSide = (dp0[n-1] + dp1[n-1]) % MOD;
        return (int) ((perSide * perSide) % MOD);
    }
}
