/**
You are given an array coordinates, coordinates[i] = [x, y], where [x, y] represents the coordinate of a point. Check if these points make a straight line in the XY plane.

 

 

Example 1:
https://assets.leetcode.com/uploads/2019/10/15/untitled-diagram-2.jpg

Input: coordinates = [[1,2],[2,3],[3,4],[4,5],[5,6],[6,7]]
Output: true

Example 2:
https://assets.leetcode.com/uploads/2019/10/09/untitled-diagram-1.jpg

Input: coordinates = [[1,1],[2,2],[3,4],[4,5],[5,6],[7,7]]
Output: false
 

Constraints:

2 <= coordinates.length <= 1000
coordinates[i].length == 2
-10^4 <= coordinates[i][0], coordinates[i][1] <= 10^4
coordinates contains no duplicate point.
 */



// My Solution:
class Solution {
    public boolean checkStraightLine(int[][] coordinates) {
        // slope [numerator, denominator] + gcd
        int[] slope = cal(coordinates[0], coordinates[1]);
        for (int i=1; i<coordinates.length-1; i++) {
            int[] curSlope = cal(coordinates[i], coordinates[i+1]);
            if (slope[0] == curSlope[0] && slope[1] == curSlope[1]) continue;
            else return false;
        }
        return true;
    }
    
    public int[] cal(int[] pointA, int[] pointB) {
        int[] slope = new int[]{pointA[1]-pointB[1], pointA[0]-pointB[0]};
        int gcd = gcd(slope[0], slope[1]);
        slope[0] /= gcd;
        slope[1] /= gcd;
        return slope;
    }
    
    public int gcd(int a, int b) {
        if (a == 0) return b; // 若 a 或 b 的任意一个为 0，则最大公约数就是另一个数
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
