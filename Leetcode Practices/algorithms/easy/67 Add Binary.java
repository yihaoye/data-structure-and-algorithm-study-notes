/*
Given two binary strings, return their sum (also a binary string).

The input strings are both non-empty and contains only characters 1 or 0.

Example 1:

Input: a = "11", b = "1"
Output: "100"
Example 2:

Input: a = "1010", b = "1011"
Output: "10101"
*/



// My Solution:
class Solution {
    public String addBinary(String a, String b) {
        char[] aChars = a.toCharArray();
        char[] bChars = b.toCharArray();
        int len = aChars.length > bChars.length ? aChars.length : bChars.length;
        char[] res = new char[len];
        char carry = '0';
        for (int i=len-1, j=aChars.length-1, k=bChars.length-1; i>=0; i--, j--, k--) {
            char byteA = j >= 0 ? aChars[j] : '0';
            char byteB = k >= 0 ? bChars[k] : '0';
            if (byteA == '1' && byteB == '1') {
                res[i] = carry;
                carry = '1';
            } else if (byteA == '1' || byteB == '1') {
                if (carry == '1') res[i] = '0';
                else res[i] = '1';
            } else {
                res[i] = carry;
                carry = '0';
            }
        }
        StringBuilder strB = new StringBuilder();  
        if (carry == '1') strB.append(carry);
        for (char byteC : res) strB.append(byteC);
        
        return strB.toString();
    }
}