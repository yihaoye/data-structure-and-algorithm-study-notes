/*
You are given an array points where points[i] = [xi, yi] is the coordinates of the ith point on a 2D plane. Multiple points can have the same coordinates.

You are also given an array queries where queries[j] = [xj, yj, rj] describes a circle centered at (xj, yj) with a radius of rj.

For each query queries[j], compute the number of points inside the jth circle. Points on the border of the circle are considered inside.

Return an array answer, where answer[j] is the answer to the jth query.

 

Example 1:
https://assets.leetcode.com/uploads/2021/03/25/chrome_2021-03-25_22-34-16.png

Input: points = [[1,3],[3,3],[5,3],[2,2]], queries = [[2,3,1],[4,3,1],[1,1,2]]
Output: [3,2,2]
Explanation: The points and circles are shown above.
queries[0] is the green circle, queries[1] is the red circle, and queries[2] is the blue circle.

Example 2:
https://assets.leetcode.com/uploads/2021/03/25/chrome_2021-03-25_22-42-07.png

Input: points = [[1,1],[2,2],[3,3],[4,4],[5,5]], queries = [[1,2,2],[2,2,2],[4,3,2],[4,3,3]]
Output: [2,3,2,4]
Explanation: The points and circles are shown above.
queries[0] is green, queries[1] is red, queries[2] is blue, and queries[3] is purple.
 

Constraints:

1 <= points.length <= 500
points[i].length == 2
0 <= x​​​​​​i, y​​​​​​i <= 500
1 <= queries.length <= 500
queries[j].length == 3
0 <= xj, yj <= 500
1 <= rj <= 500
All coordinates are integers.
 

Follow up: Could you find the answer for each query in better complexity than O(n)?
*/



// My Solution:
class Solution {
    public int[] countPoints(int[][] points, int[][] queries) {
        KDTree kdTree = new KDTree();
        kdTree.buildTree(points);
        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int x_center = queries[i][0], y_center = queries[i][1], radius = queries[i][2];
            // 范围查询
            List<KDTree.Node> rangePoints = new ArrayList<>();
            kdTree.rangeSearch(kdTree.getRoot(), new int[]{x_center - radius, y_center - radius}, new int[]{x_center + radius, y_center + radius}, 0, rangePoints);
            int count = 0;
            for (KDTree.Node node : rangePoints) {
                int x = node.point[0], y = node.point[1];
                // 计算点到圆心的距离
                if (Math.pow(x - x_center, 2) + Math.pow(y - y_center, 2) <= radius * radius) count++;
            }
            result[i] = count;
        }
        return result;
    }
}

class KDTree {
    class Node {
        int[] point; Node left, right; int count; // count == 0 means no longer exist

        Node(int[] point) {
            this.point = point;
        }
    }

    private Node root;
    private Map<int[], Node> nodes = new HashMap<>(); // point -> node
    private int k;

    public KDTree() {
        this.root = null;
    }

    public void buildTree(int[][] points) {
        this.k = points[0].length;
        for (int[] point : points) {
            nodes.computeIfAbsent(point, v -> new Node(point)).count++;
        }
        int[][] uniqPoints = new int[nodes.size()][k];
        int i = 0;
        for (int[] point : nodes.keySet()) {
            uniqPoints[i++] = point;
        }
        root = buildTree(uniqPoints, 0, uniqPoints.length - 1, 0);
    }

    private Node buildTree(int[][] points, int start, int end, int depth) {
        if (start > end) return null;

        int axis = depth % k; // axis（轴）用于决定节点在哪个维度上进行划分。KD 树是一种多维空间的数据结构，每个节点根据当前的深度（depth）选择一个维度（axis），并按照该维度上的值对数据进行划分。
        Arrays.sort(points, start, end + 1, Comparator.comparingInt(a -> a[axis]));
        
        int mid = start + (end - start) / 2;
        int[] point = points[mid];
        Node node = nodes.get(point);
        node.left = buildTree(points, start, mid - 1, depth + 1);
        node.right = buildTree(points, mid + 1, end, depth + 1);
        return node;
    }

    public Node getRoot() {
        return root;
    }

    public void rangeSearch(Node node, int[] minBound, int[] maxBound, int depth, List<Node> list) {
        if (node == null) return;
        if (node.count > 0 && isWithinBounds(node.point, minBound, maxBound)) {
            list.add(node);
        }
        int axis = depth % k;
        if (minBound[axis] <= node.point[axis]) rangeSearch(node.left, minBound, maxBound, depth + 1, list);
        if (maxBound[axis] >= node.point[axis]) rangeSearch(node.right, minBound, maxBound, depth + 1, list);
    }

    private boolean isWithinBounds(int[] point, int[] minBound, int[] maxBound) {
        for (int i = 0; i < k; i++) {
            if (point[i] < minBound[i] || point[i] > maxBound[i]) return false;
        }
        return true;
    }

    public boolean delete(int[] point) {
        if (!nodes.containsKey(point)) return false;
        Node node = nodes.get(point);
        node.count--;
        return true;
    }
}
