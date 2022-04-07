/**
Given an integer array nums, return the number of AND triples.

An AND triple is a triple of indices (i, j, k) such that:

0 <= i < nums.length
0 <= j < nums.length
0 <= k < nums.length
nums[i] & nums[j] & nums[k] == 0, where & represents the bitwise-AND operator.
 

Example 1:

Input: nums = [2,1,3]
Output: 12
Explanation: We could choose the following i, j, k triples:
(i=0, j=0, k=1) : 2 & 2 & 1
(i=0, j=1, k=0) : 2 & 1 & 2
(i=0, j=1, k=1) : 2 & 1 & 1
(i=0, j=1, k=2) : 2 & 1 & 3
(i=0, j=2, k=1) : 2 & 3 & 1
(i=1, j=0, k=0) : 1 & 2 & 2
(i=1, j=0, k=1) : 1 & 2 & 1
(i=1, j=0, k=2) : 1 & 2 & 3
(i=1, j=1, k=0) : 1 & 1 & 2
(i=1, j=2, k=0) : 1 & 3 & 2
(i=2, j=0, k=1) : 3 & 2 & 1
(i=2, j=1, k=0) : 3 & 1 & 2
Example 2:

Input: nums = [0,0,0]
Output: 27
 

Constraints:

1 <= nums.length <= 1000
0 <= nums[i] < 216

 */



// Other's Solution:
class Solution {
    public int countTriplets(int[] nums) {
        /*
            DP - https://leetcode.com/problems/triples-with-bitwise-and-equal-to-zero/discuss/226721/Java-DP-O(3-*-216-*-n)-time-O(216)-space
            i=2 means pick 2 numbers. dp[10] = 5 means when pick 2 numbers, the count of combinations is 5, where the AND result of such numbers is 10. Trick: initialize the dp[(1<<16) - 1] to 1 because the AND result of every number with (1<<16) - 1 is the number itself.
            时间复杂度 O(N)，空间复杂度 O(1)
        */
        int N = 1 << 16;
        int[] dp = new int[N];
        dp[N - 1] = 1;
        for (int i = 0; i < 3; i++) {
            int[] temp = new int[N];
            for (int k = 0; k < N; k++) {
                for (int num : nums) {
                    temp[k & num] += dp[k];
                }
            }
            dp = temp;
        }
        return dp[0];
    }
}
