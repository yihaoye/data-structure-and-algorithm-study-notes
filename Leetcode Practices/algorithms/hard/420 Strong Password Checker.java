/*
A password is considered strong if the below conditions are all met:

It has at least 6 characters and at most 20 characters.
It contains at least one lowercase letter, at least one uppercase letter, and at least one digit.
It does not contain three repeating characters in a row (i.e., "Baaabb0" is weak, but "Baaba0" is strong).
Given a string password, return the minimum number of steps required to make password strong. if password is already strong, return 0.

In one step, you can:

Insert one character to password,
Delete one character from password, or
Replace one character of password with another character.
 

Example 1:

Input: password = "a"
Output: 5
Example 2:

Input: password = "aA1"
Output: 3
Example 3:

Input: password = "1337C0d3"
Output: 0
 

Constraints:

1 <= password.length <= 50
password consists of letters, digits, dot '.' or exclamation mark '!'.
*/



// My Solution:
class Solution {
    public int strongPasswordChecker(String password) {
        // String + Greedy + Simulate + DP
        // fix invalid part first and then fullfill the rest
        // only delete if password length exceed 20 (otherwise replace is always more powerful and straightforward)
        // only insert if password length less then 6 (otherwise replace is always more powerful and straightforward)
        int res = 0;
        boolean hasLowercase = false, hasUppercase = false, hasDigit = false;
        int[] repeatIndex = new int[password.length()]; // repeatIndex[i] means first continue repeat index
        int[] repeatSubLen = new int[password.length()]; // repeatSubLen record repeat sub array start to end length, if i between start and end, it will be 0
        int needInsert = 0, needDelete = 0, min = 6, max = 20;
        if (password.length() < min) needInsert = min - password.length();
        else if (password.length() > max) needDelete = password.length() - max;
        for (int i=0; i<password.length(); i++) {
            char c = password.charAt(i);
            if ('0' <= c && c <= '9') hasDigit = true;
            if ('a' <= c && c <= 'z') hasLowercase = true;
            if ('A' <= c && c <= 'Z') hasUppercase = true;

            if (i >=1 && password.charAt(i-1) == c) repeatIndex[i] = repeatIndex[i-1];
            else repeatIndex[i] = i;

            repeatSubLen[repeatIndex[i]] = i - repeatIndex[i] + 1;
        }
        for (int i=0; i<password.length(); i++) {
            if (needDelete == 0) break;
            if (repeatSubLen[i] >= 3 && repeatSubLen[i] % 3 == 0) {
                res++;
                repeatSubLen[i]--;
                needDelete--;
            }
        }
        for (int i=0; i<password.length(); i++) {
            if (needDelete < 2) break;
            if (repeatSubLen[i] == 4 && needDelete >= 2) {
                res += 2;
                repeatSubLen[i] -= 2;
                needDelete -= 2;
            }
        }
        
        for (int i=0; i<password.length(); i++) {
            while (repeatSubLen[i] >= 3) {
                res++;
                if (needInsert > 0) {
                    needInsert--;
                    repeatSubLen[i] -= 2;
                    if (!hasLowercase) hasLowercase = true;
                    else if (!hasUppercase) hasUppercase = true;
                    else if (!hasDigit) hasDigit = true;
                } else if (needDelete > 0) {
                    repeatSubLen[i] -= 1;
                    needDelete--;
                } else { // replace
                    repeatSubLen[i] -= 3;
                    if (!hasLowercase) hasLowercase = true;
                    else if (!hasUppercase) hasUppercase = true;
                    else if (!hasDigit) hasDigit = true;
                }
            }
        }
        if (!hasLowercase) {
            res++;
            if (needInsert > 0) needInsert--; // otherwise replace
        }
        if (!hasUppercase) {
            res++;
            if (needInsert > 0) needInsert--; // otherwise replace
        }
        if (!hasDigit) {
            res++;
            if (needInsert > 0) needInsert--; // otherwise replace
        }
        if (needInsert > 0) res += needInsert;
        if (needDelete > 0) res += needDelete;
        
        return res;
    }
}