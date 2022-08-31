// 数组全填充
int[] arr = new int[10000];
Arrays.fill(arr, -1);

// 数组复制
int[] arrCopy1 = arr.clone();
// or
int[] arrCopy2 = Arrays.copyOf(arr, arr.length);
// 子数组复制
int[] subArr = Arrays.copyOfRange(arr, i, j);

// List 转换成数组
List<Object> list = new ArrayList<>();
Object[] lArr = new Object[list.size()];
list.toArray(lArr);

// 多维数组排序
int[][] arrs = new int[100][100];
Arrays.sort(arrs, (a, b) -> (a[0] == b[0]) ? (a[1] - b[1]) : (a[0] - b[0])); // 均为升序排序

// 多维数组比较是否相等
String[][] arr1 = new String[][]{{"a", "a"}};
String[][] arr2 = new String[][]{{"a", "a"}};
Arrays.equals(arr1, arr2); // Return false - comparing two dimensional arrays, which means the elements of these arrays are themselves arrays. Therefore, when the elements are compared (using Object's equals), false is returned, since Object's equals compares Object references.
Arrays.deepEquals(arr1, arr2); // Returns true if the two specified arrays are deeply equal to one another. Unlike the equals(Object [], Object []) method, this method is appropriate for use with nested arrays of arbitrary depth.

// 二维迷宫探索（上下左右并有界线检查）
class Solution {
    private int N, M;
    private int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private boolean[][] visited;

    public void main(int[][] matrix) {
        this.M = matrix.length;
        this.N = matrix[0].length;
        this.visited = new int[M][N];
    }

    private void dfs(int[][] matrix, int x, int y) {
        if (visited[y][x]) return;
        visited[y][x] = true;

        // process...

        for (int[] direction : this.directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];
            if (isValid(newX, newY, this.N, this.M)) {
                dfs(matrix, newX, newY);
            }
        }
    }

    private boolean isValid(int x, int y, int maxX, int maxY) {
        return x >= 0 && x < maxX && y >= 0 && y < maxY;
    }
}

// ToDo: 二维迷宫探索（上下左右并有界线以及障碍检查）

