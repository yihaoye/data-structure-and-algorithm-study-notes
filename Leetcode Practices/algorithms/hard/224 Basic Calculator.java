/**
Given a string s representing a valid expression, implement a basic calculator to evaluate it, and return the result of the evaluation.

Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as eval().

 

Example 1:

Input: s = "1 + 1"
Output: 2
Example 2:

Input: s = " 2-1 + 2 "
Output: 3
Example 3:

Input: s = "(1+(4+5+2)-3)+(6+8)"
Output: 23
 

Constraints:

1 <= s.length <= 3 * 105
s consists of digits, '+', '-', '(', ')', and ' '.
s represents a valid expression.
'+' is not used as a unary operation (i.e., "+1" and "+(2 + 3)" is invalid).
'-' could be used as a unary operation (i.e., "-1" and "-(2 + 3)" is valid).
There will be no two consecutive operators in the input.
Every number and running calculation will fit in a signed 32-bit integer.
 */



// Other's Solution:
class Solution {
    // 使用 map 维护一个运算符优先级 这里的优先级划分按照"数学"进行划分即可
    Map<Character, Integer> map = new HashMap<>(){{
        put('-', 1);
        put('+', 1);
        put('*', 2);
        put('/', 2);
        put('%', 2);
        put('^', 3);
    }};
    
    public int calculate(String s) {
        // 双栈（通用表达式问题的通用模版） - https://leetcode.cn/problems/basic-calculator-ii/solution/shi-yong-shuang-zhan-jie-jue-jiu-ji-biao-c65k/
        // Time: O(N), Space: O(N)
        
        s = s.replaceAll(" ", ""); // 将所有的空格去掉
        char[] cArray = s.toCharArray();
        int n = s.length();
        Deque<Integer> nums = new ArrayDeque<>(); // 存放所有的数字
        nums.addLast(0); // 为了防止第一个数为负数，先往 nums 加个 0
        Deque<Character> ops = new ArrayDeque<>(); // 存放所有「非数字以外」的操作
        for (int i = 0; i < n; i++) {
            char c = cArray[i];
            if (c == '(') {
                ops.addLast(c);
            } else if (c == ')') {
                // 计算到最近一个左括号为止
                while (!ops.isEmpty()) {
                    if (ops.peekLast() != '(') {
                        calc(nums, ops);
                    } else {
                        ops.pollLast();
                        break;
                    }
                }
            } else {
                if (Character.isDigit(c)) {
                    int u = 0, j = i;
                    while (j < n && Character.isDigit(cArray[j])) u = u * 10 + (cArray[j++] - '0'); // 将从 i 位置开始后面的连续数字整体取出，加入 nums
                    nums.addLast(u);
                    i = j - 1;
                } else {
                    if (i > 0 && (cArray[i - 1] == '(' || cArray[i - 1] == '+' || cArray[i - 1] == '-')) {
                        nums.addLast(0);
                    }
                    while (!ops.isEmpty() && ops.peekLast() != '(') { // 有一个新操作要入栈时，先把栈内可以算的都算了，只有满足「栈内运算符」比「当前运算符」优先级高/同等，才进行运算
                        char prev = ops.peekLast();
                        if (map.get(prev) >= map.get(c)) {
                            calc(nums, ops);
                        } else {
                            break;
                        }
                    }
                    ops.addLast(c);
                }
            }
        }
        while (!ops.isEmpty()) calc(nums, ops); // 将剩余的计算完
        return nums.peekLast();
    }
    
    void calc(Deque<Integer> nums, Deque<Character> ops) {
        if (nums.isEmpty() || nums.size() < 2) return;
        if (ops.isEmpty()) return;
        int b = nums.pollLast(), a = nums.pollLast();
        char op = ops.pollLast();
        int res = 0;
        if (op == '+') res = a + b;
        else if (op == '-') res = a - b;
        else if (op == '*') res = a * b;
        else if (op == '/') res = a / b;
        else if (op == '^') res = (int) Math.pow(a, b);
        else if (op == '%') res = a % b;
        nums.addLast(res);
    }
}
