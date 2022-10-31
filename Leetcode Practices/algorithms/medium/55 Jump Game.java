/*
Given an array of non-negative integers, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Determine if you are able to reach the last index.

Example 1:

Input: [2,3,1,1,4]
Output: true
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
Example 2:

Input: [3,2,1,0,4]
Output: false
Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.
*/



// My Solution:
class Solution {
    public boolean canJump(int[] nums) {
        int len = nums.length;
        int lastValid = len-1;
        for (int i=len-2; i>=0; i--) {
            if (nums[i]+i >= lastValid) lastValid = i;
        }
        
        return lastValid == 0;
    }
}



// Other's Solution:
class Solution {
    public boolean canJump(int[] nums) {
        /*
            贪心 - https://leetcode-cn.com/problems/jump-game/solution/55-by-ikaruga/
            时间复杂度 O(N)，空间复杂度 O(1)
        */
        int k = 0; // 前n-1个元素能够跳到的最远距离
        for (int i = 0; i <= k; i++) {
            int temp = i + nums[i]; // 第i个元素能够跳到的最远距离
            k = Math.max(k, temp); // 更新最远距离
            if (k >= nums.length - 1) return true; // 如果最远距离已经大于或等于最后一个元素的下标, 则说明能跳过去,退出减少循环
        }
        
        return false; // 最远距离 k 不再改变, 且没有到末尾元素
    }
}



// My Solution:
class Solution {
    public boolean canJump(int[] nums) {
        // 贪心
        int nextStart = 0, max = nums[0], end = nums.length-1;
        while (nextStart < max && nextStart < end) {
            nextStart++;
            max = Math.max(max, nextStart + nums[nextStart]);
        }
        
        return max >= end;
    }
}
