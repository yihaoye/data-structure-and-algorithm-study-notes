/*
https://codeforces.com/problemset/problem/226/D
Harry Potter has a difficult homework. Given a rectangular table, consisting of n × m cells. Each cell of the table contains the integer. Harry knows how to use two spells: the first spell change the sign of the integers in the selected row, the second — in the selected column. Harry's task is to make non-negative the sum of the numbers in each row and each column using these spells.

Alone, the boy can not cope. Help the young magician!

Input
The first line contains two integers n and m (1 ≤ n,  m ≤ 100) — the number of rows and the number of columns.

Next n lines follow, each contains m integers: j-th integer in the i-th line is ai, j (|ai, j| ≤ 100), the number in the i-th row and j-th column of the table.

The rows of the table numbered from 1 to n. The columns of the table numbered from 1 to m.

Output
In the first line print the number a — the number of required applications of the first spell. Next print a space-separated integers — the row numbers, you want to apply a spell. These row numbers must be distinct!

In the second line print the number b — the number of required applications of the second spell. Next print b space-separated integers — the column numbers, you want to apply a spell. These column numbers must be distinct!

If there are several solutions are allowed to print any of them.

Examples
input
4 1
-1
-1
-1
-1
output
4 1 2 3 4 
0 

input
2 4
-1 -1 -1 2
1 1 1 1
output
1 1 
1 4 
*/



import java.util.*;

public class D_The_table {
    static int[][] A; // 存储输入的矩阵
    static boolean[] x; // 记录每行是否需要翻转
    static boolean[] y; // 记录每列是否需要翻转

    public static void main(String[] args) {
        // 构造 + Greedy - https://www.cnblogs.com/SovietPower/p/10462907.html
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        A = new int[n+1][m+1];
        x = new boolean[n+1];
        y = new boolean[m+1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) A[i][j] = scanner.nextInt();
        }

        while (true) {
            boolean ok = true; // 标记是否所有行和列的和都为非负
            
            /* explain by claude.ai
            当对行或列进行翻转时，实际上是将该行或列的所有元素乘以-1。但是，如果一个元素所在的行和列都被翻转了，那么这个元素实际上被乘以了两次-1，结果还是原来的值。
            所以：
            如果 x[i] 和 y[j] 中只有一个为true，那么 A[i][j] 需要变号（乘以-1）
            如果 x[i] 和 y[j] 都为true或都为false，那么 A[i][j] 保持原值

            这就是为什么使用异或操作 ^：它正好能表达 "只有一个为 true" 这个条件。
            总结一下：
            当 x[i] ^ y[j] 为 true 时，将 -A[i][j] 加到 s 上（相当于翻转了这个元素）
            当 x[i] ^ y[j] 为 false 时，将 A[i][j] 加到 s 上（保持原值）
            这样，就能在不实际修改矩阵的情况下，计算出如果应用了所有的行和列翻转操作后，每一行或每一列的和。
            */
            for (int i = 1; i <= n; i++) { // 检查每一行的和，如果和为负，则标记需要翻转这一行
                int s = 0;
                for (int j = 1; j <= m; j++) {
                    s += x[i] ^ y[j] ? -A[i][j] : A[i][j];
                }
                if (s < 0) {
                    x[i] ^= true; // 异或操作 翻转行标记
                    ok = false;
                }
            }

            for (int j = 1; j <= m; j++) { // 检查每一列的和，如果和为负，则标记需要翻转这一列
                int s = 0;
                for (int i = 1; i <= n; i++) {
                    s += x[i] ^ y[j] ? -A[i][j] : A[i][j];
                }
                if (s < 0) {
                    y[j] ^= true; // 异或操作 翻转列标记
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
