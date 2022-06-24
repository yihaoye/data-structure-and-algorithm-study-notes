/**
There is a rectangular brick wall in front of you with n rows of bricks. The ith row has some number of bricks each of the same height (i.e., one unit) but they can be of different widths. The total width of each row is the same.

Draw a vertical line from the top to the bottom and cross the least bricks. If your line goes through the edge of a brick, then the brick is not considered as crossed. You cannot draw a line just along one of the two vertical edges of the wall, in which case the line will obviously cross no bricks.

Given the 2D array wall that contains the information about the wall, return the minimum number of crossed bricks after drawing such a vertical line.

 

Example 1:
https://assets.leetcode.com/uploads/2021/04/24/cutwall-grid.jpg

Input: wall = [[1,2,2,1],[3,1,2],[1,3,2],[2,4],[3,1,2],[1,3,1,1]]
Output: 2

Example 2:

Input: wall = [[1],[1],[1]]
Output: 3
 

Constraints:

n == wall.length
1 <= n <= 104
1 <= wall[i].length <= 104
1 <= sum(wall[i].length) <= 2 * 104
sum(wall[i]) is the same for each row i.
1 <= wall[i][j] <= 231 - 1
 */



// My Solution:
class Solution {
    public int leastBricks(List<List<Integer>> wall) {
        // 记录每个横轴的空隙 x 值（每个 List 的前缀和），0 < x < wall[0].length
        // 找出一个出现在各个 List 最多次数的 x 值即可，可以维护一个 HashMap<x, countOfListHasX>
        // 更新每个 countOfListHasX 可以维护一个 maxCount = Math.max(maxCount, countOfListHasX)
        // Time: O(N), Space: O(M), N 为 wall 的 Integer 元素数, M 为不同的 x 的个数
        Map<Integer, Integer> preSumCount = new HashMap<>();
        int max = 0;
        for (List<Integer> row : wall) {
            int rowPreSum = 0;
            for (int i=0; i<row.size()-1; i++) {
                int brickLen = row.get(i);
                rowPreSum += brickLen;
                int curPreSumCount = preSumCount.getOrDefault(rowPreSum, 0) + 1;
                preSumCount.put(rowPreSum, curPreSumCount);
                max = Math.max(max, curPreSumCount);
            }
        }
        return wall.size() - max;
    }
}
