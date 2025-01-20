public class HollowTriangle {

    public static void print(int n) {
        for (int i = 0; i < n; i++) {
            // 打印前置空格，打印 n 个是因为三角形为 n 行
            // 原理是中间空心空格为 1, 3, 5, 7 ...，5 行的三角形第 4 行为 5 个空心，最后一行填满（但实际原先是 7 个空心，也即总长应 +2 为 9）
            // 找规律可知底部长度总是 2n-1，所以第一行总是应该左边打印 n-1（n-1+n-1+1 == 2n-1）
            // 而假设行为 i，除 i == 0 外，每一行的空心总是等于 2i-1（上一行长度 +2）
            for (int j = 0; j < n - i - 1; j++) {
                System.out.print(" ");
            }

            if (i == 0) {
                // 第一行只有一个 *
                System.out.println("*");
            } else if (i == n - 1) {
                // 最后一行全部填满 *
                for (int j = 0; j < 2 * n - 1; j++) System.out.print("*");
                System.out.println();
            } else {
                // 中间行
                System.out.print("*"); // 左边的 *
                for (int j = 0; j < 2 * i - 1; j++) System.out.print(" ");
                System.out.println("*"); // 右边的 *
            }
        }
    }

    public static void main(String[] args) {
        int n = 5; // 三角形的行数
        print(n);
    }
}

/*
    *
   * *
  *   *
 *     *
*********
*/