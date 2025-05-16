/*
Description:


Input: 

 
Output: 


*/

import java.util.*;

class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] matrix = new int[][]{
            {1, 2, 3}, 
            {4, 5, 6}, 
            {7, 8, 9}
        };
        int res = s.process(matrix);
        assert res == 0;
    }

    public int process(int[][] matrix) {
        for (int[] row : matrix) {
            for (int num : row) {
                System.out.print(num + ", ");
            }
            System.out.println("");
        }
        return 0;
    }
}