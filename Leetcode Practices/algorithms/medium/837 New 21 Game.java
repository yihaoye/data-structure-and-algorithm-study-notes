/**
Alice plays the following game, loosely based on the card game "21".

Alice starts with 0 points and draws numbers while she has less than k points. During each draw, she gains an integer number of points randomly from the range [1, maxPts], where maxPts is an integer. Each draw is independent and the outcomes have equal probabilities.

Alice stops drawing numbers when she gets k or more points.

Return the probability that Alice has n or fewer points.

Answers within 10-5 of the actual answer are considered accepted.

 

Example 1:

Input: n = 10, k = 1, maxPts = 10
Output: 1.00000
Explanation: Alice gets a single card, then stops.
Example 2:

Input: n = 6, k = 1, maxPts = 10
Output: 0.60000
Explanation: Alice gets a single card, then stops.
In 6 out of 10 possibilities, she is at or below 6 points.
Example 3:

Input: n = 21, k = 17, maxPts = 10
Output: 0.73278
 

Constraints:

0 <= k <= n <= 10^4
1 <= maxPts <= 10^4
 */



// Other's Solution:
class Solution {
    public double new21Game(int n, int k, int maxPts) {
        /* 
            sliding window + dp - https://leetcode.com/problems/new-21-game/discuss/132334/One-Pass-DP-O(N)
            https://leetcode.com/problems/new-21-game/discuss/132334/One-Pass-DP-O(N)/230189
            
            dp[i] is the probability that we get points i at some moment.
            i=1(抽中1分的概率)就是 1/10.
            i=2: 0.1(抽中2分的概率)+0.1x0.1(两次抽中1)的概率
            i=3: 0.1 (抽中3分的概率) + 0.1x0.1(第一次抽中1，第二次抽中2) + 0.1x0.1(第一次抽中2，第二次抽中1)+0.1x0.1x0.1（三次抽中0.1）
            可以从公式里面归纳出
            i=1: 0.1x1
            i=2: 0.1x(1+0.1)
            i=3: 0.1(1+0.1+0.1+0.01) = 0.1(1+0.1+0.11) 括号里是之前的p的sum，以此来推出Wsum的公式
            那为什么>k之后，几不变了呢，例如k=3
            i=4: 0.1(抽中4)+0.1x0.1(抽中1 and 3) +0.1x0.1(抽中2 and 2)+0.1x0.1x0.1(抽中2个1，一个2)
            i=5: 0.1(抽中4)+0.1x0.1(抽中1 and 4) +0.1x0.1(抽中2 and3)+0.1x0.1x0.1(抽中2个1，一个3)
            …
            可以看到，最后大于k的公式后面都是一样的。因为3（k）是一道坎，只有抽中小于3的数，后面才可能继续抽。

            最后。。如果i-w >0, 比如say W = 10, when we reach i = 11, dp[i] = Wsum / W = (dp[1] + .. + dp[10]) /10
            i = 11是不可能一次抽中的（大于w），所以要把一次抽中的概率减去，就是第一次。
            i = 12不可能跟2一样（抽中1一次11，再抽中1，也不可能一下抽中12）要把这次概率减去。
        */
        if (k == 0 || maxPts <= n - k) return 1;
        double dp[] = new double[n + 1], sum = 1, res = 0;
        dp[0] = 1; // beginning point is 0
        for (int i = 1; i <= n; ++i) {
            dp[i] = sum / maxPts;
            if (i < k) sum += dp[i]; else res += dp[i];
            if (i - maxPts >= 0) sum -= dp[i - maxPts];
        }
        return res;
    }
}
