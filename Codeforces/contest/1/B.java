/*
https://codeforces.com/contest/1/problem/B

In the popular spreadsheets systems (for example, in Excel) the following numeration of columns is used. The first column has number A, the second — number B, etc. till column 26 that is marked by Z. Then there are two-letter numbers: column 27 has number AA, 28 — AB, column 52 is marked by AZ. After ZZ there follow three-letter numbers, etc.

The rows are marked by integer numbers starting with 1. The cell name is the concatenation of the column and the row numbers. For example, BC23 is the name for the cell that is in column 55, row 23.

Sometimes another numeration system is used: RXCY, where X and Y are integer numbers, showing the column and the row numbers respectfully. For instance, R23C55 is the cell from the previous example.

Your task is to write a program that reads the given sequence of cell coordinates and produce each item written according to the rules of another numeration system.

Input
The first line of the input contains integer number n (1 ≤ n ≤ 10^5), the number of coordinates in the test. Then there follow n lines, each of them contains coordinates. All the coordinates are correct, there are no cells with the column and/or the row numbers larger than 10^6 .

Output
Write n lines, each line should contain a cell coordinates in the other numeration system.

Examples
input
2
R23C55
BC23
output
BC23
R23C55
*/



// My Solution:
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class B {
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static Pattern pattern = Pattern.compile("R\\d+C\\d+");
    private static Matcher matcher;

    public static void main(String[] args) throws Exception {
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new PrintWriter(System.out);
        int n = Integer.parseInt(reader.readLine());
        while (n-- > 0) {
            String res = process(reader.readLine());
            writer.write(res + "\n");
        }
        reader.close();
        writer.close();
    }

    public static String process(String s) {
        if (isRXCY(s)) {
            int cIndex = s.indexOf('C');
            int r = Integer.parseInt(s.substring(1, cIndex));
            int c = Integer.parseInt(s.substring(cIndex + 1));
            return convert(c) + r;
        } else {
            int dIndex = 0; // digit start index
            while (dIndex < s.length() && Character.isAlphabetic(s.charAt(dIndex))) {
                dIndex++;
            }
            int r = Integer.parseInt(s.substring(dIndex));
            String c = s.substring(0, dIndex);
            return "R" + r + "C" + convert(c);
        }
    }

    public static boolean isRXCY(String input) {
        matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static int convert(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res = res * 26 + (s.charAt(i) - 'A' + 1);
        }
        return res;
    }

    public static String convert(int num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            int rem = num % 26;
            if (rem == 0) {
                rem = 26;
                num -= 26;
            }
            sb.append((char) ('A' + rem - 1));
            num /= 26;
        }
        return sb.reverse().toString();
    }
}
