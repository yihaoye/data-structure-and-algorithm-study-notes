/*
You are climbing a stair case. It takes n steps to reach to the top.

Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

Note: Given n will be a positive integer.

Example 1:

Input: 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps
Example 2:

Input: 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step
*/



// My Solution (DP):
class Solution {
    public int climbStairs(int n) {
        /*
            if (n == 0) return 0;
            else if (n == 1) return 1;
            else if (n == 2) return 2;
            else return climbStairs(n-1) + climbStairs(n-2); // left possibility after do step 1 + left possibility after do step 2
        */
        // Above recursive dfs will exceed time limit, so dp (like Fibonacci) is better option/replacement
        if (n <= 2) return n;
        int[] dp = new int[n];
        dp[0] = 1; // 1 stair
        dp[1] = 2; // 2 stair
        for (int i=2; i<n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }

        return dp[n-1];
    }
}



// My Solution (Improved):
class Solution {
    List<Integer> dp = new ArrayList<>(); // 使用 List 使得同个对象多次调用但输入不同 n 时可复用之前的 dp 数据。风险：数据量较大时，可能堆上超空间，integer 占多少比特？10^6 会比较大，通常内存不能超过 1 个 GB
    
    public int climbStairs(int n) {
        if (n == 0) return 1;
        // dp.get(i) -> how many step from 0 to i
        if (dp.size() > n) return dp.get(n);
        if (dp.size() <= 0) dp.add(1);
        if (dp.size() <= 1) dp.add(1);
        for (int i=2; i<=n; i++) {
            dp.add(dp.get(i-1) + dp.get(i-2));
        }
        
        return dp.get(n);
    }
}
