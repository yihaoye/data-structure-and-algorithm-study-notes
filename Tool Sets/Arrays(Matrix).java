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

// 计算哈希（不受顺序影响，集合一样即可）
String[] arr1 = new String[]{"a", "b", "c"};
String[] arr2 = new String[]{"c", "b", "a"};
int hash1 = Arrays.hashCode(arr1);
int hash2 = Arrays.hashCode(arr2);
System.out.println(hash1 == hash2); // true
// 多维数组计算哈希
String[][] arr1 = new String[][]{{"a", "b", "c"}, {"d", "e", "f"}};
String[][] arr2 = new String[][]{{"d", "e", "f"}, {"a", "b", "c"}};
int hash1 = Arrays.deepHashCode(arr1);
int hash2 = Arrays.deepHashCode(arr2);
System.out.println(hash1 == hash2); // true

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

