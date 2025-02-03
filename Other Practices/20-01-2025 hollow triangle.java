public class HollowTriangle {
    // My Solution - two pointer
    public static void print(int n) {
        // 找规律可知底部长度总是 2n-1，所以第一行总是应该左边打印 n-1（n-1+n-1+1 == 2n-1）
        int cLen = 2*n - 1;
        int l = n - 1, r = n - 1;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < cLen; col++) {
                if (col == l || col == r || row == n - 1) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
            l--; 
            r++;
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