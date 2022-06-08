/**
On an n x n chessboard, a knight starts at the cell (row, column) and attempts to make exactly k moves. The rows and columns are 0-indexed, so the top-left cell is (0, 0), and the bottom-right cell is (n - 1, n - 1).

A chess knight has eight possible moves it can make, as illustrated below. Each move is two cells in a cardinal direction, then one cell in an orthogonal direction.
https://assets.leetcode.com/uploads/2018/10/12/knight.png


Each time the knight is to move, it chooses one of eight possible moves uniformly at random (even if the piece would go off the chessboard) and moves there.

The knight continues moving until it has made exactly k moves or has moved off the chessboard.

Return the probability that the knight remains on the board after it has stopped moving.

 

Example 1:

Input: n = 3, k = 2, row = 0, column = 0
Output: 0.06250
Explanation: There are two moves (to (1,2), (2,1)) that will keep the knight on the board.
From each of those positions, there are also two moves that will keep the knight on the board.
The total probability the knight stays on the board is 0.0625.
Example 2:

Input: n = 1, k = 0, row = 0, column = 0
Output: 1.00000
 

Constraints:

1 <= n <= 25
0 <= k <= 100
0 <= row, column <= n
 */



// My Solution:
import java.math.BigDecimal;

class Solution {
    int[][] nextSteps = new int[][]{{1, -2}, {-2, -1}, {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {-1, -2}};
    
    public double knightProbability(int n, int k, int row, int column) {
        // 分类：所有可能组合统计
        // 计算所有 k 步后的所有可能性总计，总步数是 8^k 为分母，分子为仍然留在棋盘上的所有点（重复点也计入）
        // 因为 k 最大为 100，暴力解 DFS 将爆栈，因此可以考虑 BFS 或者 DP，比如采用 BFS 加上剪枝加上双队列（因为有重复可以使用双哈希表，二维 pos 可以状态压缩 x << 5 & y）的方式，但因为 k 值稍微大一点数字就会非常大以至于超过普通的 Java 内置数据类型（甚至 Long）所以需要用 BigDecimal。
        Map<Integer, BigDecimal> m1 = new HashMap<>();
        Map<Integer, BigDecimal> m2 = new HashMap<>();
        int[] startPos = new int[]{row, column};
        m1.put(posToInt(startPos), new BigDecimal("1"));
        int tmpK = k;
        BigDecimal numerator = new BigDecimal("1");
        while (true) {
            if (--tmpK < 0) break;
            numerator = new BigDecimal("0");
            for (Map.Entry<Integer, BigDecimal> entry : m1.entrySet()) {
                int[] curPos = intToPos(entry.getKey());
                for (int i=0; i<8; i++) {
                    int[] nextPos = move(curPos, nextSteps[i]);
                    if (isValid(n, nextPos)) {
                        int nextPosInt = posToInt(nextPos);
                        m2.put(nextPosInt, m2.getOrDefault(nextPosInt, new BigDecimal("0")).add( entry.getValue()));
                        numerator = numerator.add(entry.getValue());
                    }
                }
            }
            m1.clear();
            
            if (--tmpK < 0) break;
            numerator = new BigDecimal("0");
            for (Map.Entry<Integer, BigDecimal> entry : m2.entrySet()) {
                int[] curPos = intToPos(entry.getKey());
                for (int i=0; i<8; i++) {
                    int[] nextPos = move(curPos, nextSteps[i]);
                    if (isValid(n, nextPos)) {
                        int nextPosInt = posToInt(nextPos);
                        m1.put(nextPosInt, m1.getOrDefault(nextPosInt, new BigDecimal("0")).add(entry.getValue()));
                        numerator = numerator.add(entry.getValue());
                    }
                }
            }
            m2.clear();
        }
        
        BigDecimal eight = new BigDecimal("8");
        BigDecimal denominator = eight.pow(k);
        BigDecimal res = numerator.divide(denominator);
        return res.doubleValue();
    }
    
    private int[] move(int[] curPos, int[] nextStep) {
        return new int[]{curPos[0] + nextStep[0], curPos[1] + nextStep[1]};
    }
    
    private boolean isValid(int n, int[] pos) {
        if (pos[0] < 0 || pos[1] < 0 || pos[0] >= n || pos[1] >= n) return false;
        return true;
    }
    
    private int posToInt(int[] pos) {
        return (pos[0] << 5) | pos[1];
    }
    
    private int[] intToPos(int i) {
        return new int[]{i >> 5, i & 31};
    }
}
