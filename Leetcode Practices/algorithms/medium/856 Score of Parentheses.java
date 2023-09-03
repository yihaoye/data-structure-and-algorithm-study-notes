/*
Given a balanced parentheses string s, return the score of the string.

The score of a balanced parentheses string is based on the following rule:

"()" has score 1.
AB has score A + B, where A and B are balanced parentheses strings.
(A) has score 2 * A, where A is a balanced parentheses string.
 

Example 1:

Input: s = "()"
Output: 1
Example 2:

Input: s = "(())"
Output: 2
Example 3:

Input: s = "()()"
Output: 2
 

Constraints:

2 <= s.length <= 50
s consists of only '(' and ')'.
s is a balanced parentheses string.
*/



// Other's Solution:
class Solution {
    public int scoreOfParentheses(String S) {
        /*
             栈：https://leetcode.cn/problems/score-of-parentheses/solutions/1878233/by-ac_oier-0mhz/
        */
        Deque<Integer> stack = new LinkedList();
        stack.push(0);
        
        for (char c: S.toCharArray()) {
            if (c == '(') stack.push(0);
            else {
                int curr = stack.pop();
                int prev = stack.pop();
                stack.push(prev + Math.max(2 * curr, 1));
            }
        }

        return stack.pop();
    }
}
