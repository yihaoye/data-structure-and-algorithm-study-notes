/**
Write an efficient algorithm that searches for a value target in an m x n integer matrix matrix. This matrix has the following properties:

Integers in each row are sorted from left to right.
The first integer of each row is greater than the last integer of the previous row.
 

Example 1:
https://assets.leetcode.com/uploads/2020/10/05/mat.jpg

Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
Output: true

Example 2:
https://assets.leetcode.com/uploads/2020/10/05/mat2.jpg

Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
Output: false
 

Constraints:

m == matrix.length
n == matrix[i].length
1 <= m, n <= 100
-104 <= matrix[i][j], target <= 10^4
 */



// My Solution:
class Solution {
    public int n, m;
    public boolean searchMatrix(int[][] matrix, int target) {
        // 2D 二分搜索
        n = matrix[0].length; m = matrix.length;
        int row = lowerBound(matrix, target);
        if (row == -1) return false;
        int start = 0, end = n;
        while (start < end) {
            int mid = start + (end - start) / 2; // 直接平均可能會溢位，所以用此算法
            if (matrix[row][mid] > target) {
                end = mid;
            } else if (matrix[row][mid] < target) {
                start = mid + 1;
            } else {
                return true;
            }
        }
		return false;
    }
    
    public int lowerBound(int[][] matrix, int x) {
		int start = 0, end = m;
        while (start < end) {
            int mid = start + (end - start) / 2; // 直接平均可能會溢位，所以用此算法
            if (matrix[mid][n-1] >= x) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
		if (start == matrix.length) return -1;
        return start;
	}
}
