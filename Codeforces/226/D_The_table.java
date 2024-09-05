import java.util.*;

public class D_The_table {
    static final int N = 105;
    static int[][] A = new int[N][N];
    static boolean[] x = new boolean[N];
    static boolean[] y = new boolean[N];

    public static void main(String[] args) {
        // 构造 + Greedy - https://www.cnblogs.com/SovietPower/p/10462907.html
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                A[i][j] = scanner.nextInt();
            }
        }

        while (true) {
            boolean ok = true;
            
            for (int i = 1; i <= n; i++) {
                int s = 0;
                for (int j = 1; j <= m; j++) {
                    s += x[i] ^ y[j] ? -A[i][j] : A[i][j];
                }
                if (s < 0) {
                    x[i] ^= true;
                    ok = false;
                }
            }

            for (int j = 1; j <= m; j++) {
                int s = 0;
                for (int i = 1; i <= n; i++) {
                    s += x[i] ^ y[j] ? -A[i][j] : A[i][j];
                }
                if (s < 0) {
                    y[j] ^= true;
                    ok = false;
                }
            }

            if (ok) break;
        }

        print(x, n);
        print(y, m);
        
        scanner.close();
    }

    static void print(boolean[] a, int n) {
        int t = 0;
        for (int i = 1; i <= n; i++) {
            if (a[i]) t++;
        }
        System.out.print(t + " ");
        for (int i = 1; i <= n; i++) {
            if (a[i]) System.out.print(i + " ");
        }
        System.out.println();
    }
}
