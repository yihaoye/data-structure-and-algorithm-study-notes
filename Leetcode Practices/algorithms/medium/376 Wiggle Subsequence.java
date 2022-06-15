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



// My Solution:
class Solution {
    public int wiggleMaxLength(int[] nums) {
        // 标记变量
        // lastNegLen - 上一次 diff 为负数的最大长度, lastPosLen - 上一次 diff 为正数的最大长度
        // if (curDiff > 0) lastPosLen = Math.max(lastNegLen + 1, lastPosLen);
        // if (curDiff < 0) lastNegLen = Math.max(lastPosLen + 1, lastNegLen);
        // 同时维护一个 res = Math.max(lastNegLen, lastPosLen);
        // Time: O(N), Space: O(1)
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
