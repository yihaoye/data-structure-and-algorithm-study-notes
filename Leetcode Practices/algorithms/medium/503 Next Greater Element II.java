/**
Given a circular integer array nums (i.e., the next element of nums[nums.length - 1] is nums[0]), return the next greater number for every element in nums.

The next greater number of a number x is the first greater number to its traversing-order next in the array, which means you could search circularly to find its next greater number. If it doesn't exist, return -1 for this number.

 

Example 1:

Input: nums = [1,2,1]
Output: [2,-1,2]
Explanation: The first 1's next greater number is 2; 
The number 2 can't find next greater number. 
The second 1's next greater number needs to search circularly, which is also 2.
Example 2:

Input: nums = [1,2,3,4,3]
Output: [2,3,4,-1,4]
 

Constraints:

1 <= nums.length <= 104
-109 <= nums[i] <= 109
 */



// My Solution:
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        /*
            单调栈
            Time: O(N), Space: O(N)
        */
        Deque<Integer> stack = new LinkedList<>();
        int len = nums.length;
        int[] res = new int[len];
        Arrays.fill(res, -1);
        for (int i=0; i<len*2; i++) {
            int j = i;
            if (j >= len) j -= len;
            while (!stack.isEmpty() && nums[j] > nums[stack.peek()]) {
                res[stack.peek()] = nums[j];
                stack.pop();
            }
            stack.push(j);
        }
        
        return res;
    }
}
