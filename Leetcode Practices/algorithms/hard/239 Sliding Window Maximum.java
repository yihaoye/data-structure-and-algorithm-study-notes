/**
You are given an array of integers nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.

Return the max sliding window.

 

Example 1:

Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]
Explanation: 
Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
Example 2:

Input: nums = [1], k = 1
Output: [1]
 

Constraints:

1 <= nums.length <= 105
-104 <= nums[i] <= 104
1 <= k <= nums.length
 */



// Other's Solution:
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        // 单调栈 - https://leetcode.com/problems/sliding-window-maximum/discuss/65884/Java-O(n)-solution-using-deque-with-explanation
        // Time: O(N), Space: O(k)
        if (nums == null || k <= 0) {
			return new int[0];
		}
		int n = nums.length, resIndex = 0;
		int[] res = new int[n-k+1];
		// store index
		Deque<Integer> q = new ArrayDeque<>();
		for (int i=0; i<nums.length; i++) {
			// remove numbers out of range k
			while (!q.isEmpty() && q.peek() < i-k+1) q.poll();
			
			// remove smaller numbers in k range as they are useless
			while (!q.isEmpty() && nums[q.peekLast()] < nums[i]) q.pollLast();
			
			// q contains index... res contains content
			q.offer(i);
			if (i >= k-1) res[resIndex++] = nums[q.peek()];
		}
        
		return res;
    }
}
