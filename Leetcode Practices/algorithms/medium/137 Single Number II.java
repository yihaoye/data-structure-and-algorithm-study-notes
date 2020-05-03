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
/*
These are more details:

    VAR ^ 4,
    if there is no 4 in VAR, means SAVE 4 into VAR.
    if there is 4 in VAR, means CLEAR 4 from VAR.

    7 & (~4) = 3, means CLEAR 4 from 7.

    VAR & (-4), means CLEAR 4 form VAR.

For example, an array {1, 2, 2, 1, 1, 7, 2 }

        1        2       2       1         1       7        2
  ones [1]     [1,2]    [1]    [null]    [null]   [7,-2]   [7] (<=Answer)
  twos [null]  [null]   [2]    [1,2]     [2]      [null]   [null]
  // if above array has more than 1 element, it means result of that array equals sum of its elements, for example [1,2] = [3] for example [7,-2] = [5]
  // some of above display as [null] which is i.e. [0]
  
  Index form 0 to 6.....====>>>>>>
※ which [N] means the info in variables.
*/