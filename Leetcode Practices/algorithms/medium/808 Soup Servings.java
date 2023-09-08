/*
There are two types of soup: type A and type B. Initially, we have n ml of each type of soup. There are four kinds of operations:

Serve 100 ml of soup A and 0 ml of soup B,
Serve 75 ml of soup A and 25 ml of soup B,
Serve 50 ml of soup A and 50 ml of soup B, and
Serve 25 ml of soup A and 75 ml of soup B.
When we serve some soup, we give it to someone, and we no longer have it. Each turn, we will choose from the four operations with an equal probability 0.25. If the remaining volume of soup is not enough to complete the operation, we will serve as much as possible. We stop once we no longer have some quantity of both types of soup.

Note that we do not have an operation where all 100 ml's of soup B are used first.

Return the probability that soup A will be empty first, plus half the probability that A and B become empty at the same time. Answers within 10-5 of the actual answer will be accepted.

 

Example 1:

Input: n = 50
Output: 0.62500
Explanation: If we choose the first two operations, A will become empty first.
For the third operation, A and B will become empty at the same time.
For the fourth operation, B will become empty first.
So the total probability of A becoming empty first plus half the probability that A and B become empty at the same time, is 0.25 * (1 + 1 + 0.5 + 0) = 0.625.
Example 2:

Input: n = 100
Output: 0.71875
 

Constraints:

0 <= n <= 10^9
*/



// My Solution: (n > 4451 is inspired by others)
class Solution {
    Map<Integer, Map<Integer, Double>> dp = new HashMap<>();

    public double soupServings(int n) {
        // 数学（概率）+ 递归 + 记忆化搜索
        if (n == 0) return 0.5;
        return n > 4451 ? 1.0 : soupServings(n, n); // n > 4451 时直接返回 1.0 -- Answers within 10^-5 of the actual answer will be accepted. 可以通过二分搜索得出当 n == 4451 时 res >= 0.99999 (https://leetcode.com/problems/soup-servings/solutions/3831172/video-100-soup-servings-a-dive-into-dynamic-programming-and-probability/) 如果不使用该优化，会在 n 较大时造成 stackoverflow
    }

    public double soupServings(int A, int B) {
        double res = 0.0;
        if (A <= 0 || B <= 0) return res;
        if (dp.containsKey(A) && dp.get(A).containsKey(B)) return dp.get(A).get(B);

        if (A <= 100) res += 0.25; // must be B > 0 since above
        else res += 0.25 * soupServings(A - 100, B);

        if (A <= 75 && B > 25) res += 0.25;
        else if (A <= 75 && B <= 25) res += 0.25 * 0.5;
        else res += 0.25 * soupServings(A - 75, B - 25);

        if (A <= 50 && B > 50) res += 0.25;
        else if (A <= 50 && B <= 50) res += 0.25 * 0.5;
        else res += 0.25 * soupServings(A - 50, B - 50);

        if (A <= 25 && B > 75) res += 0.25;
        else if (A <= 25 && B <= 75) res += 0.25 * 0.5;
        else res += 0.25 * soupServings(A - 25, B - 75);

        dp.computeIfAbsent(A, k -> new HashMap<>()).put(B, res);
        return res;
    }
}
