/*
Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.

For example, given the following triangle

[
     [2],
    [3,4],
   [6,5,7],
  [4,1,8,3]
]
The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).

Note:

Bonus point if you are able to do this using only O(n) extra space, where n is the total number of rows in the triangle.
*/



// Other's Solution (DP 动态规划): 
// https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-120-triangle/
class Solution {
    // [[2]                 [[2]
    //  [3, 4]]              [5, 6]
    //  [6, 5, 7]]           [11, 10, 11]
    //  [4, 1, 8, 3]]        [15, 11, 18, 14]]
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();

        for (int i=0; i < n; i++) {
            for (int j=0; j <= i; j++) {
                if (i == 0 && j == 0) continue;
                int temp = triangle.get(i).get(j);
                if (j == 0) triangle.get(i).set(j, temp + triangle.get(i-1).get(j));
                else if (j == i) triangle.get(i).set(j, temp + triangle.get(i-1).get(j-1));
                else triangle.get(i).set(j, temp + Math.min(triangle.get(i-1).get(j), triangle.get(i-1).get(j-1)));
            }
        }

        return Collections.min(triangle.get(n-1));
    }
}