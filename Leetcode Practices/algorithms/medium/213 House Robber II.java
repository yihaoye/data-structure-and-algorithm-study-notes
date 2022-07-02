/**
You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed. All houses at this place are arranged in a circle. That means the first house is the neighbor of the last one. Meanwhile, adjacent houses have a security system connected, and it will automatically contact the police if two adjacent houses were broken into on the same night.

Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.

 

Example 1:

Input: nums = [2,3,2]
Output: 3
Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2), because they are adjacent houses.
Example 2:

Input: nums = [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
Total amount you can rob = 1 + 3 = 4.
Example 3:

Input: nums = [1,2,3]
Output: 3
 

Constraints:

1 <= nums.length <= 100
0 <= nums[i] <= 1000
 */



// My Solution:
class Solution {
    public int rob(int[] nums) {
        // DP
        // Time: O(N), Space: O(1)

        // include 0 exclude nums.length-1
        int res = robUnlimit(nums, 2, nums.length-2) + nums[0];

        // include nums.length-1 exclude 0
        res = Math.max(res, robUnlimit(nums, 1, nums.length-3) + nums[nums.length-1]);
        
        // exclude 0 exclude nums.length-1
        res = Math.max(res, robUnlimit(nums, 1, nums.length-2));

        return res;
    }
    
    private int robUnlimit(int[] nums, int lIndex, int rIndex) {
        int thisMax = 0, lastMax = 0, lastLastMax = 0;
        for (int i=lIndex; i<=rIndex; i++) {
            lastLastMax = Math.max(lastLastMax, lastMax);
            lastMax = Math.max(lastMax, thisMax);
            thisMax = Math.max(lastMax, lastLastMax + nums[i]); 
        }
        return thisMax;
    }
}
