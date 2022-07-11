/**
Given an array of n integers nums, a 132 pattern is a subsequence of three integers nums[i], nums[j] and nums[k] such that i < j < k and nums[i] < nums[k] < nums[j].

Return true if there is a 132 pattern in nums, otherwise, return false.

 

Example 1:

Input: nums = [1,2,3,4]
Output: false
Explanation: There is no 132 pattern in the sequence.
Example 2:

Input: nums = [3,1,4,2]
Output: true
Explanation: There is a 132 pattern in the sequence: [1, 4, 2].
Example 3:

Input: nums = [-1,3,2,0]
Output: true
Explanation: There are three 132 patterns in the sequence: [-1, 3, 2], [-1, 3, 0] and [-1, 2, 0].
 

Constraints:

n == nums.length
1 <= n <= 2 * 10^5
-109 <= nums[i] <= 10^9
 */



// Other's Solution:
class Solution {
    public boolean find132pattern(int[] nums) {
        // 单调栈 - https://leetcode.com/problems/132-pattern/discuss/906789/Short-Java-O(N)-Solution-with-Detailed-Explanation-and-Sample-Test-Case-or-Stack-or-100
        // Time: O(N), Space: O(N)
        Deque<Integer> stack = new LinkedList<>(); // 单调递减的栈（从数组的右边向左边遍历的）
        int second = Integer.MIN_VALUE; // second 总是更新为从右往左可以找到的最大的 2（for 里 3 行代码的逻辑结合以保证，因为如果新的 nums[i] 小于 second 时将直接返回 true 而不是会在后续 nums[j] > nums[i] 时 second 被更小的 nums[i] 替换），而只要 second 被设置过，stack 里肯定不为空且已有满足 2 的 3，不用关心 3 有多大（栈里的 3 可能有多个且必然都比 2/second 大，而且总是有栈顶大于 2/second 即栈顶必然可以为 3），因为只要接下来任意一个 nums[i] < 2 时，模式 132 必然已经满足
        for (int i=nums.length-1; i>=0; i--) {
            if (nums[i] < second) return true;
            while (!stack.isEmpty() && nums[i] > stack.peek()) second = stack.pop();
            stack.push(nums[i]);
        }
        return false;
    }
}
