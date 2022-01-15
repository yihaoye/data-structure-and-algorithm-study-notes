/*
Given an integer array nums, find a contiguous non-empty subarray within the array that has the largest product, and return the product.

The test cases are generated so that the answer will fit in a 32-bit integer.

A subarray is a contiguous subsequence of the array.

 

Example 1:

Input: nums = [2,3,-2,4]
Output: 6
Explanation: [2,3] has the largest product 6.
Example 2:

Input: nums = [-2,0,-1]
Output: 0
Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
 

Constraints:

1 <= nums.length <= 2 * 104
-10 <= nums[i] <= 10
The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
*/



// My Solution:
class Solution {
    public int maxProduct(int[] nums) {
        /*
            数学，遍历一遍，遇到 0 就重算（跨 0 皆为 0），一段非 0 数组 [i, j] 其乘积绝对值必然单调递增若 multi[i, j] 大于 0 则必为该段数组的最大值乘积否则则从最大正乘积 multi[i, k] 或 multi[i, j]/multi[i, l] （l 为本段数组的第一个负数的索引，multi[i, l] 即为本段第一个负乘积）
            时间复杂度 O(N)，空间复杂度 O(1)
        */
        int res = nums[0], firstNegativeMulti = 0, multi = 0;
        for (int i=0; i<nums.length; i++) {
            multi = multi == 0 ? nums[i] : multi * nums[i];
            if (firstNegativeMulti == 0 && nums[i] < 0) firstNegativeMulti = multi;
            else if (multi < 0 && multi/firstNegativeMulti > res) res = multi/firstNegativeMulti;
            if (multi > res) res = multi;
            if (nums[i] == 0) firstNegativeMulti = 0;
        }
        
        return res;
    }
}
