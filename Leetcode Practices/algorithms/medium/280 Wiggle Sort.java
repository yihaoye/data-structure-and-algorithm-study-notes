/*
Given an unsorted array nums, reorder it in-place such that nums[0] <= nums[1] >= nums[2] <= nums[3]....

Example:

Input: nums = [3,5,2,1,6,4]
Output: One possible answer is [3,5,1,6,2,4]
*/



// Other's Solution:
class Solution {
    public void wiggleSort(int[] nums) {
        for (int i = 1; i < nums.length; ++i) {
            if ((i % 2 == 1) != (nums[i] > nums[i - 1])) {
                int cache = nums[i];
                nums[i] = nums[i-1];
                nums[i-1] = cache;
            }
        }
    }
}



// My Solution:
class Solution {
    public void wiggleSort(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length;
        int[] res = new int[len];
        int i = 0;
        for (int j = 0; j < len; j+=2, i++) res[j] = nums[i];
        for (int j = 1; j < len; j+=2, i++) res[j] = nums[i];
        for (int j = 0; j < len; j++) nums[j] = res[j];
    }
}