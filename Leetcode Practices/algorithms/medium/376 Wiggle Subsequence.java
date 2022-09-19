/**
A wiggle sequence is a sequence where the differences between successive numbers strictly alternate between positive and negative. The first difference (if one exists) may be either positive or negative. A sequence with one element and a sequence with two non-equal elements are trivially wiggle sequences.

For example, [1, 7, 4, 9, 2, 5] is a wiggle sequence because the differences (6, -3, 5, -7, 3) alternate between positive and negative.
In contrast, [1, 4, 7, 2, 5] and [1, 7, 4, 5, 5] are not wiggle sequences. The first is not because its first two differences are positive, and the second is not because its last difference is zero.
A subsequence is obtained by deleting some elements (possibly zero) from the original sequence, leaving the remaining elements in their original order.

Given an integer array nums, return the length of the longest wiggle subsequence of nums.

 

Example 1:

Input: nums = [1,7,4,9,2,5]
Output: 6
Explanation: The entire sequence is a wiggle sequence with differences (6, -3, 5, -7, 3).
Example 2:

Input: nums = [1,17,5,10,13,15,10,5,16,8]
Output: 7
Explanation: There are several subsequences that achieve this length.
One is [1, 17, 10, 13, 10, 16, 8] with differences (16, -7, 3, -3, 6, -8).
Example 3:

Input: nums = [1,2,3,4,5,6,7,8,9]
Output: 2
 

Constraints:

1 <= nums.length <= 1000
0 <= nums[i] <= 1000
 

Follow up: Could you solve this in O(n) time?
 */



// Other's Solution:
class Solution {
    public int wiggleMaxLength(int[] nums) {
        /*
         动态规划 - https://leetcode.cn/problems/wiggle-subsequence/solution/tan-xin-si-lu-qing-xi-er-zheng-que-de-ti-jie-by-lg/
         假设 up[i] 表示 nums[0:i] 中最后两个数字递增的最长摆动序列长度，down[i] 表示 nums[0:i] 中最后两个数字递减的最长摆动序列长度，只有一个数字时默认为 1。
            1. nums[i+1] > nums[i]
                假设 down[i] 表示的最长摆动序列的最远末尾元素下标正好为 i，遇到新的上升元素后，up[i+1] = down[i] + 1 ，这是因为 up 一定从 down 中产生（初始除外），并且 down[i] 此时最大。
                假设 down[i] 表示的最长摆动序列的最远末尾元素下标小于 i，设为 j，那么 nums[j:i] 一定是递增的，因为若完全递减，最远元素下标等于 i，若波动，那么 down[i] > down[j]。由于 nums[j:i] 递增，down[j:i] 一直等于 down[j] ，依然满足 up[i+1] = down[i] + 1 。
            2. nums[i+1] < nums[i]，类似第一种情况
            3. nums[i+1] == nums[i]，新的元素不能用于任何序列，保持不变
        */
        int n = nums.length;
        if (n < 2) return n;
        int lastNegLen = nums[1] - nums[0] >= 0 ? 1 : 2, lastPosLen = nums[1] - nums[0] <= 0 ? 1 : 2; // 为什么初始值为 2 的情况？因为下面的遍历从 i=2 出发，且 nums[1] - nums[0] > 0 时 i=1 时 lastNegLen 必为 2
        int res = Math.max(lastNegLen, lastPosLen); // 注意特殊情况：nums[1] == nums[0] 时，为 1
        for (int i=2; i<n; i++) {
            int curDiff = nums[i] - nums[i-1];
            if (curDiff > 0) lastPosLen = Math.max(lastNegLen + 1, lastPosLen);
            if (curDiff < 0) lastNegLen = Math.max(lastPosLen + 1, lastNegLen);
            res = Math.max(lastNegLen, lastPosLen);
        }
        
        return res;
    }
}
