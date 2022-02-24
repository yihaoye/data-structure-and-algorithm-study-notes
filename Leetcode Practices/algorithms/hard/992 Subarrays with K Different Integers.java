/*
Given an integer array nums and an integer k, return the number of good subarrays of nums.

A good array is an array where the number of different integers in that array is exactly k.

For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.
A subarray is a contiguous part of an array.

 

Example 1:

Input: nums = [1,2,1,2,3], k = 2
Output: 7
Explanation: Subarrays formed with exactly 2 different integers: [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2]
Example 2:

Input: nums = [1,2,1,3,4], k = 3
Output: 3
Explanation: Subarrays formed with exactly 3 different integers: [1,2,1,3], [2,1,3], [1,3,4].
 

Constraints:

1 <= nums.length <= 2 * 104
1 <= nums[i], k <= nums.length
*/





// Other's Solution:
class Solution {
    public int subarraysWithKDistinct(int[] nums, int k) {
        /*
            https://leetcode-cn.com/problems/subarrays-with-k-different-integers/solution/k-ge-bu-tong-zheng-shu-de-zi-shu-zu-by-l-ud34/
            时间复杂度 O(N)，空间复杂度 O(N)
        */
        return atMostKDistinct(nums, k) - atMostKDistinct(nums, k-1);
    }

    // 最多包含 k 个不同整数的子区间的个数
    private int atMostKDistinct(int[] nums, int k) {
        int len = nums.length;
        int[] freq = new int[len + 1];
        int left = 0, right = 0, res = 0;
        int count = 0; // [left, right) 里不同整数的个数
        
        while (right < len) {
            if (freq[nums[right]] == 0) count++;
            freq[nums[right]]++;
            right++;

            while (count > k) { // [left, right) 包含不同整数的个数小于等于 k
                freq[nums[left]]--;
                if (freq[nums[left]] == 0) count--;
                left++;
            }
            res += right-left; // [left, right) 区间的长度就是对结果的贡献
        }
        return res;
    }
}
