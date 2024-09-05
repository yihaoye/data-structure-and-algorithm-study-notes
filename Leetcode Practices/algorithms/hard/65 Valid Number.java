/*
Given a string s, return whether s is a valid number.

For example, all the following are valid numbers: "2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789", while the following are not valid numbers: "abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53".

Formally, a valid number is defined using one of the following definitions:

An integer number followed by an optional exponent.
A decimal number followed by an optional exponent.
An integer number is defined with an optional sign '-' or '+' followed by digits.

A decimal number is defined with an optional sign '-' or '+' followed by one of the following definitions:

Digits followed by a dot '.'.
Digits followed by a dot '.' followed by digits.
A dot '.' followed by digits.
An exponent is defined with an exponent notation 'e' or 'E' followed by an integer number.

The digits are defined as one or more digits.

 

Example 1:

Input: s = "0"

Output: true

Example 2:

Input: s = "e"

Output: false

Example 3:

Input: s = "."

Output: false

 

Constraints:

1 <= s.length <= 20
s consists of only English letters (both uppercase and lowercase), digits (0-9), plus '+', minus '-', or dot '.'.
*/



// My Solution:
class Solution {
    public boolean isNumber(String s) {
        // String + Divide-and-conquer
        // recommand to forward to DFA solution
        int ePos = -1;
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) != 'e' && s.charAt(i) != 'E') continue;
            ePos = i; break;
        }
        if (ePos == -1) return isDecimal(s);
        if (s.length() == 1) return false;
        String s1 = s.substring(0, ePos), s2 = s.substring(ePos + 1);
        return isDecimal(s1) && isInteger(s2);
    }

    public boolean isDecimal(String s) { // include isInteger
        if (s.isBlank()) return false;
        int dotPos = -1;
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) != '.') continue;
            dotPos = i; break;
        }
        if (dotPos == -1) return isInteger(s);
        if (s.length() == 1) return false;
        String s1 = s.substring(0, dotPos), s2 = s.substring(dotPos + 1);
        if ((isInteger(s1) || isSign(s1) || s1.isBlank()) && isPureInteger(s2)) return true;
        if (isInteger(s1) && s2.isBlank()) return true;
        return false;
    }

    public boolean isInteger(String s) {
        if (s.isBlank()) return false;
        for (int i=0; i<s.length(); i++) {
            if (i == 0 && (s.charAt(i) == '-' || s.charAt(i) == '+') && s.length() != 1) continue;
            if ('0' <= s.charAt(i) && s.charAt(i) <= '9') continue;
            return false;
        }
        return true;
    }

    public boolean isPureInteger(String s) {
        if (s.isBlank()) return false;
        for (int i=0; i<s.length(); i++) {
            if ('0' <= s.charAt(i) && s.charAt(i) <= '9') continue;
            return false;
        }
        return true;
    }

    public boolean isSign(String s) {
        return s.equals("+") || s.equals("-");
    }
}