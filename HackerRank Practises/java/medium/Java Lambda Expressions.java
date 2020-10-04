/*
This Java 8 challenge tests your knowledge of Lambda expressions!

Write the following methods that return a lambda expression performing a specified action:

1. PerformOperation isOdd(): The lambda expression must return true if a number is odd or false if it is even.
2. PerformOperation isPrime(): The lambda expression must return true if a number is prime or false if it is composite.
3. PerformOperation isPalindrome(): The lambda expression must return true if a number is a palindrome or false if it is not.
Input Format

Input is handled for you by the locked stub code in your editor.

Output Format

The locked stub code in your editor will print T lines of output.

Sample Input

The first line contains an integer, T (the number of test cases).

The T subsequent lines each describe a test case in the form of 2 space-separated integers:
The first integer specifies the condition to check for (1 for Odd/Even, 2 for Prime, or 3 for Palindrome). The second integer denotes the number to be checked.

5
1 4
2 5
3 898
1 3
2 12
Sample Output

EVEN
PRIME
PALINDROME
ODD
COMPOSITE
*/



// other's solution:
import java.io.*;
import java.util.*;
interface PerformOperation {
 boolean check(int a);
}
class MyMath {
 public static boolean checker(PerformOperation p, int num) {
  return p.check(num);
 }
 public static PerformOperation isOdd() {
  return num -> num%2 != 0;
 }
 public static PerformOperation isPrime() {
  return num -> java.math.BigInteger.valueOf(num).isProbablePrime(1);
 }
 public static PerformOperation isPalindrome() {
  return num -> Integer.toString(num).equals(new StringBuilder(Integer.toString(num)).reverse().toString());
 }
}

public class Solution {

 public static void main(String[] args) throws IOException {
  MyMath ob = new MyMath();
  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  int T = Integer.parseInt(br.readLine());
  PerformOperation op;
  boolean ret = false;
  String ans = null;
  while (T--> 0) {
   String s = br.readLine().trim();
   StringTokenizer st = new StringTokenizer(s);
   int ch = Integer.parseInt(st.nextToken());
   int num = Integer.parseInt(st.nextToken());
   if (ch == 1) {
    op = ob.isOdd();
    ret = ob.checker(op, num);
    ans = (ret) ? "ODD" : "EVEN";
   } else if (ch == 2) {
    op = ob.isPrime();
    ret = ob.checker(op, num);
    ans = (ret) ? "PRIME" : "COMPOSITE";
   } else if (ch == 3) {
    op = ob.isPalindrome();
    ret = ob.checker(op, num);
    ans = (ret) ? "PALINDROME" : "NOT PALINDROME";
   }
   System.out.println(ans);
  }
 }
}
