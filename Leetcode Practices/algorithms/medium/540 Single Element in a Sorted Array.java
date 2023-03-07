/**
You are given a sorted array consisting of only integers where every element appears exactly twice, except for one element which appears exactly once.

Return the single element that appears only once.

Your solution must run in O(log n) time and O(1) space.

 

Example 1:

Input: nums = [1,1,2,3,3,4,4,8,8]
Output: 2
Example 2:

Input: nums = [3,3,7,7,10,11,11]
Output: 10
 

Constraints:

1 <= nums.length <= 10^5
0 <= nums[i] <= 10^5
 */



// My Solution:
class Solution {
    public int singleNonDuplicate(int[] nums) {
        // binary search
        // nums.length must be odd
        // bs -> must target the middle first
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int m = l + (r - l) / 2;
            if (m < nums.length - 1 && nums[m] == nums[m+1]) {
                if ((m-l) % 2 != 0) r = m - 1;
                else l = m;
            } else if (m > 0 && nums[m] == nums[m-1]) {
                if ((r-m) % 2 != 0) l = m + 1;
                else r = m;
            } else {
                return nums[m];
            }
        }
        return nums[l]; // l == r
    }
}
