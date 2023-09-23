/*
There are n friends that are playing a game. The friends are sitting in a circle and are numbered from 1 to n in clockwise order. More formally, moving clockwise from the ith friend brings you to the (i+1)th friend for 1 <= i < n, and moving clockwise from the nth friend brings you to the 1st friend.

The rules of the game are as follows:

Start at the 1st friend.
Count the next k friends in the clockwise direction including the friend you started at. The counting wraps around the circle and may count some friends more than once.
The last friend you counted leaves the circle and loses the game.
If there is still more than one friend in the circle, go back to step 2 starting from the friend immediately clockwise of the friend who just lost and repeat.
Else, the last friend in the circle wins the game.
Given the number of friends, n, and an integer k, return the winner of the game.

 

Example 1:


Input: n = 5, k = 2
Output: 3
Explanation: Here are the steps of the game:
1) Start at friend 1.
2) Count 2 friends clockwise, which are friends 1 and 2.
3) Friend 2 leaves the circle. Next start is friend 3.
4) Count 2 friends clockwise, which are friends 3 and 4.
5) Friend 4 leaves the circle. Next start is friend 5.
6) Count 2 friends clockwise, which are friends 5 and 1.
7) Friend 1 leaves the circle. Next start is friend 3.
8) Count 2 friends clockwise, which are friends 3 and 5.
9) Friend 5 leaves the circle. Only friend 3 is left, so they are the winner.
Example 2:

Input: n = 6, k = 5
Output: 1
Explanation: The friends leave in this order: 5, 4, 6, 2, 3. The winner is friend 1.
 

Constraints:

1 <= k <= n <= 500
 

Follow up:

Could you solve this problem in linear time with constant space?
*/



// My Solution:
class Solution {
    public int findTheWinner(int n, int k) {
        // 约瑟夫环 - 数学归纳法
        // f(1, K) = 1
        // f(N, K) = (f(N-1, K) + K) % N
        // 迭代、DP 而不是递归，可使 Time: O(N), Space: O(1)
        int dp = 1; // f(1, K) = 1（编号从 1 开始所以 f(1, K) = 1，若从 0 开始则为 f(1, K) = 0）
        for (int i=1; i<=n; i++) {
            dp = (dp + k) % i;
        }

        return dp + 1; // 题目要求返回的是人的编号，编号从 1 开始，而不是从 0 开始，所以要加上 1
    }
}



// Other Solution:
class Solution {
    public int findTheWinner(int n, int k) {
        // 递归写法 - https://mp.weixin.qq.com/s/-FW_4GC_g72sQ-AcTm2tVA
        // 每次往同一方向，以固定步长 k 进行消数。由于下一次操作的发起点为消除位置的下一个点（即前后两次操作发起点在原序列下标中相差 k），
        // 同时问题规模会从 n 变为 n-1，因此原问题答案等价于 findTheWinner(n - 1, k) + k。
        if (n <= 1) return n;
        int ans = (findTheWinner(n - 1, k) + k) % n;
        return ans == 0 ? n : ans;
    }
}
