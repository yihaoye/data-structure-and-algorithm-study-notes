/**
Given two positive integers a and b, return the number of common factors of a and b.

An integer x is a common factor of a and b if x divides both a and b.

 

Example 1:

Input: a = 12, b = 6
Output: 4
Explanation: The common factors of 12 and 6 are 1, 2, 3, 6.
Example 2:

Input: a = 25, b = 30
Output: 2
Explanation: The common factors of 25 and 30 are 1, 5.
 

Constraints:

1 <= a, b <= 1000
 */



// My Solution:
class Solution {
    public int commonFactors(int a, int b) {
        int res = 0;
        int gcd = gcd(a, b);
        for (int i=1; i<=gcd; i++) {
            if (a % i == 0 && b % i == 0) {
                res++;
            }
        }
        return res;
    }
    
    public int gcd(int a, int b) {
        if (a == 0) return b; // 若 a 或 b 的任意一个为 0，则最大公约数就是另一个数
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
