/*
Given a binary array nums, you should delete one element from it.

Return the size of the longest non-empty subarray containing only 1's in the resulting array. Return 0 if there is no such subarray.

 

Example 1:

Input: nums = [1,1,0,1]
Output: 3
Explanation: After deleting the number in position 2, [1,1,1] contains 3 numbers with value of 1's.
Example 2:

Input: nums = [0,1,1,1,0,1,1,0,1]
Output: 5
Explanation: After deleting the number in position 4, [0,1,1,1,1,1,0,1] longest subarray with value of 1's is [1,1,1,1,1].
Example 3:

Input: nums = [1,1,1]
Output: 2
Explanation: You must delete one element.
 

Constraints:

1 <= nums.length <= 10^5
nums[i] is either 0 or 1.
*/



// Other's Solution:
class Solution {
    public int longestSubarray(int[] nums) {
        // sliding window - https://leetcode.com/problems/longest-subarray-of-1s-after-deleting-one-element/?envType=study-plan-v2&envId=leetcode-75
        // k is the allowed number of 0 in the sliding window
        int i = 0, j, k = 1, res = 0;
        for (j = 0; j < nums.length; ++j) {
            if (nums[j] == 0) k--;
            while (k < 0) {
                if (nums[i] == 0) k++; // nums[i] == 0 的话，此时 i++ 就会 k++，减少一个 0，所以 k 自加一，最后保证 k 至少 >= 0 即最多一个 0
                i++;
            }
            res = Math.max(res, j - i);
        }
        return res;
    }
}



// My Solution:
class Solution {
    public int longestSubarray(int[] nums) {
        // dp - both direction
        int n = nums.length;
        int[][] dp = new int[n][2];
        for (int i=0; i<n; i++) {
            if (nums[i] == 1) {
                dp[i][0] = 1 + (i == 0 ? 0 : dp[i-1][0]);
            }
            if (nums[n-1-i] == 1) {
                dp[n-1-i][1] = 1 + (i == 0 ? 0 : dp[n-i][1]);
            }
        }
        int res = 0;
        for (int i=0; i<n; i++) {
            int l = i == 0 ? 0 : dp[i-1][0];
            int r = i == n-1 ? 0 : dp[i+1][1];
            res = Math.max(res, l + r);
        }
        return res;
    }
}
