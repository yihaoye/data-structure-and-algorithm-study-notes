/**
Your car starts at position 0 and speed +1 on an infinite number line. Your car can go into negative positions. Your car drives automatically according to a sequence of instructions 'A' (accelerate) and 'R' (reverse):

When you get an instruction 'A', your car does the following:
position += speed
speed *= 2
When you get an instruction 'R', your car does the following:
If your speed is positive then speed = -1
otherwise speed = 1
Your position stays the same.
For example, after commands "AAR", your car goes to positions 0 --> 1 --> 3 --> 3, and your speed goes to 1 --> 2 --> 4 --> -1.

Given a target position target, return the length of the shortest sequence of instructions to get there.

 

Example 1:

Input: target = 3
Output: 2
Explanation: 
The shortest instruction sequence is "AA".
Your position goes from 0 --> 1 --> 3.
Example 2:

Input: target = 6
Output: 5
Explanation: 
The shortest instruction sequence is "AAARA".
Your position goes from 0 --> 1 --> 3 --> 7 --> 7 --> 6.
 

Constraints:

1 <= target <= 10^4
 */



// Other's Solution:
class Solution {
    int[] dp = new int[10001];

    public int racecar(int t) {
        // DP + Bitwise + Math + Greedy - https://leetcode.com/problems/race-car/solutions/123834/java-c-python-dp-solution/?orderBy=most_votes
        // https://leetcode.com/problems/race-car/solutions/123834/java-c-python-dp-solution/comments/122557
        if (dp[t] > 0) return dp[t]; // 记忆化搜索
        int n = (int) (Math.log(t) / Math.log(2)) + 1; // log2(t) + 1 此处故意 +1
        if ((1 << n) - 1 == t) { // 等比数列求和 Sn = (1-r^n)/(1-r) 此处 r 为 2 因为只有 AR 两种情况
            dp[t] = n;
        } else {
            dp[t] = racecar((1 << n) - 1 - t) + n + 1; // 因为前面 n 是故意 +1 了，所以 (1 << n) - 1 - t 必大于等于 0
            for (int m = 0; m < n - 1; ++m) { // 这里循环并不多，因为 n 是 log2(t)
                dp[t] = Math.min(dp[t], racecar(t - ((1 << (n - 1)) - (1 << m))) + n + m + 1); // 这里 t 必大于等于 2^(n-1)，m 为 0 时就是 t - (2^(n-1) - 1)，m 为 n-2 时是 t - (2^(n-1) - 1 - (2^(n-2) - 1))
            }
        }
        return dp[t];
    }
}
