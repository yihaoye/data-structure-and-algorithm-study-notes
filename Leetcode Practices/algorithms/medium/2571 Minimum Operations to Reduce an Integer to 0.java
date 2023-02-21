/**
You are given a positive integer n, you can do the following operation any number of times:

Add or subtract a power of 2 from n.
Return the minimum number of operations to make n equal to 0.

A number x is power of 2 if x == 2^i where i >= 0.

 

Example 1:

Input: n = 39
Output: 3
Explanation: We can do the following operations:
- Add 20 = 1 to n, so now n = 40.
- Subtract 23 = 8 from n, so now n = 32.
- Subtract 25 = 32 from n, so now n = 0.
It can be shown that 3 is the minimum number of operations we need to make n equal to 0.
Example 2:

Input: n = 54
Output: 3
Explanation: We can do the following operations:
- Add 21 = 2 to n, so now n = 56.
- Add 23 = 8 to n, so now n = 64.
- Subtract 26 = 64 from n, so now n = 0.
So the minimum number of operations is 3.
 

Constraints:

1 <= n <= 10^5
 */



// My Solution:
class Solution {
    public int minOperations(int n) {
        // 贪心 + 位操作 - 计算 n 的二进制，比如 010011101，可以加一个 000000100 从而变成 010100001，然后再减去 3 个 1 即可
        // 具体来说，从低位先往高位检查，如果目前找到一个落单的 1，则将它减去，若是有任何连续的 1 则先对连续 1 的最低 1 位加 1，然后重复
        // 01011000 -> 01100000 -> 10000000 -> 0, 若是减则 01011000 -> 00011000 -> 00001000 -> 0
        boolean[] bits = new boolean[32];
        // convert n to binary array
        int res = 0, i = 0;
        while (n > 0) {
            bits[i++] = (n & 1) == 1;
            n = (n >> 1);
        }
        for (int j=0; j<32; j++) {
            if (bits[j] == false) continue;
            
            res++;
            int k = j, cnt = 0;
            while (k < 32 && bits[k] == true) {
                bits[k++] = false;
                cnt++;
            }
            if (cnt > 1 && k < 32) {
                bits[k] = true;
            }
        }
        return res;
    }
}
