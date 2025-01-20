public class HollowTriangle {

    public static void print(int n) {
        for (int i = 1; i <= n; i++) {
            // 打印前置空格
            for (int j = 1; j <= n - i; j++) {
                System.out.print(" ");
            }

            if (i == 1) {
                // 第一行只有一个 *
                System.out.println("*");
            } else if (i == n) {
                // 最后一行全部填满 *
                for (int j = 1; j <= 2 * n - 1; j++) {
                    System.out.print("*");
                }
                System.out.println();
            } else {
                // 中间行
                System.out.print("*"); // 左边的 *
                for (int j = 1; j <= 2 * i - 3; j++) {
                    System.out.print(" ");
                }
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