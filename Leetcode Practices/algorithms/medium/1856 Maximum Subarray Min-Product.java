/*
The min-product of an array is equal to the minimum value in the array multiplied by the array's sum.

For example, the array [3,2,5] (minimum value is 2) has a min-product of 2 * (3+2+5) = 2 * 10 = 20.
Given an array of integers nums, return the maximum min-product of any non-empty subarray of nums. Since the answer may be large, return it modulo 109 + 7.

Note that the min-product should be maximized before performing the modulo operation. Testcases are generated such that the maximum min-product without modulo will fit in a 64-bit signed integer.

A subarray is a contiguous part of an array.

 

Example 1:

Input: nums = [1,2,3,2]
Output: 14
Explanation: The maximum min-product is achieved with the subarray [2,3,2] (minimum value is 2).
2 * (2+3+2) = 2 * 7 = 14.
Example 2:

Input: nums = [2,3,3,1,2]
Output: 18
Explanation: The maximum min-product is achieved with the subarray [3,3] (minimum value is 3).
3 * (3+3) = 3 * 6 = 18.
Example 3:

Input: nums = [3,1,5,6,4,2]
Output: 60
Explanation: The maximum min-product is achieved with the subarray [5,6,4] (minimum value is 4).
4 * (5+6+4) = 4 * 15 = 60.
 

Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 107

*/



// Other's Solution:
class Solution {
    public int maxSumMinProduct(int[] nums) {
        /*
            单调栈+前缀和 - https://leetcode-cn.com/problems/maximum-subarray-min-product/solution/zi-shu-zu-zui-xiao-cheng-ji-de-zui-da-zh-rq8r/
            时间复杂度 O(N)，空间复杂度 O(N)
        */
        int len = nums.length;
        int[] left = new int[len];
        int[] right = new int[len];
        Deque<Integer> stack = new LinkedList<>();
        // 对于每一个 i 找到右侧连续的不小于它的元素，记录最后一个连续的，不小于它的元素对应下标
        for (int i = 0; i < len; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) right[stack.pop()] = i-1;
            stack.push(i); // 存放的是下标
        }
        while (!stack.isEmpty()) right[stack.pop()] = len-1;

        // 对于每一个 i 找到左侧连续的不小于它的元素，记录最后一个连续的，不小于它的元素对应下标
        for (int i = len-1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) left[stack.pop()] = i+1;
            stack.push(i); // 存放的是下标
        }
        while (!stack.isEmpty()) left[stack.pop()] = 0;

        // 前缀和，用 long 来存放，防止相加时溢出
        long[] prefixSum = new long[len];
        prefixSum[0] = nums[0];
        for (int i = 1; i < len; i++) prefixSum[i] = prefixSum[i-1] + nums[i];

        long result = 0;
        for (int i = 0; i < len; i++) {
            // right[i] 为右坐标 left[i] 为左坐标，从 left[i] 到 right[i] 所有元素的和应该是 prefixSum[right[i]] - prefixSum[left[i]] + nums[left[i]]
            result = Math.max(result, nums[i]*(prefixSum[right[i]] - prefixSum[left[i]] + nums[left[i]]));
        }
        return (int)(result % 1000000007);
    }
}
