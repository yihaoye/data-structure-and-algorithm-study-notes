/* Question:
给定一幅由 N*N 矩阵表示的图像，其中每个像素的大小为 4 字节，编写一个方法，将图像旋转 90 度。不占用额外内存空间能否做到？
*/



// 对每一层执行环状旋转，按索引一个一个进行交换
public class Solution {
    public void rotate(int[][] matrix, int n) {
        for (int layer = 0; layer < n/2; ++layer) {
            int first = layer;
            int last = n - 1 - layer;
            for (int i = first; i < last; ++i) {
                int offset = i - first;
                // 存储上边
                int top = matrix[first][i];

                // 左到上
                matrix[first][i] = matrix[last-offset][first];

                // 下到左
                matrix[last-offset][first] = matrix[last][last-offset];

                // 右到下
                matrix[last][last-offset] = matrix[i][last];

                // 上到右
                matrix[i][last] = top;
            }
        }
    }
}
// 此算法时间复杂度为 O(N^2)，已是最优解，因为任何算法都要访问 N*N 个元素