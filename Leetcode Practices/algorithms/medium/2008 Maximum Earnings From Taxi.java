/**
There are n points on a road you are driving your taxi on. The n points on the road are labeled from 1 to n in the direction you are going, and you want to drive from point 1 to point n to make money by picking up passengers. You cannot change the direction of the taxi.

The passengers are represented by a 0-indexed 2D integer array rides, where rides[i] = [starti, endi, tipi] denotes the ith passenger requesting a ride from point starti to point endi who is willing to give a tipi dollar tip.

For each passenger i you pick up, you earn endi - starti + tipi dollars. You may only drive at most one passenger at a time.

Given n and rides, return the maximum number of dollars you can earn by picking up the passengers optimally.

Note: You may drop off a passenger and pick up a different passenger at the same point.

 

Example 1:

Input: n = 5, rides = [[2,5,4],[1,5,1]]
Output: 7
Explanation: We can pick up passenger 0 to earn 5 - 2 + 4 = 7 dollars.
Example 2:

Input: n = 20, rides = [[1,6,1],[3,10,2],[10,12,3],[11,12,2],[12,15,2],[13,18,1]]
Output: 20
Explanation: We will pick up the following passengers:
- Drive passenger 1 from point 3 to point 10 for a profit of 10 - 3 + 2 = 9 dollars.
- Drive passenger 2 from point 10 to point 12 for a profit of 12 - 10 + 3 = 5 dollars.
- Drive passenger 5 from point 13 to point 18 for a profit of 18 - 13 + 1 = 6 dollars.
We earn 9 + 5 + 6 = 20 dollars in total.
 

Constraints:

1 <= n <= 10^5
1 <= rides.length <= 3 * 10^4
rides[i].length == 3
1 <= starti < endi <= n
1 <= tipi <= 10^5
 */



// My Solution:
class Solution {
    public long maxTaxiEarnings(int n, int[][] rides) {
        // dp -> dp[endi] 即以 endi 或之前为下车点的最大收益（从左到右遍历）
        // 当遍历到 [starti', endi', tipi'] 时: 先判断是否 endi' > lastEndI, 如果是则把 dp(lastEndI .. endi'] 全部填充为 dp[lastEndI] 然后更新 lastEndI = endi'
        // dp[endi'] = Math.max(dp[endi'], dp[starti'] + tipi' + endi' - starti') 
        // Time: O(Max(n, m*logm)), Space: O(n) - m = rides.length
        Arrays.sort(rides, (a, b) -> (a[1] == b[1]) ? a[0] - b[0] : a[1] - b[1]);
        int lastEndI = 0;
        long[] dp = new long[n+1];
        for (int i=0; i<rides.length; i++) {
            if (rides[i][1] > lastEndI) {
                for (int j=lastEndI+1; j<=rides[i][1]; j++) dp[j] = dp[lastEndI];
                lastEndI = rides[i][1];
            }
            dp[rides[i][1]] = Math.max(dp[rides[i][1]], dp[rides[i][0]] + rides[i][2] + rides[i][1] - rides[i][0]);
        }
        if (n > lastEndI) {
            for (int j=lastEndI+1; j<=n; j++) dp[j] = dp[lastEndI];
        }
        return dp[n];
    }
}

// 另外可以参考 https://leetcode.cn/problems/maximum-earnings-from-taxi/solution/5681-shuang-zhou-sai-t3-dong-tai-gui-hua-oikd/
