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



// Other's Solution:
class Solution {
    public int maxProduct(int[] nums) {
        /*
            Kadane 算法 - https://leetcode.com/problems/maximum-product-subarray/discuss/183483/JavaC%2B%2BPython-it-can-be-more-simple
            Calculate prefix product in A. Calculate suffix product in A. Return the max. 
            First, if there's no zero in the array, then the subarray with maximum product must start with the first element or end with the last element. And therefore, the maximum product must be some prefix product or suffix product. So in this solution, we compute the prefix product A and suffix product B, and simply return the maximum of A and B. 
            Why? Here's the proof:
            Say, we have a subarray A[i : j](i != 0, j != n) and the product of elements inside is P. Take P > 0 for example: if A[i] > 0 or A[j] > 0, then obviously, we should extend this subarray to include A[i] or A[j]; if both A[i] and A[j] are negative, then extending this subarray to include both A[i] and A[j] to get a larger product. Repeating this procedure and eventually we will reach the beginning or the end of A. 
            What if there are zeroes in the array? Well, we can split the array into several smaller ones. That's to say, when the prefix product is 0, we start over and compute prefix profuct from the current element instead. And this is exactly what A[i] *= (A[i - 1]) or 1 does. 
            
            Time: O(N), Space: O(1)
         */
        int n = nums.length, res = nums[0], l = 0, r = 0;
        for (int i = 0; i < n; i++) {
            l =  (l == 0 ? 1 : l) * nums[i];
            r =  (r == 0 ? 1 : r) * nums[n - 1 - i];
            res = Math.max(res, Math.max(l, r));
        }
        return res;
    }
}



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



// My Solution 2:
class Solution {
    public int maxProduct(int[] nums) {
        /*
            数学（前缀积），遍历时计算前缀积，同时维护一个当前最小正前缀积（初始值为 1）和当前最大负前缀积（初始值也为 1，若 nums[0] < 0 则为 nums[0]），然后用当前前缀积除以其中一个求较大值、然后再与 res 对比，然后如果当前前缀积比维护值的其中一个更优则替换，但因为最小正前缀积必然总是等于 1 且 num 为整形所以不用更新，但是这个过程有一个特殊情况需要使前缀积跳过重来 - 即当上一个数为 0 时全部维护值应重置且 preProduct[i] = nums[i]。
            Time: O(N), Space: O(N)
        */
        int n = nums.length, minPositive = 1, maxNegative = nums[0] < 0 ? nums[0] : 1, res = nums[0];
        int[] preProduct = new int[n];
        preProduct[0] = nums[0];
        for (int i=1; i<n; i++) {
            preProduct[i] = preProduct[i-1] * nums[i];
            if (nums[i-1] == 0) {
                maxNegative = 1;
                preProduct[i] = nums[i];
            }
            int tmpRes = Math.max(preProduct[i]/minPositive, preProduct[i]/maxNegative);
            res = Math.max(res, tmpRes);
            if (preProduct[i] < 0 && (preProduct[i] > maxNegative || maxNegative > 0)) maxNegative = preProduct[i];
        }
        
        return res;
    }
}
