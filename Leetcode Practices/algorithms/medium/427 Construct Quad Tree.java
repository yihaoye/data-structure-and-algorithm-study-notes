/*
Given a n * n matrix grid of 0's and 1's only. We want to represent grid with a Quad-Tree.

Return the root of the Quad-Tree representing grid.

A Quad-Tree is a tree data structure in which each internal node has exactly four children. Besides, each node has two attributes:

val: True if the node represents a grid of 1's or False if the node represents a grid of 0's. Notice that you can assign the val to True or False when isLeaf is False, and both are accepted in the answer.
isLeaf: True if the node is a leaf node on the tree or False if the node has four children.
class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;
}
We can construct a Quad-Tree from a two-dimensional area using the following steps:

If the current grid has the same value (i.e all 1's or all 0's) set isLeaf True and set val to the value of the grid and set the four children to Null and stop.
If the current grid has different values, set isLeaf to False and set val to any value and divide the current grid into four sub-grids as shown in the photo.
Recurse for each of the children with the proper sub-grid.

https://assets.leetcode.com/uploads/2020/02/11/new_top.png
If you want to know more about the Quad-Tree, you can refer to the wiki.

Quad-Tree format:

You don't need to read this section for solving the problem. This is only if you want to understand the output format here. The output represents the serialized format of a Quad-Tree using level order traversal, where null signifies a path terminator where no node exists below.

It is very similar to the serialization of the binary tree. The only difference is that the node is represented as a list [isLeaf, val].

If the value of isLeaf or val is True we represent it as 1 in the list [isLeaf, val] and if the value of isLeaf or val is False we represent it as 0.

 

Example 1:
https://assets.leetcode.com/uploads/2020/02/11/grid1.png

Input: grid = [[0,1],[1,0]]
Output: [[0,1],[1,0],[1,1],[1,1],[1,0]]
Explanation: The explanation of this example is shown below:
Notice that 0 represnts False and 1 represents True in the photo representing the Quad-Tree.
https://assets.leetcode.com/uploads/2020/02/12/e1tree.png


Example 2:
https://assets.leetcode.com/uploads/2020/02/12/e2mat.png

Input: grid = [[1,1,1,1,0,0,0,0],[1,1,1,1,0,0,0,0],[1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1],[1,1,1,1,0,0,0,0],[1,1,1,1,0,0,0,0],[1,1,1,1,0,0,0,0],[1,1,1,1,0,0,0,0]]
Output: [[0,1],[1,1],[0,1],[1,1],[1,0],null,null,null,null,[1,0],[1,0],[1,1],[1,1]]
Explanation: All values in the grid are not the same. We divide the grid into four sub-grids.
The topLeft, bottomLeft and bottomRight each has the same value.
The topRight have different values so we divide it into 4 sub-grids where each has the same value.
Explanation is shown in the photo below:
https://assets.leetcode.com/uploads/2020/02/12/e2tree.png
 

Constraints:

n == grid.length == grid[i].length
n == 2^x where 0 <= x <= 6
*/



// My Solution:
class Solution {
    public Node construct(int[][] grid) {
        // 二维前缀和 + DFS - inspired by https://leetcode.cn/problems/construct-quad-tree/solution/python-by-himymben-ld18/
        // Time: O(N) -> N = n^2
        int n = grid.length;
        int[][] dp = new int[n][n];
        for (int r=0; r<n; r++) {
            for (int c=0; c<n; c++) {
                dp[r][c] = grid[r][c] + (r > 0 ? dp[r-1][c] : 0) + (c > 0 ? dp[r][c-1] : 0) - (r > 0 && c > 0 ? dp[r-1][c-1] : 0);
            }
        }

        return dfs(dp, -1, -1, n-1, n-1);
    }

    public Node dfs(int[][] dp, int rMin, int cMin, int rMax, int cMax) { // [rMin, cMin] -> range top left (not included), [rMax, cMax] -> range bottom right (included)
        int rangeSum = dp[rMax][cMax] - (rMin >= 0 ? dp[rMin][cMax] : 0) - (cMin >= 0 ? dp[rMax][cMin] : 0) + (rMin >= 0 && cMin >= 0 ? dp[rMin][cMin] : 0);
        
        if (rangeSum == (cMax - cMin) * (rMax - rMin)) {
            return new Node(true, true);
        } else if (rangeSum == 0) {
            return new Node(false, true);
        } else {
            int rMid = (rMax + rMin) / 2;
            int cMid = (cMax + cMin) / 2;
            Node topLeft = dfs(dp, rMin, cMin, rMid, cMid);
            Node topRight = dfs(dp, rMin, cMid, rMid, cMax);
            Node bottomLeft = dfs(dp, rMid, cMin, rMax, cMid);
            Node bottomRight = dfs(dp, rMid, cMid, rMax, cMax);
            return new Node(false, false, topLeft, topRight, bottomLeft, bottomRight);
        }
    }
}
/*
// Definition for a QuadTree node.
class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;

    
    public Node() {
        this.val = false;
        this.isLeaf = false;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }
    
    public Node(boolean val, boolean isLeaf) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }
    
    public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }
};
*/
