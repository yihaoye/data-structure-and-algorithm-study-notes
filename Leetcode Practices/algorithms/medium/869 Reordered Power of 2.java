/**
You are given an integer n. We reorder the digits in any order (including the original order) such that the leading digit is not zero.

Return true if and only if we can do this so that the resulting number is a power of two.

 

Example 1:

Input: n = 1
Output: true
Example 2:

Input: n = 10
Output: false
 

Constraints:

1 <= n <= 10^9
 */



// Other's Solution:
class Solution {
    public boolean reorderedPowerOf2(int n) {
        /*
            https://leetcode.com/problems/reordered-power-of-2/discuss/149843/C%2B%2BJavaPython-Straight-Forward
            counter will count the number of digits 9876543210 in the given number.
            Then just compare counter(N) with all counter(power of 2).
            1 <= N <= 10^9, so up to 8 same digits.
            If N > 10^9, we can use a hash map.
        */
        long c = counter(n);
        for (int i = 0; i < 32; i++)
            if (counter(1 << i) == c) return true;
        
        return false;
    }
    
    public long counter(int n) { // 其实是 hash
        long res = 0;
        for (; n > 0; n /= 10) res += (int) Math.pow(10, n % 10);
        return res;
    }
}
