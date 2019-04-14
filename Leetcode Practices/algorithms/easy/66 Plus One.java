/*
Given a non-empty array of digits representing a non-negative integer, plus one to the integer.

The digits are stored such that the most significant digit is at the head of the list, and each element in the array contain a single digit.

You may assume the integer does not contain any leading zero, except the number 0 itself.

Example 1:

Input: [1,2,3]
Output: [1,2,4]
Explanation: The array represents the integer 123.
Example 2:

Input: [4,3,2,1]
Output: [4,3,2,2]
Explanation: The array represents the integer 4321.
*/



// Other's Solution:
class Solution {
    public int[] plusOne(int[] digits) {
        int n = digits.length;
        for(int i=n-1; i>=0; i--) {
            if(digits[i] < 9) {
                digits[i]++;
                return digits;
            }

            digits[i] = 0;
        }

        int[] newNumber = new int [n+1];
        newNumber[0] = 1;

        return newNumber;
    }
}



// My Solution: still error may because data type limit (long, int and pow function)
class Solution {
    public int[] plusOne(int[] digits) {
        int len = digits.length;
        long temp = 0;
        
        for (int digit : digits) {
            temp += digit*Math.pow(10, len-1);
            len--;
        }
        temp++;
        
        len = digits.length;
        if (temp == Math.pow(10, len)) {
            len++;
        }
        int[] result = new int[len];
        for (int i = len-1; i >= 0; i--) {
            result[i] = (int)(temp%10);
            temp /= 10;
        }
        
        return result;
    }
}