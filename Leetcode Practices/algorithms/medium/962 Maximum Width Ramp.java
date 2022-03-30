/*
A ramp in an integer array nums is a pair (i, j) for which i < j and nums[i] <= nums[j]. The width of such a ramp is j - i.

Given an integer array nums, return the maximum width of a ramp in nums. If there is no ramp in nums, return 0.

 

Example 1:

Input: nums = [6,0,8,2,1,5]
Output: 4
Explanation: The maximum width ramp is achieved at (i, j) = (1, 5): nums[1] = 0 and nums[5] = 5.
Example 2:

Input: nums = [9,8,1,0,1,9,4,0,4,1]
Output: 7
Explanation: The maximum width ramp is achieved at (i, j) = (2, 9): nums[2] = 1 and nums[9] = 1.
 

Constraints:

2 <= nums.length <= 5 * 104
0 <= nums[i] <= 5 * 104
*/





// Other's Solution:
class Solution {
    public int maxWidthRamp(int[] nums) {
        /*
            单调栈 - https://leetcode-cn.com/problems/maximum-width-ramp/solution/zui-da-kuan-du-po-dan-diao-zhan-cun-de-s-myj9/
            时间复杂度 O(N) 空间复杂度 O(N)
        */
        int n = nums.length, maxWidth = 0;
        Deque<Integer> stack = new LinkedList<>();
        for (int i=0; i < n; i++) {
            if (stack.isEmpty() || nums[stack.peek()] > nums[i]) {
                stack.push(i);
            }
        }

        for (int i=n-1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]) {
                int pos = stack.pop();
                maxWidth = Math.max(maxWidth, i - pos);
            }
        }
        
        return maxWidth;
    }
}
