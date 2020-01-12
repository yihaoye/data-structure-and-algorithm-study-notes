/*
Given a n x n matrix where each of the rows and columns are sorted in ascending order, find the kth smallest element in the matrix.

Note that it is the kth smallest element in the sorted order, not the kth distinct element.

Example:

matrix = [
   [ 1,  5,  9],
   [10, 11, 13],
   [12, 13, 15]
],
k = 8,

return 13.
Note:
You may assume k is always valid, 1 ≤ k ≤ n2.
*/



// Other's Solution (Binary Search):
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int len = matrix.length;
        int start = matrix[0][0], end = matrix[len-1][len-1];
        while (start < end) {
            int mid = start + (end - start) / 2;
            int total = 0;
            for (int[] row : matrix) {
                total += upperBound(row, mid);
            }
            if (total >= k) end = mid;
            else start = mid + 1;
        }
        
        return start;
    }

    public int upperBound(int[] row, int mid) {
        int res = 0;
        for (int e : row) {
            if (e <= mid) res++;
            else break;
        }
        return res;
    }
}
