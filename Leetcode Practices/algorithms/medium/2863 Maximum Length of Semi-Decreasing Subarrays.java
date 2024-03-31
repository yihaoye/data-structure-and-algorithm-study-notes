/*
You are given an integer array nums.

Return the length of the longest semi-decreasing subarray of nums, and 0 if there are no such subarrays.

A subarray is a contiguous non-empty sequence of elements within an array.
A non-empty array is semi-decreasing if its first element is strictly greater than its last element.
 

Example 1:

Input: nums = [7,6,5,4,3,2,1,6,10,11]
Output: 8
Explanation: Take the subarray [7,6,5,4,3,2,1,6].
The first element is 7 and the last one is 6 so the condition is met.
Hence, the answer would be the length of the subarray or 8.
It can be shown that there aren't any subarrays with the given condition with a length greater than 8.
Example 2:

Input: nums = [57,55,50,60,61,58,63,59,64,60,63]
Output: 6
Explanation: Take the subarray [61,58,63,59,64,60].
The first element is 61 and the last one is 60 so the condition is met.
Hence, the answer would be the length of the subarray or 6.
It can be shown that there aren't any subarrays with the given condition with a length greater than 6.
Example 3:

Input: nums = [1,2,3,4]
Output: 0
Explanation: Since there are no semi-decreasing subarrays in the given array, the answer is 0.
 

Constraints:

1 <= nums.length <= 10^5
-10^9 <= nums[i] <= 10^9
*/



// Other's Solution:
class Solution {
    public int maxSubarrayLength(int[] nums) {
        // 单调栈 + 贪心 - https://leetcode.com/problems/maximum-length-of-semi-decreasing-subarrays/
        // Time: O(N)
        Deque<Integer> st = new LinkedList<>();
        int n = nums.length;
        for(int i=0; i<n; i++) {
            if(st.isEmpty() || nums[st.peek()] < nums[i]) st.push(i);
        }
        int ans = 0;
        for(int i=n-1; i>=0; i--) {
            while(!st.isEmpty() && nums[st.peek()] > nums[i]) {
                ans = Math.max(ans, i - st.peek() + 1);
                st.pop();
            }
        }
        return ans;
    }
}
/*
We'll use a stack to keep track of indices of elements in the array. First, we'll go through the array from left to right and keep pushing
indices into the stack if the current element is greater than the top element of the stack.

Then, we'll iterate from right to left and check how far we can
extend a semi-decreasing subarray for each element.

We'll pop elements from the stack and update the maximum length
when we find an element that is greater than the current element.
*/



// My Solution:
class Solution {
    public int maxSubarrayLength(int[] nums) {
        // DP + 二分搜索
        // Time: O(N*logN), Space: O(N)
        int res = 0;
        TreeSet<Integer> tSet = new TreeSet<>(); // map<nums[i]>
        Map<Integer, Integer> dp = new HashMap<>(); // map<nums[i], leftestIndex of num >= nums[i]>
        for (int i=0; i<nums.length; i++) {
            if (tSet.add(nums[i])) {
                dp.put(nums[i], i);
            }
            Integer higherNum = tSet.higher(nums[i]);
            if (higherNum != null) {
                res = Math.max(res, i - dp.get(higherNum) + 1);
                int update = Math.min(dp.get(nums[i]), dp.get(higherNum));
                dp.put(nums[i], update);
            }
        }
        return res;
    }
}