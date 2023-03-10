/**
There is a test that has n types of questions. You are given an integer target and a 0-indexed 2D integer array types where types[i] = [counti, marksi] indicates that there are counti questions of the ith type, and each one of them is worth marksi points.

Return the number of ways you can earn exactly target points in the exam. Since the answer may be too large, return it modulo 109 + 7.

Note that questions of the same type are indistinguishable.

For example, if there are 3 questions of the same type, then solving the 1st and 2nd questions is the same as solving the 1st and 3rd questions, or the 2nd and 3rd questions.
 

Example 1:

Input: target = 6, types = [[6,1],[3,2],[2,3]]
Output: 7
Explanation: You can earn 6 points in one of the seven ways:
- Solve 6 questions of the 0th type: 1 + 1 + 1 + 1 + 1 + 1 = 6
- Solve 4 questions of the 0th type and 1 question of the 1st type: 1 + 1 + 1 + 1 + 2 = 6
- Solve 2 questions of the 0th type and 2 questions of the 1st type: 1 + 1 + 2 + 2 = 6
- Solve 3 questions of the 0th type and 1 question of the 2nd type: 1 + 1 + 1 + 3 = 6
- Solve 1 question of the 0th type, 1 question of the 1st type and 1 question of the 2nd type: 1 + 2 + 3 = 6
- Solve 3 questions of the 1st type: 2 + 2 + 2 = 6
- Solve 2 questions of the 2nd type: 3 + 3 = 6
Example 2:

Input: target = 5, types = [[50,1],[50,2],[50,5]]
Output: 4
Explanation: You can earn 5 points in one of the four ways:
- Solve 5 questions of the 0th type: 1 + 1 + 1 + 1 + 1 = 5
- Solve 3 questions of the 0th type and 1 question of the 1st type: 1 + 1 + 1 + 2 = 5
- Solve 1 questions of the 0th type and 2 questions of the 1st type: 1 + 2 + 2 = 5
- Solve 1 question of the 2nd type: 5
Example 3:

Input: target = 18, types = [[6,1],[3,2],[2,3]]
Output: 1
Explanation: You can only earn 18 points by answering all questions.
 

Constraints:

1 <= target <= 1000
n == types.length
1 <= n <= 50
types[i].length == 2
1 <= counti, marksi <= 50
 */



// Other's Solution:
class Solution {
    public int waysToReachTarget(int target, int[][] types) {
        // https://leetcode.com/problems/number-of-ways-to-earn-points/solutions/3258120/java-c-python-knapsack-dp/?orderBy=most_votes
        /**
            dp[i] means the ways to reach i points.

            Iterative each [count, mark] in question of all types.
            we can do 1,2...,count question right,
            dp[i] can be reached from dp[i - mark], dp[i - mark * 2] ... dp[i - mark * count].

            Note that dp[big] depends on dp[small],
            if you change dp[small] first, we need to update dp[big] from old dp[small].
            So here I iterative from dp[big] to dp[small].

            We continuely do this and finally return dp[target].
         */
        // Time: O(target * n * count)
        int mod = (int) 1e9 + 7, dp[] = new int[target + 1];
        dp[0] = 1;
        for (int[] t : types)
            for (int i = target; i > 0; --i)
                for (int k = 1; k <= t[0] && i - t[1] * k >= 0; ++k)
                    dp[i] = (dp[i] + dp[i - t[1] * k]) % mod;
        
        return dp[target];
    }
}
