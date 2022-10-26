// https://www.youtube.com/watch?v=Yeh1_2GyS5s
// https://zh.wikipedia.org/wiki/%E7%BA%A6%E7%91%9F%E5%A4%AB%E6%96%AF%E9%97%AE%E9%A2%98

class Solution {
    public int removeKth(int N, int K) {
        // 数学归纳法
        // f(1, K) = 1
        // f(N, K) = (f(N-1, K) + K) % N
        // 迭代、DP 而不是递归，可使 Time: O(N), Space: O(1)
        int[] dp = new int[N];
        dp[0] = 1; // f(1, K)
        for (int i=1; i<N; i++) {
            dp[i] = (dp[i-1] + K) % i;
        }

        return dp[N-1];
    }
}
