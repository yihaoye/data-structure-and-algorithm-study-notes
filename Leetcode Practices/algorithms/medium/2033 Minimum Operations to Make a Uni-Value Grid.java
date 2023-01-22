/**
You are given a 2D integer grid of size m x n and an integer x. In one operation, you can add x to or subtract x from any element in the grid.

A uni-value grid is a grid where all the elements of it are equal.

Return the minimum number of operations to make the grid uni-value. If it is not possible, return -1.

 

Example 1:
https://assets.leetcode.com/uploads/2021/09/21/gridtxt.png

Input: grid = [[2,4],[6,8]], x = 2
Output: 4
Explanation: We can make every element equal to 4 by doing the following: 
- Add x to 2 once.
- Subtract x from 6 once.
- Subtract x from 8 twice.
A total of 4 operations were used.

Example 2:
https://assets.leetcode.com/uploads/2021/09/21/gridtxt-1.png

Input: grid = [[1,5],[2,3]], x = 1
Output: 5
Explanation: We can make every element equal to 3.

Example 3:
https://assets.leetcode.com/uploads/2021/09/21/gridtxt-2.png

Input: grid = [[1,2],[3,4]], x = 2
Output: -1
Explanation: It is impossible to make every element equal.
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 10^5
1 <= m * n <= 10^5
1 <= x, grid[i][j] <= 10^4
 */



// Other's Solution:
class Solution {
    public int minOperations(int[][] grid, int x) {
        // 数学(中位数) - https://leetcode.cn/problems/minimum-operations-to-make-a-uni-value-grid/solution/zhong-wei-shu-by-endlesscheng-p0vj/
        /*
            假设要让所有元素均为 yy，设小于 yy 的元素有 pp 个，大于 yy 的元素有 qq 个，可以发现：
                * 若 p<qp<q，yy 每增加 xx，操作数就可以减小 q-pq−p；
                * 若 p>qp>q，yy 每减小 xx，操作数就可以减小 p-qp−q；
            因此 p=qp=q 时可以让总操作数最小，此时 yy 为所有元素的中位数。
        */
        int n = grid.length, m = grid[0].length, i = 0;
        int[] arr = new int[m*n];
        for (int[] a : grid)
            for (int a_ : a) arr[i++] = a_;

        Arrays.sort(arr);
        int j = arr[(n*m)/2];
        int sum = 0;
        for (int a : arr) {
            int l = Math.abs(j-a);
            if(l%x != 0) return -1;
            sum += l/x;
        }
        return sum;
    }
}
