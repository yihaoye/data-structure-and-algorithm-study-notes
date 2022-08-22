/**
You are given a string num consisting of digits only.

Return the largest palindromic integer (in the form of a string) that can be formed using digits taken from num. It should not contain leading zeroes.

Notes:

You do not need to use all the digits of num, but you must use at least one digit.
The digits can be reordered.
 

Example 1:

Input: num = "444947137"
Output: "7449447"
Explanation: 
Use the digits "4449477" from "444947137" to form the palindromic integer "7449447".
It can be shown that "7449447" is the largest palindromic integer that can be formed.
Example 2:

Input: num = "00009"
Output: "9"
Explanation: 
It can be shown that "9" is the largest palindromic integer that can be formed.
Note that the integer returned should not contain leading zeroes.
 

Constraints:

1 <= num.length <= 10^5
num consists of digits.
 */



// My Solution:
class Solution {
    public String largestPalindromic(String num) {
        // 哈希表 + 贪心
        int[] map = new int[10];
        StringBuilder strB = new StringBuilder();
        for (char c : num.toCharArray()) {
            map[c-'0']++;
        }
        StringBuilder reverseStrB = new StringBuilder();
        for (int i=9; i>=0; i--) {
            if (i == 0 && strB.length() == 0) break;
            while (map[i] > 1) {
                strB.append(i);
                reverseStrB.append(i);
                map[i] -= 2;
            }
        }
        reverseStrB.reverse();
        for (int i=9; i>=0; i--) {
            if (map[i] > 0) {
                strB.append(i);
                break;
            }
        }
        strB.append(reverseStrB);
        return strB.toString();
    }
}
