/*
Given the radius and the position of the center of a circle, implement the function randPoint which generates a uniform random point inside the circle.

Implement the Solution class:

Solution(double radius, double x_center, double y_center) initializes the object with the radius of the circle radius and the position of the center (x_center, y_center).
randPoint() returns a random point inside the circle. A point on the circumference of the circle is considered to be in the circle. The answer is returned as an array [x, y].
 

Example 1:

Input
["Solution", "randPoint", "randPoint", "randPoint"]
[[1.0, 0.0, 0.0], [], [], []]
Output
[null, [-0.02493, -0.38077], [0.82314, 0.38945], [0.36572, 0.17248]]

Explanation
Solution solution = new Solution(1.0, 0.0, 0.0);
solution.randPoint(); // return [-0.02493, -0.38077]
solution.randPoint(); // return [0.82314, 0.38945]
solution.randPoint(); // return [0.36572, 0.17248]
 

Constraints:

0 < radius <= 10^8
-10^7 <= x_center, y_center <= 10^7
At most 3 * 10^4 calls will be made to randPoint.
*/



// Other's Solution:
class Solution {
    double radius, x_center, y_center;

    public Solution(double radius, double x_center, double y_center) {
        // https://leetcode.com/problems/generate-random-point-in-a-circle/solutions/155650/explanation-with-graphs-why-using-math-sqrt/
        this.radius = radius;
        this.x_center = x_center;
        this.y_center = y_center;
    }
    
    public double[] randPoint() {
        double len = Math.sqrt(Math.random()) * radius;
        double degree = Math.random() * 2 * Math.PI;
        double x = x_center + len * Math.cos(degree);
        double y = y_center + len * Math.sin(degree);
        return new double[]{x, y};
    }
}
/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(radius, x_center, y_center);
 * double[] param_1 = obj.randPoint();
 */
