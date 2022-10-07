/**
You are given a circle represented as (radius, xCenter, yCenter) and an axis-aligned rectangle represented as (x1, y1, x2, y2), where (x1, y1) are the coordinates of the bottom-left corner, and (x2, y2) are the coordinates of the top-right corner of the rectangle.

Return true if the circle and rectangle are overlapped otherwise return false. In other words, check if there is any point (xi, yi) that belongs to the circle and the rectangle at the same time.

 

Example 1:
https://assets.leetcode.com/uploads/2020/02/20/sample_4_1728.png

Input: radius = 1, xCenter = 0, yCenter = 0, x1 = 1, y1 = -1, x2 = 3, y2 = 1
Output: true
Explanation: Circle and rectangle share the point (1,0).

Example 2:

Input: radius = 1, xCenter = 1, yCenter = 1, x1 = 1, y1 = -3, x2 = 2, y2 = -1
Output: false

Example 3:
https://assets.leetcode.com/uploads/2020/02/20/sample_2_1728.png

Input: radius = 1, xCenter = 0, yCenter = 0, x1 = -1, y1 = 0, x2 = 0, y2 = 1
Output: true
 

Constraints:

1 <= radius <= 2000
-10^4 <= xCenter, yCenter <= 10^4
-10^4 <= x1 < x2 <= 10^4
-10^4 <= y1 < y2 <= 10^4
 */



// Other's Solution:
class Solution {
    public boolean checkOverlap(int radius, int xCenter, int yCenter, int x1, int y1, int x2, int y2) {
        // Math - https://leetcode.com/problems/circle-and-rectangle-overlapping/discuss/563463/C%2B%2B-with-simple-explanation
        // Move the center of the circle to the coordinate origin (0, 0), then this problem becomes "is there a point (x, y) (x1 <= x <= x2, y1 <= y <= y2) satisfying x^2 + y^2 <= r^2".
        // So just compute minimum values of x^2 and y^2, then compare the sum with r^2.
        // Time: O(1)
        x1 -= xCenter; x2 -= xCenter; y1 -= yCenter; y2 -= yCenter;
        
        int minX = (x1>0 && x2>0) || (x1<0 && x2<0) ? Math.min(x1*x1, x2*x2) : 0;
        int minY = (y1>0 && y2>0) || (y1<0 && y2<0) ? Math.min(y1*y1, y2*y2) : 0;
        return minY + minX <= radius * radius;
    }
}
