/*
A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).

Write a function to determine if a number is strobogrammatic. The number is represented as a string.

Example 1:

Input:  "69"
Output: true
Example 2:

Input:  "88"
Output: true
Example 3:

Input:  "962"
Output: false
*/



// My Solution:
class Solution {
    char[] digits1 = "018".toCharArray();
    char[] digits2 = "69".toCharArray();
    
    public boolean isStrobogrammatic(String num) {
        char[] digitArr = num.toCharArray();
        int len = digitArr.length;
        for (int i=0; i < len/2; i++) {
            if (!validDigitPair(digitArr[i], digitArr[len-1-i])) return false;
        }
        if (len%2 == 1) {
            if (digits1[0] != digitArr[len/2] && digits1[1] != digitArr[len/2] && digits1[2] != digitArr[len/2]) return false;
        }
        return true;
    }
    
    public boolean validDigitPair(char digit1, char digit2) {
        if (digit1 == digit2) {
            for (char digit : digits1) {
                if (digit == digit1) return true;
            }
        } else if ((digits2[0] == digit1 && digits2[1] == digit2) || (digits2[1] == digit1 && digits2[0] == digit2)) {
            return true;
        }
        return false;
    }
}