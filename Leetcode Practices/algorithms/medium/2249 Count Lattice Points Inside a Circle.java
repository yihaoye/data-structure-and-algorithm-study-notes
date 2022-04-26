/**
Given a 2D integer array circles where circles[i] = [xi, yi, ri] represents the center (xi, yi) and radius ri of the ith circle drawn on a grid, return the number of lattice points that are present inside at least one circle.

Note:

A lattice point is a point with integer coordinates.
Points that lie on the circumference of a circle are also considered to be inside it.
 

Example 1:
https://assets.leetcode.com/uploads/2022/03/02/exa-11.png

Input: circles = [[2,2,1]]
Output: 5
Explanation:
The figure above shows the given circle.
The lattice points present inside the circle are (1, 2), (2, 1), (2, 2), (2, 3), and (3, 2) and are shown in green.
Other points such as (1, 1) and (1, 3), which are shown in red, are not considered inside the circle.
Hence, the number of lattice points present inside at least one circle is 5.

Example 2:
https://assets.leetcode.com/uploads/2022/03/02/exa-22.png

Input: circles = [[2,2,2],[3,4,1]]
Output: 16
Explanation:
The figure above shows the given circles.
There are exactly 16 lattice points which are present inside at least one circle. 
Some of them are (0, 2), (2, 0), (2, 4), (3, 2), and (4, 4).
 

Constraints:

1 <= circles.length <= 200
circles[i].length == 3
1 <= xi, yi <= 100
1 <= ri <= min(xi, yi)
 */



// My Solution:
class Solution {
    int radiusPow = 0;
    
    public int countLatticePoints(int[][] circles) {
        Set<Integer> set = new HashSet<>();
        for (int[] circle : circles) {
            radiusPow = circle[2]*circle[2];
            recordValidPoints(circle, set);
        }
        
        return set.size();
    }
    
    public void recordValidPoints(int[] circle, Set<Integer> pointSet) {
        int xMin = circle[0] - circle[2], xMax = circle[0] + circle[2];
        int yMax = circle[1] + circle[2], yMin = circle[1] - circle[2];
        for (int i=xMin; i<=xMax; i++) {
            for (int j=yMin; j<=yMax; j++) {
                int point = i << 8 | j;
                if (pointSet.contains(point)) continue;
                if ((i-circle[0])*(i-circle[0]) + (j-circle[1])*(j-circle[1]) <= radiusPow) pointSet.add(point);
            }
        }
        return;
    }
}
