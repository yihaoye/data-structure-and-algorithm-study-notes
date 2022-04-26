/*
Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

 

Example 1:

Input: n = 3
Output: ["((()))","(()())","(())()","()(())","()()()"]
Example 2:

Input: n = 1
Output: ["()"]
 

Constraints:

1 <= n <= 8
*/





// Other's Solution (backtrack):
class Solution {
    public List<String> generateParenthesis(int n) {
        // 回溯法，Time: O(N^2), Space: O(N)
        List<String> res = new ArrayList<String>();
        backtrack(res, "", 0, 0, n);
        return res;
    }
    
    public void backtrack(List<String> res, String str, int open, int close, int n) {
        if (str.length() == n*2) {
            res.add(str);
            return;
        }
        if (open < n) backtrack(res, str+"(", open+1, close, n);
        if (close < open) backtrack(res, str+")", open, close+1, n);
    }
}
