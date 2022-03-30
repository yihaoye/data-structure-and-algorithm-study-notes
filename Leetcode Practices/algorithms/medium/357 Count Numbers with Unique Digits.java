/*
Given an integer n, return the count of all numbers with unique digits, x, where 0 <= x < 10n.

 

Example 1:

Input: n = 2
Output: 91
Explanation: The answer should be the total numbers in the range of 0 ≤ x < 100, excluding 11,22,33,44,55,66,77,88,99
Example 2:

Input: n = 0
Output: 1
 

Constraints:

0 <= n <= 8
*/



// Other's Solution:
class Solution {
    public int countNumbersWithUniqueDigits(int n) {
        /*
            数学 - https://leetcode.com/problems/count-numbers-with-unique-digits/discuss/83061/Java-O(1)-with-explanation
            时间复杂度 O(1)，空间复杂度 O(1)
        */
        if (n == 0) {
            return 1;
        }
        int res = 10, base = 9;
        for (int i = 2; i <= n && i <= 10; i++) {
            base = base * (9 - i + 2);
            res += base;
        }
        return res;
    }
}
