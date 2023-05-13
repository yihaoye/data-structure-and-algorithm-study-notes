/*
Given n points on a 2D plane, find if there is such a line parallel to the y-axis that reflects the given points symmetrically.

In other words, answer whether or not if there exists a line that after reflecting all points over the given line, the original points' set is the same as the reflected ones.

Note that there can be repeated points.

 

Example 1:
https://assets.leetcode.com/uploads/2020/04/23/356_example_1.PNG

Input: points = [[1,1],[-1,1]]
Output: true
Explanation: We can choose the line x = 0.

Example 2:
https://assets.leetcode.com/uploads/2020/04/23/356_example_2.PNG

Input: points = [[1,1],[-1,-1]]
Output: false
Explanation: We can't choose a line.
 

Constraints:

n == points.length
1 <= n <= 10^4
-10^8 <= points[i][j] <= 10^8
 

Follow up: Could you do better than O(n^2)?
*/



// My Solution:
class Solution {
    public boolean isReflected(int[][] points) {
        // HashMap + 数学/几何
        // 找到最小的 x 的点和最大的 x 的点（如果有多个，找在同一 y 轴上的可以两个同是 y 轴最小，然后可以借此确定所求 y 轴）
        // 然后对剩下的点借着求得的 y 轴一一消解，若找不到对应点则返回 false
        // Time: O(N)
        Map<Integer, Set<Integer>> records = new HashMap<>(); // <x, <y1, y2, ...>>
        int[] xMaxPoint = new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE}, xMinPoint = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        for (int[] point : points) {
            int x = point[0]; int y = point[1];
            records.computeIfAbsent(x, v -> new HashSet<>()).add(y);

            if (x > xMaxPoint[0] || (x == xMaxPoint[0] && y < xMaxPoint[1])) xMaxPoint = new int[]{x, y};
            if (x < xMinPoint[0] || (x == xMinPoint[0] && y < xMinPoint[1])) xMinPoint = new int[]{x, y};
        }
        if (xMaxPoint[1] != xMinPoint[1]) return false;
        int yAxisMulTwo = xMaxPoint[0] + xMinPoint[0]; // yAxis = (xMaxPoint[0] + xMinPoint[0]) / 2.0

        for (int[] point : points) {
            int x = point[0]; int y = point[1];
            if (!records.containsKey(x) || !records.get(x).contains(y)) continue; // previously processed
            records.get(x).remove(y);
            if (records.get(x).isEmpty()) records.remove(x);

            int oppositeX = yAxisMulTwo - x;
            if (records.getOrDefault(oppositeX, new HashSet<>()).contains(y)) {
                records.get(oppositeX).remove(y);
                if (records.get(oppositeX).isEmpty()) records.remove(oppositeX);
            } else if (x != oppositeX) {
                return false;
            }
        }
        return true;
    }
}
