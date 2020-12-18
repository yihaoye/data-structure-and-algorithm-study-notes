/*
Given an integer array nums, find three numbers whose product is maximum and return the maximum product.

 

Example 1:

Input: nums = [1,2,3]
Output: 6
Example 2:

Input: nums = [1,2,3,4]
Output: 24
Example 3:

Input: nums = [-1,-2,-3]
Output: -6
 

Constraints:

3 <= nums.length <= 104
-1000 <= nums[i] <= 1000
*/



// My Solution (O(N*logN)):
class Solution {
    public int maximumProduct(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length;
        if (nums[len-1] <= 0 || nums[len-2]*nums[len-3] >= nums[0]*nums[1]) return nums[len-1]*nums[len-2]*nums[len-3];
        return nums[len-1]*nums[0]*nums[1];
    }
}
