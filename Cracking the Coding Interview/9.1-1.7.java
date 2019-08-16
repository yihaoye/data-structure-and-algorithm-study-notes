/* Question:
编写一个算法，若 M*N 矩阵中某个元素为 0，则将其所在的行与列清零。
*/



// 此题目隐藏陷阱，如果每次遇到元素 0 就即可清零行与列，则可能带入新的 0，因此应采用标记方式
// 标记时不需要把每个元素的二维值都各自记录下来 - 否则占用空间将是 O(M*N)，只需记录哪个行或列有 0 值即可，因为可能同一个行或列有多个 0，因此节省了空间 - 占用 O(M+N)
public class Solution {
    public void setZero(int[][] matrix) {
        boolean[] row = new boolean[matrix.length];
        boolean[] column = new boolean[matrix[0].length];

        // 记录值为 0 的元素所在行索引和列索引
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = true;
                    column[j] = true;
                }
            }
        }

        // 若行 i 或列 j 有个元素为 0，则将 arr[i][j] 置为 0
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (row[i] || column[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
}
// 可以选用位向量替代布尔数组从而提高空间利用率