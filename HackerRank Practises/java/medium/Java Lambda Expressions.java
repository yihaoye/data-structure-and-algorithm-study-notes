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





/* Thinking in Java 5th 参考链接：https://lingcoder.github.io/OnJava8/#/book/13-Functional-Programming?id=lambda%e8%a1%a8%e8%be%be%e5%bc%8f */
// functional/LambdaExpressions.java
interface Description {
    String brief();
}
  
interface Body {
    String detailed(String head);
}
  
interface Multi {
    String twoArg(String head, Double d);
}
  
public class LambdaExpressions {
  
    static Body bod = h -> h + " No Parens!"; // [1]
  
    static Body bod2 = (h) -> h + " More details"; // [2]
  
    static Description desc = () -> "Short info"; // [3]
  
    static Multi mult = (h, n) -> h + n; // [4]
  
    static Description moreLines = () -> { // [5]
      System.out.println("moreLines()");
      return "from moreLines()";
    };
  
    public static void main(String[] args) {
      System.out.println(bod.detailed("Oh!"));
      System.out.println(bod2.detailed("Hi!"));
      System.out.println(desc.brief());
      System.out.println(mult.twoArg("Pi! ", 3.14159));
      System.out.println(moreLines.brief());
    }
}
/*
我们从三个接口开始，每个接口都有一个单独的方法（很快就会理解它的重要性）。但是，每个方法都有不同数量的参数，以便演示 Lambda 表达式语法。

任何 Lambda 表达式的基本语法是：

1. 参数。

2. 接着 ->，可视为“产出”。

3. -> 之后的内容都是方法体。

    [1] 当只用一个参数，可以不需要括号 ()。 然而，这是一个特例。

    [2] 正常情况使用括号 () 包裹参数。 为了保持一致性，也可以使用括号 () 包裹单个参数，虽然这种情况并不常见。

    [3] 如果没有参数，则必须使用括号 () 表示空参数列表。

    [4] 对于多个参数，将参数列表放在括号 () 中。

    到目前为止，所有 Lambda 表达式方法体都是单行。 该表达式的结果自动成为 Lambda 表达式的返回值，在此处使用 return 关键字是非法的。 这是 Lambda 表达式简化相应语法的另一种方式。

    [5] 如果在 Lambda 表达式中确实需要多行，则必须将这些行放在花括号中。 在这种情况下，就需要使用 return。

Lambda 表达式通常比匿名内部类产生更易读的代码，因此我们将在本书中尽可能使用它们。
*/

/*
递归
递归函数是一个自我调用的函数。可以编写递归的 Lambda 表达式，但需要注意：递归方法必须是实例变量或静态变量，否则会出现编译时错误。 我们将为每个案例创建一个示例。

这两个示例都需要一个接受 int 型参数并生成 int 的接口：
*/
// functional/IntCall.java
interface IntCall {
  int call(int arg);
}
// 整数 n 的阶乘将所有小于或等于 n 的正整数相乘。 阶乘函数是一个常见的递归示例：
// functional/RecursiveFactorial.java
public class RecursiveFactorial {
  static IntCall fact;
  public static void main(String[] args) {
    fact = n -> n == 0 ? 1 : n * fact.call(n - 1);
    for(int i = 0; i <= 10; i++)
      System.out.println(fact.call(i));
  }
}
// 这里，fact 是一个静态变量。 注意使用三元 if-else。 递归函数将一直调用自己，直到 i == 0。所有递归函数都有“停止条件”，否则将无限递归并产生异常。

// 我们可以将 Fibonacci 序列用递归的 Lambda 表达式来实现，这次使用实例变量：
// functional/RecursiveFibonacci.java
public class RecursiveFibonacci {
  IntCall fib;

  RecursiveFibonacci() {
    fib = n -> n == 0 ? 0 :
               n == 1 ? 1 :
               fib.call(n - 1) + fib.call(n - 2);
  }

  int fibonacci(int n) { return fib.call(n); }

  public static void main(String[] args) {
    RecursiveFibonacci rf = new RecursiveFibonacci();
    for(int i = 0; i <= 10; i++)
      System.out.println(rf.fibonacci(i));
  }
}
// 将 Fibonacci 序列中的最后两个元素求和来产生下一个元素。
