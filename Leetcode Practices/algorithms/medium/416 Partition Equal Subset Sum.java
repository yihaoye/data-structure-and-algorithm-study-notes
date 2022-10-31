/*
Given a non-empty array nums containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.

 

Example 1:

Input: nums = [1,5,11,5]
Output: true
Explanation: The array can be partitioned as [1, 5, 5] and [11].
Example 2:

Input: nums = [1,2,3,5]
Output: false
Explanation: The array cannot be partitioned into equal sum subsets.
 

Constraints:

1 <= nums.length <= 200
1 <= nums[i] <= 100
*/



// Other's Solution:
public class Solution {
    public boolean canPartition(int[] nums) {
        /*
            DP（背包问题） - https://leetcode-cn.com/problems/partition-equal-subset-sum/solution/0-1-bei-bao-wen-ti-xiang-jie-zhen-dui-ben-ti-de-yo/
            时间复杂度：O(N*C)，空间复杂度：O(C)，C 是数组元素的和的一半
        */
        int len = nums.length;
        int sum = 0;
        for (int num : nums) sum += num;
        if ((sum & 1) == 1) return false;

        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        if (nums[0] <= target) dp[nums[0]] = true;
        for (int i = 1; i < len; i++) {
            for (int j = target; nums[i] <= j; j--) { // 为什么是从 target-- 而不是 nums[i]++？因为后者会对后面 nums[i]++ 有影响
                if (dp[target]) return true; // 提前返回答案，提升性能
                dp[j] = dp[j] || dp[j - nums[i]];
            }
        }
        return dp[target];
    }
}
