/*
Given an integer n, count the total number of digit 1 appearing in all non-negative integers less than or equal to n.

 

Example 1:

Input: n = 13
Output: 6
Example 2:

Input: n = 0
Output: 0
 

Constraints:

0 <= n <= 10^9
*/



// Other's Solution:
class Solution {
    public int countDigitOne(int n) {
        // Math - https://leetcode.com/problems/number-of-digit-one/solutions/64381/4-lines-o-log-n-c-java-python/
        int ones = 0;
        for (long m = 1; m <= n; m *= 10) {
            long a = n / m, b = n % m;
            ones += (a + 8) / 10 * m + (a % 10 == 1 ? b + 1 : 0);
        }
        return ones;
    }
}
/*
The case distincton between the current digit/position (a 的个位数) being 0, 1 and >=2 can easily be done in one expression. With (a + 8) / 10 you get the number of full streaks

if a%10 >= 2, then (a+8)/10 = a/10+1
if a%10 == 1, (a+8)/10 = a/10
if a%10 == 0, (a+8)/10 = a/10
*/
