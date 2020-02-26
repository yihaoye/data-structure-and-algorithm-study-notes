/*
Given an array, rotate the array to the right by k steps, where k is non-negative.

Example 1:

Input: [1,2,3,4,5,6,7] and k = 3
Output: [5,6,7,1,2,3,4]
Explanation:
rotate 1 steps to the right: [7,1,2,3,4,5,6]
rotate 2 steps to the right: [6,7,1,2,3,4,5]
rotate 3 steps to the right: [5,6,7,1,2,3,4]
Example 2:

Input: [-1,-100,3,99] and k = 2
Output: [3,99,-1,-100]
Explanation: 
rotate 1 steps to the right: [99,-1,-100,3]
rotate 2 steps to the right: [3,99,-1,-100]
Note:

Try to come up as many solutions as you can, there are at least 3 different ways to solve this problem.
Could you do it in-place with O(1) extra space?
*/



// My Solution 1:
class Solution {
    public void rotate(int[] nums, int k) {
        int len = nums.length;
        
        // edge case handle
        if (k > len) k = k%len;
        if (len == 1 || k == 0) return;
        
        // shift process
        shift(nums, len, k);
    }
    
    public void shift(int[] nums, int len, int k) {
        int tmp = 0;
        for (int i=0; i<len; i+=k) {
            for (int j=0; j<k && i+k+j<len; j++) {
                tmp = nums[i+k+j];
                nums[i+k+j] = nums[j];
                nums[j] = tmp;
            }
        }
        if (len%k != 0) shift(nums, k, k-len%k);
    }
    
    /* example */
    // [1,2,3,4,5,6,7,8] and k=3
    // after shift:
    // tmp=8
    // [7,8,6,1,2,3,4,5]
    // after nested shift: (for [7,8,6], len=3, k=1)
    // tmp=6
    // [6,7,8,1,2,3,4,5]
    
    // Although nested method, performance is ok hedged since either shift() or nested shift() take time or both similar average.
}



// Other's Solution:
class Solution2 {
    public void rotate(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
}