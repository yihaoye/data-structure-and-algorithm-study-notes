/**
You are given an integer n. You have an n x n binary grid grid with all values initially 1's except for some indices given in the array mines. The ith element of the array mines is defined as mines[i] = [xi, yi] where grid[xi][yi] == 0.

Return the order of the largest axis-aligned plus sign of 1's contained in grid. If there is none, return 0.

An axis-aligned plus sign of 1's of order k has some center grid[r][c] == 1 along with four arms of length k - 1 going up, down, left, and right, and made of 1's. Note that there could be 0's or 1's beyond the arms of the plus sign, only the relevant area of the plus sign is checked for 1's.

 

Example 1:
https://assets.leetcode.com/uploads/2021/06/13/plus1-grid.jpg

Input: n = 5, mines = [[4,2]]
Output: 2
Explanation: In the above grid, the largest plus sign can only be of order 2. One of them is shown.

Example 2:
https://assets.leetcode.com/uploads/2021/06/13/plus2-grid.jpg

Input: n = 1, mines = [[0,0]]
Output: 0
Explanation: There is no plus sign, so return 0.
 

Constraints:

1 <= n <= 500
1 <= mines.length <= 5000
0 <= xi, yi < n
All the pairs (xi, yi) are unique.
 */



// Other's Solution:
class Solution {
    public int orderOfLargestPlusSign(int n, int[][] mines) {
        /*
            DP - https://leetcode.cn/problems/largest-plus-sign/solution/zui-da-jia-hao-biao-zhi-by-leetcode/
            预先计算出每个中心的最长臂长 L_u, L_l, L_d, L_r，那么就能知道以这个为中心的加号的阶就是 min(L_u, L_l, L_d, L_r)。动态规划可以用来预先计算臂长。对于每个中心点坐标 (r, c)，从四个方向计算从 (r, c) 开始最长连续 1 的个数。用动态规划的方法来看，如果 grid[r][c] 是 0，那么臂长就是 0，如果 grid[r][c] 是 l, 那么臂长就是当前方向上连续 1 的个数再加 1。
            Time: O(N^2), Space: O(N^2)
        */
        Set<Integer> zerosPos = new HashSet();
        int[][] dp = new int[n][n];
        
        for (int[] mine: mines) zerosPos.add(mine[0]*n + mine[1]); // 二维数组的类状态压缩：r*n + c，实际是从左上到右下数第几个
        int res = 0, count;
        
        for (int r = 0; r < n; ++r) {
            // 统计每个点的正左方向有几个 1，如果当前不为 0 则为 ++count，否则为 0 且 count 重置为 0
            count = 0;
            for (int c = 0; c < n; ++c) {
                count = zerosPos.contains(r*n + c) ? 0 : count + 1;
                dp[r][c] = count;
            }
            
            // 统计每个点的正右方向有几个 1，count 操作与前面类似，但是不需要左右两边 1 的长度都保留，所以 dp[r][c] 只保留较短那边
            count = 0;
            for (int c = n-1; c >= 0; --c) {
                count = zerosPos.contains(r*n + c) ? 0 : count + 1;
                dp[r][c] = Math.min(dp[r][c], count);
            }
        }
        
        for (int c = 0; c < n; ++c) {
            count = 0;
            // 统计每个点的正上方向有几个 1，count 操作与前面类似，但是不需要左右上三边 1 的长度都保留，所以 dp[r][c] 只保留较短那边
            for (int r = 0; r < n; ++r) {
                count = zerosPos.contains(r*n + c) ? 0 : count + 1;
                dp[r][c] = Math.min(dp[r][c], count);
            }
            
            count = 0;
            // 统计每个点的正下方向有几个 1，count 操作与前面类似，但是不需要左右上下四边 1 的长度都保留，所以 dp[r][c] 只保留较短那边，因为是最后一次统计所以每次 dp[r][c] 计算完后可以和 res 对比、如果比 res 大则设为新的 res
            for (int r = n-1; r >= 0; --r) {
                count = zerosPos.contains(r*n + c) ? 0 : count + 1;
                dp[r][c] = Math.min(dp[r][c], count);
                res = Math.max(res, dp[r][c]);
            }
        }
        
        return res;
    }
}
