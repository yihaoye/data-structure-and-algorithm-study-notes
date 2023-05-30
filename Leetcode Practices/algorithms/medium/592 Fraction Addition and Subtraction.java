/*
Given a string expression representing an expression of fraction addition and subtraction, return the calculation result in string format.

The final result should be an irreducible fraction. If your final result is an integer, change it to the format of a fraction that has a denominator 1. So in this case, 2 should be converted to 2/1.

 

Example 1:

Input: expression = "-1/2+1/2"
Output: "0/1"
Example 2:

Input: expression = "-1/2+1/2+1/3"
Output: "1/3"
Example 3:

Input: expression = "1/3-1/2"
Output: "-1/6"
 

Constraints:

The input string only contains '0' to '9', '/', '+' and '-'. So does the output.
Each fraction (input and output) has the format ±numerator/denominator. If the first input fraction or the output is positive, then '+' will be omitted.
The input only contains valid irreducible fractions, where the numerator and denominator of each fraction will always be in the range [1, 10]. If the denominator is 1, it means this fraction is actually an integer in a fraction format defined above.
The number of given fractions will be in the range [1, 10].
The numerator and denominator of the final result are guaranteed to be valid and in the range of 32-bit int.
*/



// Other's Solution:
class Solution {
    public String fractionAddition(String expression) {
        // https://leetcode.com/problems/fraction-addition-and-subtraction/solutions/103384/small-simple-c-java-python/
        Scanner sc = new Scanner(expression).useDelimiter("/|(?=[-+])");
        int A = 0, B = 1;
        while (sc.hasNext()) {
            int a = sc.nextInt(), b = sc.nextInt();
            A = A * b + a * B;
            B *= b;
            int g = gcd(A, B);
            A /= g;
            B /= g;
        }
        return A + "/" + B;
    }

    private int gcd(int a, int b) {
        return a != 0 ? gcd(b % a, a) : Math.abs(b);
    }
}



// My Solution:
class Solution {
    public String fractionAddition(String expression) {
        // gcd
        int[] fraction = new int[]{0, 1, 0}; // {numerator, denominator, next unvisited index}
        if (expression.charAt(0) != '-') expression = "+" + expression;
        while (fraction[2] < expression.length()) {
            int[] nextFraction = getNextFraction(expression, fraction);
            sum(fraction, nextFraction);
        }
        String res = fraction[0] + "/" + fraction[1];
        return res;
    }

    public int[] getNextFraction(String expression, int[] lastFraction) {
        int nextIdx = lastFraction[2];
        int sign = expression.charAt(nextIdx++) == '+' ? 1 : -1;

        int numerator = 0;
        while (expression.charAt(nextIdx) != '/') {
            numerator = numerator * 10 + (expression.charAt(nextIdx++) - '0');
        }
        numerator *= sign;
        nextIdx++; // skip '/'

        int denominator = 0;
        while (nextIdx < expression.length() && expression.charAt(nextIdx) != '-' && expression.charAt(nextIdx) != '+') {
            denominator = denominator * 10 + (expression.charAt(nextIdx++) - '0');
        }

        lastFraction[2] = nextIdx;
        return new int[]{numerator, denominator};
    }

    public void sum(int[] lastFraction, int[] curFraction) {
        lastFraction[0] *= curFraction[1];
        curFraction[0] *= lastFraction[1];
        lastFraction[1] = lastFraction[1] * curFraction[1];
        curFraction[1] = lastFraction[1] * curFraction[1];
        lastFraction[0] += curFraction[0];

        int gcd = gcd(Math.abs(lastFraction[0]), Math.abs(lastFraction[1]));
        lastFraction[0] /= gcd;
        lastFraction[1] /= gcd;
    }

    public int gcd(int a, int b) {
        if (a == 0) return b;
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}

// My Solution (Original with lcm):
class Solution {
    public String fractionAddition(String expression) {
        int[] fraction = new int[]{0, 1, 0}; // {numerator, denominator, next unvisited index}
        if (expression.charAt(0) != '-') expression = "+" + expression;
        while (fraction[2] < expression.length()) {
            int[] nextFraction = getNextFraction(expression, fraction);
            sum(fraction, nextFraction);
        }
        String res = fraction[0] + "/" + fraction[1];
        return res;
    }

    public int[] getNextFraction(String expression, int[] lastFraction) {
        int nextIdx = lastFraction[2];
        int sign = expression.charAt(nextIdx++) == '+' ? 1 : -1;

        int numerator = 0;
        while (expression.charAt(nextIdx) != '/') {
            numerator = numerator * 10 + (expression.charAt(nextIdx++) - '0');
        }
        numerator *= sign;
        nextIdx++; // skip '/'

        int denominator = 0;
        while (nextIdx < expression.length() && expression.charAt(nextIdx) != '-' && expression.charAt(nextIdx) != '+') {
            denominator = denominator * 10 + (expression.charAt(nextIdx++) - '0');
        }

        lastFraction[2] = nextIdx;
        return new int[]{numerator, denominator};
    }

    public void sum(int[] lastFraction, int[] curFraction) {
        int lcm = lcm(lastFraction[1], curFraction[1]);
        lastFraction[0] *= (lcm / lastFraction[1]);
        curFraction[0] *= (lcm / curFraction[1]);
        lastFraction[1] = lcm;
        curFraction[1] = lcm;
        lastFraction[0] += curFraction[0];

        int gcd = gcd(Math.abs(lastFraction[0]), Math.abs(lastFraction[1]));
        lastFraction[0] /= gcd;
        lastFraction[1] /= gcd;
    }

    // 最小公倍数
    public int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    // 最大公约数
    public int gcd(int a, int b) {
        if (a == 0) return b;
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
