/*
Given an integer array nums, return the number of longest increasing subsequences.

Notice that the sequence has to be strictly increasing.

 

Example 1:

Input: nums = [1,3,5,4,7]
Output: 2
Explanation: The two longest increasing subsequences are [1, 3, 4, 7] and [1, 3, 5, 7].
Example 2:

Input: nums = [2,2,2,2,2]
Output: 5
Explanation: The length of longest continuous increasing subsequence is 1, and there are 5 subsequences' length is 1, so output 5.

 

Constraints:

1 <= nums.length <= 2000
-106 <= nums[i] <= 106

*/



// Other's Solution:
class Solution {
    public int findNumberOfLIS(int[] nums) {
        /*
            动态规划（基于 LIS - LC300） - https://leetcode-cn.com/problems/number-of-longest-increasing-subsequence/solution/zui-chang-di-zeng-zi-xu-lie-de-ge-shu-by-w12f/
            时间复杂度 O(N^2)，空间复杂度 O(N)
        */
        int n = nums.length, maxLen = 0, res = 0;
        int[] dp = new int[n], cnt = new int[n];
        for (int i=0; i<n; i++) {
            dp[i] = 1;
            cnt[i] = 1;
            for (int j=0; j<i; j++) {
                if (nums[i] > nums[j]) {
                    if (dp[j]+1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                        cnt[i] = cnt[j]; // 重置计数
                    } else if (dp[j]+1 == dp[i]) {
                        cnt[i] += cnt[j];
                    }
                }
            }
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                res = cnt[i]; // 重置计数
            } else if (dp[i] == maxLen) {
                res += cnt[i];
            }
        }
        return res;
    }
}
