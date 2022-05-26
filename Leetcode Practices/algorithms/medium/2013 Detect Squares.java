/**
You are given a stream of points on the X-Y plane. Design an algorithm that:

Adds new points from the stream into a data structure. Duplicate points are allowed and should be treated as different points.
Given a query point, counts the number of ways to choose three points from the data structure such that the three points and the query point form an axis-aligned square with positive area.
An axis-aligned square is a square whose edges are all the same length and are either parallel or perpendicular to the x-axis and y-axis.

Implement the DetectSquares class:

DetectSquares() Initializes the object with an empty data structure.
void add(int[] point) Adds a new point point = [x, y] to the data structure.
int count(int[] point) Counts the number of ways to form axis-aligned squares with point point = [x, y] as described above.
 

Example 1:
https://assets.leetcode.com/uploads/2021/09/01/image.png

Input
["DetectSquares", "add", "add", "add", "count", "count", "add", "count"]
[[], [[3, 10]], [[11, 2]], [[3, 2]], [[11, 10]], [[14, 8]], [[11, 2]], [[11, 10]]]
Output
[null, null, null, null, 1, 0, null, 2]

Explanation
DetectSquares detectSquares = new DetectSquares();
detectSquares.add([3, 10]);
detectSquares.add([11, 2]);
detectSquares.add([3, 2]);
detectSquares.count([11, 10]); // return 1. You can choose:
                               //   - The first, second, and third points
detectSquares.count([14, 8]);  // return 0. The query point cannot form a square with any points in the data structure.
detectSquares.add([11, 2]);    // Adding duplicate points is allowed.
detectSquares.count([11, 10]); // return 2. You can choose:
                               //   - The first, second, and third points
                               //   - The first, third, and fourth points
 

Constraints:

point.length == 2
0 <= x, y <= 1000
At most 3000 calls in total will be made to add and count.
 */



// Other's Solution:
class DetectSquares {
    Map<Integer, Map<Integer, Integer>> x2y;

    public DetectSquares() {
        // 数学（嵌套哈希表）- https://leetcode.cn/problems/detect-squares/solution/gong-shui-san-xie-jian-dan-ha-xi-biao-yu-748e/
        // Time: add O(1) count O(N), Space: O(N)
        x2y = new HashMap<>();
    }
    
    public void add(int[] point) {
        int x = point[0], y = point[1];
        Map<Integer, Integer> y2Cnt = x2y.getOrDefault(x, new HashMap<>());
        y2Cnt.put(y, y2Cnt.getOrDefault(y, 0) + 1);
        x2y.put(x, y2Cnt);
    }
    
    public int count(int[] point) {
        int x = point[0], y = point[1];
        int res = 0;
        Map<Integer, Integer> y2Cnt = x2y.getOrDefault(x, new HashMap<>());
        for (int nextY : y2Cnt.keySet()) {
            if (nextY == y) continue;
            int c1 = y2Cnt.get(nextY);
            int len = y - nextY;
            int[] nums = new int[]{x + len, x - len};
            for (int nextX : nums) {
                Map<Integer, Integer> temp = x2y.getOrDefault(nextX, new HashMap<>());
                int c2 = temp.getOrDefault(y, 0), c3 = temp.getOrDefault(nextY, 0);
                res += c1 * c2 * c3;
            }
        }
        return res;
    }
}
/**
 * Your DetectSquares object will be instantiated and called as such:
 * DetectSquares obj = new DetectSquares();
 * obj.add(point);
 * int param_2 = obj.count(point);
 */
