/**
Given an integer array nums and an integer k, return the number of non-empty subarrays that have a sum divisible by k.

A subarray is a contiguous part of an array.

 

Example 1:

Input: nums = [4,5,0,-2,-3,1], k = 5
Output: 7
Explanation: There are 7 subarrays with a sum divisible by k = 5:
[4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
Example 2:

Input: nums = [5], k = 9
Output: 0
 

Constraints:

1 <= nums.length <= 3 * 10^4
-10^4 <= nums[i] <= 10^4
2 <= k <= 10^4
 */



// My Solution:
class Solution {
    public int subarraysDivByK(int[] nums, int k) {
        // hashmap + presum + Math(mod) - map<integer, integer> : <sum[0..i]%k, cnt>
        Map<Integer, Integer> preSumMod = new HashMap<>();
        int res = 0, curSum = 0;
        for (int num : nums) {
            curSum += num;
            int curSumMod = curSum % k;
            if (curSumMod == 0) {
                res += preSumMod.getOrDefault(0, 0) + 1;
            } else {
                res += preSumMod.getOrDefault(curSumMod, 0); // curSumMod - preSumMod == 0
                res += preSumMod.getOrDefault(curSumMod - k, 0); // curSumMod - preSumMod == k
                res += preSumMod.getOrDefault(curSumMod + k, 0); // curSumMod - preSumMod == -k
            }
            preSumMod.put(curSumMod, preSumMod.getOrDefault(curSumMod, 0) + 1);
        }
        return res;
    }
}
