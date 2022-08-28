/**
Given an integer num, return a string representing its hexadecimal representation. For negative integers, two’s complement method is used.

All the letters in the answer string should be lowercase characters, and there should not be any leading zeros in the answer except for the zero itself.

Note: You are not allowed to use any built-in library method to directly solve this problem.

 

Example 1:

Input: num = 26
Output: "1a"
Example 2:

Input: num = -1
Output: "ffffffff"
 

Constraints:

-2^31 <= num <= 2^31 - 1
 */



// Other's Solution:
class Solution {
    char[] map = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
    
    public String toHex(int num) {
        // https://leetcode.com/problems/convert-a-number-to-hexadecimal/discuss/89253/Simple-Java-solution-with-comment
        if (num == 0) return "0";
        StringBuilder strB = new StringBuilder();
        while (num != 0) {
            strB.insert(0, map[(num & 15)]); 
            num = (num >>> 4);
        }
        return strB.toString();
    }
}
