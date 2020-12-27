/*
Given an array nums with n integers, your task is to check if it could become non-decreasing by modifying at most 1 element.

We define an array is non-decreasing if nums[i] <= nums[i + 1] holds for every i (0-based) such that (0 <= i <= n - 2).

 

Example 1:

Input: nums = [4,2,3]
Output: true
Explanation: You could modify the first 4 to 1 to get a non-decreasing array.
Example 2:

Input: nums = [4,2,1]
Output: false
Explanation: You can't get a non-decreasing array by modify at most one element.
 

Constraints:

1 <= n <= 10 ^ 4
- 10 ^ 5 <= nums[i] <= 10 ^ 5
*/



// My Solution (flag):
class Solution {
    public boolean checkPossibility(int[] nums) {
        int[] dec1 = new int[]{-1, 0};
        int flag = 0;
        for (int i=1; i<nums.length; i++) {
            if (nums[i] < nums[i-1]) {
                if (flag > 0) return false;
                flag++;
                
                dec1[0] = i-1;
                dec1[1] = nums[i-1];
            }
        }
        boolean tmp1 = true, tmp2 = true;
        if (dec1[0] != -1 && dec1[0] != nums.length-2 && dec1[0] != 0) {
            nums[dec1[0]] = nums[dec1[0]-1];
            for (int i=1; i<nums.length; i++) if (nums[i] < nums[i-1]) tmp1 = false;
            nums[dec1[0]] = dec1[1];
            nums[dec1[0]+1] = nums[dec1[0]];
            for (int i=1; i<nums.length; i++) if (nums[i] < nums[i-1]) tmp2 = false;
        }
        
        return tmp1 || tmp2;
    }
}



// Other's Solution:
class Solution {
    public boolean checkPossibility(int[] nums) {
        int cnt = 0; // the number of changes
        for (int i=1; i<nums.length && cnt<=1; i++) {
            if (nums[i-1] > nums[i]) {
                cnt++;
                if (i < 2 || nums[i-2] <= nums[i]) nums[i-1] = nums[i]; // modify nums[i-1] of a priority
                else nums[i] = nums[i-1]; // have to modify nums[i]
            }
        }
        return cnt <= 1;
    }
}
/*
Input: [3,4,2,3]
Expected: false
*/
