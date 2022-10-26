// https://www.youtube.com/watch?v=Yeh1_2GyS5s
// https://zh.wikipedia.org/wiki/%E7%BA%A6%E7%91%9F%E5%A4%AB%E6%96%AF%E9%97%AE%E9%A2%98

class Solution {
    public int removeKth(int N, int K) {
        // 数学归纳法
        // f(1, K) = 1
        // f(N, K) = (f(N-1, K) + K) % N
        // 迭代、DP 而不是递归，可使 Time: O(N), Space: O(1)
        int dp = 1; // f(1, K) = 1（编号从 1 开始所以 f(1, K) = 1，若从 0 开始则为 f(1, K) = 0）
        for (int i=1; i<N; i++) {
            dp = (dp + K) % i;
        }

        return dp;
    }
}
