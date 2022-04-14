/**
Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.

Note that you must do this in-place without making a copy of the array.

 

Example 1:

Input: nums = [0,1,0,3,12]
Output: [1,3,12,0,0]
Example 2:

Input: nums = [0]
Output: [0]
 

Constraints:

1 <= nums.length <= 104
-231 <= nums[i] <= 231 - 1
 

Follow up: Could you minimize the total number of operations done?
 */



// Other's Solution:
class Solution {
    public void moveZeroes(int[] nums) {
        /*
            双指针
            Time: O(N), Space: (1)
        */
        int pos = 0; // last non zero element in nums after one pass

        for (int i=0; i<nums.length; i++) {
            if (nums[i] != 0) swap(pos++, i, nums);
        }
    }

    private void swap(int indexA, int indexB, int[] nums) {
        int numA = nums[indexA], numB = nums[indexB];
        nums[indexA] = numB;
        nums[indexB] = numA;
    }
}
