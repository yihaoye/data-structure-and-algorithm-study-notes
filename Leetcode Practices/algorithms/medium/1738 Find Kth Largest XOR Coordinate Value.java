/**
You are given a 2D matrix of size m x n, consisting of non-negative integers. You are also given an integer k.

The value of coordinate (a, b) of the matrix is the XOR of all matrix[i][j] where 0 <= i <= a < m and 0 <= j <= b < n (0-indexed).

Find the kth largest value (1-indexed) of all the coordinates of matrix.

 

Example 1:

Input: matrix = [[5,2],[1,6]], k = 1
Output: 7
Explanation: The value of coordinate (0,1) is 5 XOR 2 = 7, which is the largest value.
Example 2:

Input: matrix = [[5,2],[1,6]], k = 2
Output: 5
Explanation: The value of coordinate (0,0) is 5 = 5, which is the 2nd largest value.
Example 3:

Input: matrix = [[5,2],[1,6]], k = 3
Output: 4
Explanation: The value of coordinate (1,0) is 5 XOR 1 = 4, which is the 3rd largest value.
 

Constraints:

m == matrix.length
n == matrix[i].length
1 <= m, n <= 1000
0 <= matrix[i][j] <= 10^6
1 <= k <= m * n
 */



// Other's Solution:
class Solution {
    public int kthLargestValue(int[][] matrix, int k) {
        // DP - https://leetcode.com/problems/find-kth-largest-xor-coordinate-value/solutions/1032143/java-detailed-explanation-dp-with-graph-demo/?orderBy=hot
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                int x = i > 0 ? dp[i - 1][j] : 0;
                int y = j > 0 ? dp[i][j - 1] : 0;
                int z = j > 0 && i > 0 ? dp[i - 1][j - 1] : 0;
                dp[i][j] = x ^ y ^ z ^ matrix[i][j];
                pq.offer(dp[i][j]);
                if (pq.size() > k) pq.poll();
            }
        }
        return pq.poll();
    }
}
