/**
Given an array of n integers nums and an integer target, find the number of index triplets i, j, k with 0 <= i < j < k < n that satisfy the condition nums[i] + nums[j] + nums[k] < target.

 

Example 1:

Input: nums = [-2,0,1,3], target = 2
Output: 2
Explanation: Because there are two triplets which sums are less than 2:
[-2,0,1]
[-2,0,3]
Example 2:

Input: nums = [], target = 0
Output: 0
Example 3:

Input: nums = [0], target = 0
Output: 0
 

Constraints:

n == nums.length
0 <= n <= 3500
-100 <= nums[i] <= 100
-100 <= target <= 100
 */



// Other's Solution:
class Solution {
    public int threeSumSmaller(int[] nums, int target) {
        // 双指针
        // Time: O(N^2), Space: O(1)
        Arrays.sort(nums);
        int res = 0;
        for (int i=0; i<nums.length-2; i++) {
            res += twoSumSmaller(nums, i+1, target-nums[i]);
        }
        return res;
    }

    private int twoSumSmaller(int[] nums, int start, int target) {
        int sum = 0, l = start, r = nums.length-1;
        while (l < r) {
            if (nums[l] + nums[r] < target) {
                sum += r - l;
                l++;
            } else {
                r--;
            }
        }
        return sum;
    }
}
