/*
Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.

Example:

Input: [-2,1,-3,4,-1,2,1,-5,4],
Output: 6
Explanation: [4,-1,2,1] has the largest sum = 6.
Follow up:

If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
*/



// Other's Solution:
class Solution {
    public int maxSubArray(int[] nums) {
        // Kadane 算法
        // Time: O(N), Space: O(1)
        int n = nums.length, maxSum = Integer.MIN_VALUE, maxEnd = 0;
        for (int i=0; i<n; i++) {
            maxEnd = maxEnd + nums[i];
            maxSum = Math.max(maxSum, maxEnd);
            if (maxEnd < 0) maxEnd = 0;
        }
        
        return maxSum;
    }
}



// Solution under help:
class Solution {
    public int maxSubArray(int[] nums) {
        int maxTmp = 0;
        int max = nums[0];
        
        for (int num : nums) {
            maxTmp = Math.max(maxTmp + num, num);
            max = Math.max(maxTmp, max);
        }
        
        return max;
    }
}



// Other's Solution:
class Solution {
    public int maxSubArray(int[] nums) {
        /*
            a: 思路 - DP，dp[i] 表示以 nums[i] 结尾的连续子数组的最大和
            b: 时间复杂度 O(N)，空间复杂度 O(N)
            c: 看答案（https://leetcode-cn.com/problems/maximum-subarray/solution/dong-tai-gui-hua-fen-zhi-fa-python-dai-ma-java-dai/）
        */
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int res = dp[0];
        for (int i=1; i<nums.length; i++) {
            dp[i] = Math.max(dp[i-1] + nums[i], nums[i]);
            res = Math.max(res, dp[i]);
        }
        
        return res;
    }
}
