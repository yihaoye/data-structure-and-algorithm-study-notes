/**
You are given two positive integers startPos and endPos. Initially, you are standing at position startPos on an infinite number line. With one step, you can move either one position to the left, or one position to the right.

Given a positive integer k, return the number of different ways to reach the position endPos starting from startPos, such that you perform exactly k steps. Since the answer may be very large, return it modulo 109 + 7.

Two ways are considered different if the order of the steps made is not exactly the same.

Note that the number line includes negative integers.

 

Example 1:

Input: startPos = 1, endPos = 2, k = 3
Output: 3
Explanation: We can reach position 2 from 1 in exactly 3 steps in three ways:
- 1 -> 2 -> 3 -> 2.
- 1 -> 2 -> 1 -> 2.
- 1 -> 0 -> 1 -> 2.
It can be proven that no other way is possible, so we return 3.
Example 2:

Input: startPos = 2, endPos = 5, k = 10
Output: 0
Explanation: It is impossible to reach position 5 from position 2 in exactly 10 steps.
 

Constraints:

1 <= startPos, endPos, k <= 1000
 */



// Other's Solution:
class Solution {
    public int numberOfWays(int startPos, int endPos, int k) {
        // 数学（排列组合），向左、向右的次数是一定的，只是搭配不同
        long MOD = (long) (1e9) + 7;
        int diff = endPos - startPos; // 也即向右或向左的步数差，若 > 0 则说明向右步数比向左多，否则相反，== 0 说明一样
        if (Math.abs(diff) > k) return 0; // impossible
        if ((k - diff) % 2 != 0) return 0; // impossible
        
        int A = (k - diff) / 2;
        long res = 1;
        // 在 n 个事物中，选择 k 个事物出来，选择的顺序无所谓，那么选择的方式总共有这么多种：
        // C(n, k) = n! / ((n-k)! * k!) -> 在本题则是 C(k, A) -> k! / ((k-A)! * A!)
        for (int i=k; i>k-A; i--) { // res = k! / (k-A)!
            res = res * i % MOD;
        }
        for (int j=A; j>0; j--) { // res = res / A!
            res = res * fermatInv(j, MOD) % MOD; // 采用费马小定理的乘法逆元，因为除法取模与加减乘不同，可能出现不能整除的问题，所以需要获取 j 的逆元亦即使得 (res * j模MOD的逆元) % MOD，从而变相安全地实现 (res / j) % MOD
        }
        
        return (int) res;
    }

    private long fermatInv(long a, long MOD) { // 乘法逆元
        return powMod(a, MOD - 2, MOD);
    }
    
    private long powMod(long a, long n, long MOD) { // 快速幂取模
        long res = 1;
        while (n > 0) {
            if ((n & 1) == 1) res = res * a % MOD;
            a = a * a % MOD;
            n >>= 1;
        }
        return res;
    }
}
