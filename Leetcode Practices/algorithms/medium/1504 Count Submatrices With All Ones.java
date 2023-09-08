/*
Given an m x n binary matrix mat, return the number of submatrices that have all ones.

 

Example 1:
https://assets.leetcode.com/uploads/2021/10/27/ones1-grid.jpg

Input: mat = [[1,0,1],[1,1,0],[1,1,0]]
Output: 13
Explanation: 
There are 6 rectangles of side 1x1.
There are 2 rectangles of side 1x2.
There are 3 rectangles of side 2x1.
There is 1 rectangle of side 2x2. 
There is 1 rectangle of side 3x1.
Total number of rectangles = 6 + 2 + 3 + 1 + 1 = 13.

Example 2:
https://assets.leetcode.com/uploads/2021/10/27/ones2-grid.jpg

Input: mat = [[0,1,1,0],[0,1,1,1],[1,1,1,0]]
Output: 24
Explanation: 
There are 8 rectangles of side 1x1.
There are 5 rectangles of side 1x2.
There are 2 rectangles of side 1x3. 
There are 4 rectangles of side 2x1.
There are 2 rectangles of side 2x2. 
There are 2 rectangles of side 3x1. 
There is 1 rectangle of side 3x2. 
Total number of rectangles = 8 + 5 + 2 + 4 + 2 + 2 + 1 = 24.
 

Constraints:

1 <= m, n <= 150
mat[i][j] is either 0 or 1.
*/



// Other's Solution:
class Solution {
    public int numSubmat(int[][] mat) {
        /*
            前缀和 + 单调栈 - 
            https://leetcode-cn.com/problems/count-submatrices-with-all-ones/solution/tong-ji-quan-1-zi-ju-xing-by-leetcode-solution/
            类似：https://leetcode.com/problems/largest-rectangle-in-histogram/
            时间复杂度 O(N*M)，空间复杂度 O(N*M)
        */
        int n = mat.length; int m = mat[0].length;
        int[][] row = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (j == 0) row[i][j] = mat[i][j];
                else if (mat[i][j] != 0) row[i][j] = row[i][j - 1] + 1;
                else row[i][j] = 0;
            }
        }
        int ans = 0;
        for (int j = 0; j < m; ++j) { 
            Deque<int[]> monoStack = new LinkedList<int[]>(); // 创建一个单调栈，用于维护高度递增的条形矩形
            int sum = 0; // 记录以当前列为底边的所有矩形的总数
            for (int i = 0; i < n; ++i) {
                int height = 1; // 以 row[i][j] 为横轴宽度的满 1 矩形的高
                while (!monoStack.isEmpty() && monoStack.peekFirst()[0] > row[i][j]) {
                    sum -= monoStack.peekFirst()[1] * (monoStack.peekFirst()[0] - row[i][j]); // 弹出的元素代表的是矩形的数量，同时从总数中减去多余的部分
                    height += monoStack.peekFirst()[1]; // 更新以 row[i][j] 为横轴宽度的满 1 矩形的高（只能截止匹配到比自己更宽的连续上方的记录，因为如果比自己窄就不是满 1 矩形）
                    monoStack.pollFirst(); 
                } 
                sum += row[i][j]; // 类似前缀和 -- https://leetcode.cn/problems/count-submatrices-with-all-ones/solutions/316595/shi-yong-yu-yun-suan-fu-javashuang-bai-by-mp1256/
                ans += sum; 
                monoStack.offerFirst(new int[]{row[i][j], height}); 
            } 
        } 
        return ans;
    }
}
