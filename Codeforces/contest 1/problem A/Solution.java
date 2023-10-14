/*
https://codeforces.com/contest/1/problem/A

Theatre Square in the capital city of Berland has a rectangular shape with the size n × m meters. On the occasion of the city's anniversary, a decision was taken to pave the Square with square granite flagstones. Each flagstone is of the size a × a.

What is the least number of flagstones needed to pave the Square? It's allowed to cover the surface larger than the Theatre Square, but the Square has to be covered. It's not allowed to break the flagstones. The sides of flagstones should be parallel to the sides of the Square.

Input
The input contains three positive integer numbers in the first line: n,  m and a (1 ≤  n, m, a ≤ 10^9).

Output
Write the needed number of flagstones.

Examples
input
6 6 4
output
4
*/



// My Solution:
import java.io.*;
import java.util.*;

public class Solution {
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static StringTokenizer st;

    public static void main(String[] args) throws Exception {
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new PrintWriter(System.out);
        st = new StringTokenizer(reader.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        long res = process(n, m, a);
        writer.write(Long.toString(res));
        reader.close();
        writer.close();
    }

    public static long process(int n, int m, int a) {
        int x = n / a + (n % a == 0 ? 0 : 1);
        int y = m / a + (m % a == 0 ? 0 : 1);
        return (long) x * y;
    }
}
