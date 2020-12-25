/*
Given two non-negative integers num1 and num2 represented as string, return the sum of num1 and num2.

Note:

The length of both num1 and num2 is < 5100.
Both num1 and num2 contains only digits 0-9.
Both num1 and num2 does not contain any leading zero.
You must not use any built-in BigInteger library or convert the inputs to integer directly.
*/



// My Solution:
class Solution {
    public String addStrings(String num1, String num2) {
        int carry = 0;
        StringBuilder strB = new StringBuilder();
        for (int i=num1.length()-1, j=num2.length()-1; i>=0 || j>=0; i--, j--) {
            int n1 = (i >= 0) ? num1.charAt(i)-'0' : 0;
            int n2 = (j >= 0) ? num2.charAt(j)-'0' : 0;
            int sum = n1 + n2 + carry;
            carry = sum/10;
            strB.append(sum%10);
        }
        if (carry > 0) strB.append(carry);
        return strB.reverse().toString();
    }
}
