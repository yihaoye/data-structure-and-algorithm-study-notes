/**
You are given two positive integers n and target.

An integer is considered beautiful if the sum of its digits is less than or equal to target.

Return the minimum non-negative integer x such that n + x is beautiful. The input will be generated such that it is always possible to make n beautiful.

 

Example 1:

Input: n = 16, target = 6
Output: 4
Explanation: Initially n is 16 and its digit sum is 1 + 6 = 7. After adding 4, n becomes 20 and digit sum becomes 2 + 0 = 2. It can be shown that we can not make n beautiful with adding non-negative integer less than 4.
Example 2:

Input: n = 467, target = 6
Output: 33
Explanation: Initially n is 467 and its digit sum is 4 + 6 + 7 = 17. After adding 33, n becomes 500 and digit sum becomes 5 + 0 + 0 = 5. It can be shown that we can not make n beautiful with adding non-negative integer less than 33.
Example 3:

Input: n = 1, target = 1
Output: 0
Explanation: Initially n is 1 and its digit sum is 1, which is already smaller than or equal to target.
 

Constraints:

1 <= n <= 10^12
1 <= target <= 150
The input will be generated such that it is always possible to make n beautiful.
 */



// My Solution:
class Solution {
    public long makeIntegerBeautiful(long n, int target) {
        // 贪心 + 数学（每次对从最右往左数起第一个不为 0 的 digit 进行增加，因为这时整体必然未小于 target，所以必须一直增加到进位才会减少 sum，减少数量为 digit-1 而 x += 10-digit）
        int cal = cal(n);
        long carry = 1;
        long x = 0;
        
        while (cal > target) {
            while (n % 10 == 0) {
                n = n / 10;
                carry *= 10;
            }
            long digit = n % 10;
            n = n + 10 - digit;
            x += (10 - digit) * carry;
            cal = cal(n);
        }
        
        return x;
    }
    
    public int cal(long n) {
        int res = 0;
        while (n > 0) {
            res += n % 10;
            n = n / 10;
        }
        return res;
    }
}
