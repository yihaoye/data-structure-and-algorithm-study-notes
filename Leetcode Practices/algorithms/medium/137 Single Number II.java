/*
Given a non-empty array of integers, every element appears three times except for one, which appears exactly once. Find that single one.

Note:

Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

Example 1:

Input: [2,2,3,2]
Output: 3
Example 2:

Input: [0,1,0,1,0,1,99]
Output: 99
*/



// Other's Solution (bit-manipulation):
class Solution {
    public int singleNumber(int[] nums) {
        // for Single Number I
        // XOR (^) is both commutative and associative 
        // The numbers which appear twice will be cancelled
        // Only the number that appear once will survive 
        
        /*
        First time number appear -> save it in "ones"
        Second time -> clear "ones" but save it in "twos" for later check
        Third time -> try to save in "ones" but value saved in "twos" clear it.
        */
        int ones = 0, twos = 0;
        for (int i = 0; i < nums.length; i++) {
            ones = (ones ^ nums[i]) & ~twos;
            twos = (twos ^ nums[i]) & ~ones;
        }
        return ones;
    }
}