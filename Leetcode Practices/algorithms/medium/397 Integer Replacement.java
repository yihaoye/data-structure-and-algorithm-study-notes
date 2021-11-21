/*
Given a positive integer n, you can apply one of the following operations:

If n is even, replace n with n / 2.
If n is odd, replace n with either n + 1 or n - 1.
Return the minimum number of operations needed for n to become 1.

 

Example 1:

Input: n = 8
Output: 3
Explanation: 8 -> 4 -> 2 -> 1
Example 2:

Input: n = 7
Output: 4
Explanation: 7 -> 8 -> 4 -> 2 -> 1
or 7 -> 6 -> 3 -> 2 -> 1
Example 3:

Input: n = 4
Output: 2
 

Constraints:

1 <= n <= 231 - 1
*/



// My Solution:
class Solution {
    public int integerReplacement(int n) {
        /*
            思路 - 数学+二进制，最优是尽可能成为 2^x 数字然后一路除以 2，因为除 2 是最快路径（整体右移一位），2^x 即二进制的只有单个位为 1，当 n 为奇数时意味着最右第一位为 1，所以处理最终逻辑是检查最右第一位若遇到连续 0 则以右移处理假设连续 y 则为 y 步并进行右移然后再检查，若遇着连续 1 则 +1 后按连续 0 处理；（设左为 +1 后除 2，右为 -1 后除 2）00111 - 01000 - 01: 4步, 00111 - 001 - 01: 4+2步 选择左，若 10111 - 11 - 1: 4+2步, 10111 - 101 - 1: 4+3步 选择 左 ｜ 0011 - 0100 - 01 - 00: 3+1步, 0011 - 001 - 00: 2+2步 选择左 ｜ 1011 - 11 - 1: 3+2步, 1011 - 10 - 1: 4+1步 选择左 | 001 应选右（该处细节参考了 https://leetcode-cn.com/problems/integer-replacement/solution/gong-shui-san-xie-yi-ti-san-jie-dfsbfs-t-373h/）
            时间复杂度 O(logN) ?，空间复杂度 O(1)
        */
        int res = 0;
        if (n == Integer.MAX_VALUE) return 31+1;
        while (n != 0 && n != 1) {
            if ((n & 1) == 0) {
                n = n >> 1;
            } else if (n == 3 || (n & 3) == 1) {
                n--;
            } else {
                n++;
            }
            res++;
        }
        
        return res;
    }
}
