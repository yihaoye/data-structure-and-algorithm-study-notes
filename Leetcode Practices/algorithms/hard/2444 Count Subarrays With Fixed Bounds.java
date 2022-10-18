/**
You are given an integer array nums and two integers minK and maxK.

A fixed-bound subarray of nums is a subarray that satisfies the following conditions:

The minimum value in the subarray is equal to minK.
The maximum value in the subarray is equal to maxK.
Return the number of fixed-bound subarrays.

A subarray is a contiguous part of an array.

 

Example 1:

Input: nums = [1,3,5,2,7,5], minK = 1, maxK = 5
Output: 2
Explanation: The fixed-bound subarrays are [1,3,5] and [1,3,5,2].
Example 2:

Input: nums = [1,1,1,1], minK = 1, maxK = 1
Output: 10
Explanation: Every subarray of nums is a fixed-bound subarray. There are 10 possible subarrays.
 

Constraints:

2 <= nums.length <= 10^5
1 <= nums[i], minK, maxK <= 10^6
*/



// Other's Solution:
class Solution {
    public long countSubarrays(int[] nums, int minK, int maxK) {
        // 双指针 - https://leetcode.com/problems/count-subarrays-with-fixed-bounds/discuss/2708099/JavaC%2B%2BPython-Sliding-Window-with-Explanation
        // Time: O(N), Space: O(1)
        long result = 0;
        int subArrayStartIndex = 0, latestMinIndex = -1, latestMaxIndex = -1;

        for (int i=0; i<nums.length; i++) {
            if (nums[i] < minK || nums[i] > maxK) { // invalid sub array, start over (reset)
                latestMinIndex = -1;
                latestMaxIndex = -1;
                subArrayStartIndex = i + 1;
            }
            if (nums[i] == minK) latestMinIndex = i;
            if (nums[i] == maxK) latestMaxIndex = i;
            result += Math.max(0L, Math.min(latestMinIndex, latestMaxIndex) - subArrayStartIndex + 1);
        }
        return result;
    }
}
