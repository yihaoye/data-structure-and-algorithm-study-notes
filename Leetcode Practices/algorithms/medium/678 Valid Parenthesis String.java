/**
Given a string s containing only three types of characters: '(', ')' and '*', return true if s is valid.

The following rules define a valid string:

Any left parenthesis '(' must have a corresponding right parenthesis ')'.
Any right parenthesis ')' must have a corresponding left parenthesis '('.
Left parenthesis '(' must go before the corresponding right parenthesis ')'.
'*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string "".
 

Example 1:

Input: s = "()"
Output: true
Example 2:

Input: s = "(*)"
Output: true
Example 3:

Input: s = "(*))"
Output: true
 

Constraints:

1 <= s.length <= 100
s[i] is '(', ')' or '*'.
 */



// Other's Solution:
class Solution {
    public boolean checkValidString(String s) {
        // 标记变量 - https://leetcode.com/problems/valid-parenthesis-string/discuss/543521/Java-Count-Open-Parenthesis-O(n)-time-O(1)-space-Picture-Explain
        // Time: O(N), Space: O(1)
        int cmin = 0, cmax = 0; // open parentheses count in range [cmin, cmax]
        for (char c : s.toCharArray()) {
            if (c == '(') {
                cmax++;
                cmin++;
            } else if (c == ')') {
                cmax--;
                cmin--;
            } else if (c == '*') {
                cmax++; // if `*` become `(` then openCount++
                cmin--; // if `*` become `)` then openCount--
                // if `*` become `` then nothing happens
                // So openCount will be in new range [cmin-1, cmax+1]
            }
            if (cmax < 0) return false; // Currently, don't have enough open parentheses to match close parentheses-> Invalid
                                        // For example: ())(
            cmin = Math.max(cmin, 0);   // It's invalid if open parentheses count < 0 that's why cmin can't be negative
        }
        return cmin == 0; // Return true if can found `openCount == 0` in range [cmin, cmax]
    }
}



// Other's Solution:
class Solution {
    public boolean checkValidString(String s) {
        // 双栈+贪婪 - https://leetcode.com/problems/valid-parenthesis-string/discuss/107572/Java-using-2-stacks.-O(n)-space-and-time-complexity.
        // Time: O(N), Space: O(N)
        Deque<Integer> leftIdx = new LinkedList<>();
        Deque<Integer> starIdx = new LinkedList<>();
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                leftIdx.push(i);
            } else if (c == '*') {
                starIdx.push(i);
            } else { // ')'
                if (leftIdx.isEmpty() && starIdx.isEmpty()) return false;

                if (!leftIdx.isEmpty()) leftIdx.pop();
                else starIdx.pop();
            }
        }
        while (!leftIdx.isEmpty() && !starIdx.isEmpty()) {
            if (leftIdx.pop() > starIdx.pop()) return false;
        }
        return leftIdx.isEmpty();
    }
}
