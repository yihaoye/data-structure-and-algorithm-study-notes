// Question:
/*
Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

An input string is valid if:

Open brackets must be closed by the same type of brackets.
Open brackets must be closed in the correct order.
Note that an empty string is also considered valid.

Example 1:

Input: "()"
Output: true
Example 2:

Input: "()[]{}"
Output: true
Example 3:

Input: "(]"
Output: false
Example 4:

Input: "([)]"
Output: false
Example 5:

Input: "{[]}"
Output: true
*/


// Other's Solution:
class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (char c : s.toCharArray()) {
            if (c == '(')
                stack.push(')');
            else if (c == '{')
                stack.push('}');
            else if (c == '[')
                stack.push(']');
            else if (stack.isEmpty() || stack.pop() != c)
                return false;
        }
        return stack.isEmpty();
    }
}


// My Solution:
class Solution {
    public boolean isValid(String s) {
        while (!s.isEmpty()) {
            if (s.contains("()")) {
                s = s.replace("()", "");
            } else if (s.contains("{}")) {
                s = s.replace("{}", "");
            } else if (s.contains("[]")) {
                s = s.replace("[]", "");
            } else {
                return false;
            }
        }
        return true;
    }
}



// My Solution 2:
class Solution {
    public boolean isValid(String s) {
        /*
            栈
            时间复杂度 O(N)，空间复杂度最差为 O(N)
        */
        Deque<Character> stack = new LinkedList<Character>();
        for (char c : s.toCharArray()) {
            if (stack.isEmpty()) stack.push(c);
            else if ((c == ')' && stack.peek() == '(') || (c == '}' && stack.peek() == '{') || (c == ']' && stack.peek() == '[')) stack.pop();
            else stack.push(c);
        }
        return stack.isEmpty();
    }
}
