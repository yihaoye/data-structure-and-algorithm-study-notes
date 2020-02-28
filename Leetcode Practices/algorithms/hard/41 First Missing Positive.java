/*
Given an unsorted integer array, find the smallest missing positive integer.

Example 1:

Input: [1,2,0]
Output: 3
Example 2:

Input: [3,4,-1,1]
Output: 2
Example 3:

Input: [7,8,9,11,12]
Output: 1
Note:

Your algorithm should run in O(n) time and uses constant extra space.
*/



// My Solution (use nums.length space):
class Solution {
    public int firstMissingPositive(int[] nums) {
        // the smallest missing positive integer must within range [1, nums.length+1]
        int len = nums.length;
        int[] bucket = new int[len];
        for (int i=0; i<len; i++) {
            if (nums[i] <= len && nums[i] >= 1) bucket[nums[i]-1] = 1;
        }
        for (int i=0; i<len; i++) {
            if (bucket[i] == 0) return i+1;
        }
        return len+1;
    }
}



