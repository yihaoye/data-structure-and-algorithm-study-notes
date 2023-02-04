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
// Time: O(T*logT), Space: O(T)
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
            dp[t] = racecar((1 << n) - 1 - t) + n + 1; // 因为前面 n 是故意 +1 了，所以 (1 << n) - 1 - t 必大于等于 0，这里就是先计算刚刚超过 t 然后再返回的方法的所需步数，然后下面会再跟未超过 t 然后 R 两次重新起步的组合比较哪个更小
            for (int m = 0; m < n - 1; ++m) { // 这里循环并不多，因为 n 是 log2(t)
                dp[t] = Math.min(dp[t], racecar(t - ((1 << (n - 1)) - (1 << m))) + (n - 1) + m + 2); // 这里 t 必大于等于 2^(n-1)，m 为 0 时就是 t - (2^(n-1) - 1 - (2^0 - 1))，m 为 n-2 时是 t - (2^(n-1) - 1 - (2^(n-2) - 1))，这里的意思就是跟未超过 t 然后 R 两次重新起步的组合比较哪个更小，这类组合的可能性有 n-2 种可能性即 m: [0..n-2]，即总是先往 t 处走 n-1 步（肯定刚刚好未到达）然后往回走 m 步，然后再转头往 t 处走剩下的（准确来说是递归剩下的），此时剩下的也可能是某种复杂的 AR 组合即并非刚好是 Sn，但是因为这里采用了递归与记忆化搜索优化，所以很好地处理了这种穷举法场景（并且因为前面的 n 计算保证了下次递归的 t' 肯定不为负数所以能保证完美计算）
            }
        }
        return dp[t];
    }
}
