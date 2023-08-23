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



// My Solution 2:
class Solution {
    public String addStrings(String num1, String num2) {
        int carry = 0;
        StringBuilder strB = new StringBuilder();

        // 处理如果带浮点数 e.g. "345.9999", "22.1", "10.0"
        int point1 = num1.indexOf("."); int point2 = num2.indexOf(".");
        if (point1 != -1 && point2 != -1) {
            int precision1 = num1.length() - point1; int precision2 = num2.length() - point2;
            if (precision1 > precision2) 
                for (int i=0; i<precision1-precision2; i++) num2 = num2 + "0";
            if (precision2 > precision1)
                for (int i=0; i<precision2-precision1; i++) num1 = num1 + "0";
        }

        for (int i=num1.length()-1, j=num2.length()-1; i>=0 || j>=0; i--, j--) {
            if (i == point1 && j == point2) {
                strB.append(".");
                continue;
            }
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
