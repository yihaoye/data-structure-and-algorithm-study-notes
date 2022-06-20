/**
Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.

A subarray is a contiguous non-empty sequence of elements within an array.

 

Example 1:

Input: nums = [1,1,1], k = 2
Output: 2
Example 2:

Input: nums = [1,2,3], k = 3
Output: 2
 

Constraints:

1 <= nums.length <= 2 * 104
-1000 <= nums[i] <= 1000
-107 <= k <= 107
 */



// My Solution:
class Solution {
    public int subarraySum(int[] nums, int k) {
        // 前缀和 + 哈希表
        // Time: O(N), Space: O(N)
        Map<Integer, Integer> map = new HashMap<>(); // <前缀和, 前缀和索引的统计>
        int[] preSum = new int[nums.length];
        int res = 0;
        for (int i=0; i<nums.length; i++) {
            preSum[i] = i == 0 ? nums[i] : preSum[i-1] + nums[i];
            if (preSum[i] == k) res++; // [0-i] 本身就是答案之一
            res += map.getOrDefault(preSum[i]-k, 0); // preSum[i]-k == 0 时也要再计一次，因为前面有可能前缀和为 0 的子数组
            map.put(preSum[i], map.getOrDefault(preSum[i], 0) + 1);
        }

        return res;
    }
}
