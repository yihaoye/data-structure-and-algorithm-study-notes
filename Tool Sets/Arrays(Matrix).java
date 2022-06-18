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

// ToDo: 二维迷宫探索（上下左右并有界线检查）


// ToDo: 二维迷宫探索（上下左右并有界线以及障碍检查）

