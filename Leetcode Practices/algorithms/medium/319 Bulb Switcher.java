/*
There are n bulbs that are initially off. You first turn on all the bulbs, then you turn off every second bulb.

On the third round, you toggle every third bulb (turning on if it's off or turning off if it's on). For the ith round, you toggle every i bulb. For the nth round, you only toggle the last bulb.

Return the number of bulbs that are on after n rounds.

 

Example 1:
https://assets.leetcode.com/uploads/2020/11/05/bulb.jpg

Input: n = 3
Output: 1
Explanation: At first, the three bulbs are [off, off, off].
After the first round, the three bulbs are [on, on, on].
After the second round, the three bulbs are [on, off, on].
After the third round, the three bulbs are [on, off, off]. 
So you should return 1 because there is only one bulb is on.
Example 2:

Input: n = 0
Output: 0
Example 3:

Input: n = 1
Output: 1
 

Constraints:

0 <= n <= 109
*/



// My Solution:
class Solution {
    public int bulbSwitch(int n) {
        /*
            数学，默认为全关，然后从第 1 轮至最后都是非门，其实就是计算 i 能整除 [1...i] 中多少个数字，若数量为基数则开灯、偶数为关灯，而实际上若能被 j 整除得到 k（且 k 不等于 j）则必也能被 k 整除即此情况为原状态不变（情况包括 1 和 i 自己） ，所以唯有 j == k 的情况（即 i 是某个整数的平方数）才会最后保持开灯，而因为本题只是求有多少个这种数字，所以高效的做法不应是每个数求平方根，而是从 1 开始遍历 i 且每个数求平方数直到该数大于 n，最后统计遍历到哪一个数即可。
            时间复杂度 O(sqrt(N))，空间复杂度 O(1)
        */
        int res = 0;
        for (int i=1; i<=n; i++) {
            if (i*i > n) break;
            else res++;
        }
        
        return res;
    }
}
