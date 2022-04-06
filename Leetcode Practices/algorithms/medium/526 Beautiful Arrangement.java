/**
Suppose you have n integers labeled 1 through n. A permutation of those n integers perm (1-indexed) is considered a beautiful arrangement if for every i (1 <= i <= n), either of the following is true:

perm[i] is divisible by i.
i is divisible by perm[i].
Given an integer n, return the number of the beautiful arrangements that you can construct.

 

Example 1:

Input: n = 2
Output: 2
Explanation: 
The first beautiful arrangement is [1,2]:
    - perm[1] = 1 is divisible by i = 1
    - perm[2] = 2 is divisible by i = 2
The second beautiful arrangement is [2,1]:
    - perm[1] = 2 is divisible by i = 1
    - i = 2 is divisible by perm[2] = 1
Example 2:

Input: n = 1
Output: 1
 

Constraints:

1 <= n <= 15
 */



// Other's Solution:
class Solution {
    public int countArrangement(int n) {
        /*
            状态压缩+DP - https://leetcode-cn.com/problems/beautiful-arrangement/solution/gong-shui-san-xie-xiang-jie-liang-chong-vgsia/
            时间复杂度：O(n * 2^n)，空间复杂度：O(2^n)
        */
        int mask = 1 << n;
        int[] f = new int[mask];
        f[0] = 1;

        for (int state = 1; state < mask; state++) { // 枚举所有的状态
            int cnt = getCnt(state); // 计算 state 有多少个 1（也就是当前排序长度为多少）
            for (int i = 0; i < n; i++) { // 枚举最后一位数值为多少
                if (((state >> i) & 1) == 0) continue; // 数值在 state 中必须是 1
                if ((i + 1) % cnt != 0 && cnt % (i + 1) != 0) continue; // 数值（i + 1）和位置（cnt）之间满足任一整除关系
                f[state] += f[state & (~(1 << i))]; // state & (~(1 << i)) 代表将 state 中所选数值的位置置零
            }
        }
        return f[mask - 1];
    }
    
    int getCnt(int x) {
        int res = 0;
        while (x != 0) {
            x -= (x & -x); // lowbit
            res++;
        }
        return res;
    }
}
