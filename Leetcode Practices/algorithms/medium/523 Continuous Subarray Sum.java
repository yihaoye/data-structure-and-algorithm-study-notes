/**
Given an integer array nums and an integer k, return true if nums has a continuous subarray of size at least two whose elements sum up to a multiple of k, or false otherwise.

An integer x is a multiple of k if there exists an integer n such that x = n * k. 0 is always a multiple of k.

 

Example 1:

Input: nums = [23,2,4,6,7], k = 6
Output: true
Explanation: [2, 4] is a continuous subarray of size 2 whose elements sum up to 6.
Example 2:

Input: nums = [23,2,6,4,7], k = 6
Output: true
Explanation: [23, 2, 6, 4, 7] is an continuous subarray of size 5 whose elements sum up to 42.
42 is a multiple of 6 because 42 = 7 * 6 and 7 is an integer.
Example 3:

Input: nums = [23,2,6,4,7], k = 13
Output: false
 

Constraints:

1 <= nums.length <= 10^5
0 <= nums[i] <= 10^9
0 <= sum(nums[i]) <= 2^31 - 1
1 <= k <= 2^31 - 1
 */



// My Solution:
class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        // 前缀和 + HashMap
        Map<Integer, Integer> prefixSum = new HashMap<>(); // <prefixSumI, indexI>
        prefixSum.put(0, -1);
        prefixSum.putIfAbsent(nums[0], 0);
        int lastPrefixSum = nums[0];
        for (int i=1; i<nums.length; i++) {
            lastPrefixSum = lastPrefixSum + nums[i];
            if (lastPrefixSum % k == 0) return true;
            for (int j=0; j*k<lastPrefixSum; j++) {
                if (prefixSum.containsKey(lastPrefixSum-j*k) && i - prefixSum.get(lastPrefixSum-j*k) >= 2) return true;
            }
            prefixSum.putIfAbsent(lastPrefixSum, i);
        }
        
        return false;
    }
}
