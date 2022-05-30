/**
You are given a 0-indexed integer array nums. In one step, remove all elements nums[i] where nums[i - 1] > nums[i] for all 0 < i < nums.length.

Return the number of steps performed until nums becomes a non-decreasing array.

 

Example 1:

Input: nums = [5,3,4,4,7,3,6,11,8,5,11]
Output: 3
Explanation: The following are the steps performed:
- Step 1: [5,3,4,4,7,3,6,11,8,5,11] becomes [5,4,4,7,6,11,11]
- Step 2: [5,4,4,7,6,11,11] becomes [5,4,7,11,11]
- Step 3: [5,4,7,11,11] becomes [5,7,11,11]
[5,7,11,11] is a non-decreasing array. Therefore, we return 3.
Example 2:

Input: nums = [4,5,7,7,13]
Output: 0
Explanation: nums is already a non-decreasing array. Therefore, we return 0.
 

Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 109
 */



// Other's Solution:
class Solution {
    public int totalSteps(int[] nums) {
        /*
            单调栈 + DP - https://leetcode.com/problems/steps-to-make-array-non-decreasing/discuss/2085864/JavaC%2B%2BPython-Stack-%2B-DP-%2B-Explanation
            dp[i] means the number of element A[i] need to eat.
            Iterative input array A reversely,
            If A[i] is bigger than the head element A[j] of stack,
            this means A[i] can eat that element,
            Then update dp[i] to be max of dp[i] + 1 and dp[j].
            Time O(n), Space O(n)
        */
        int n = nums.length, res = 0;
        int[] dp = new int[n];
        Deque<Integer> stack = new LinkedList<>();
        for (int i=n-1; i>=0; i--) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                dp[i] = Math.max(dp[i] + 1, dp[stack.poll()]);
                res = Math.max(res, dp[i]);
            }
            stack.push(i);
        }
        return res;
    }
}
