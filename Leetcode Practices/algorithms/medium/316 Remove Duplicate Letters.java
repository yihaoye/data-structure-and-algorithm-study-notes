/*
Given a string s, remove duplicate letters so that every letter appears once and only once. You must make sure your result is 
the smallest in lexicographical order
 among all possible results.

 

Example 1:

Input: s = "bcabc"
Output: "abc"
Example 2:

Input: s = "cbacdcbc"
Output: "acdb"
 

Constraints:

1 <= s.length <= 10^4
s consists of lowercase English letters.
 

Note: This question is the same as 1081: https://leetcode.com/problems/smallest-subsequence-of-distinct-characters/
*/



// Other's Solution:
class Solution {
    public String removeDuplicateLetters(String s) {
        // 单调栈 - https://leetcode.com/problems/remove-duplicate-letters/solutions/1859410/java-c-detailed-visually-explained/
        int[] lastIndex = new int[26];
        for (int i = 0; i < s.length(); i++){
            lastIndex[s.charAt(i) - 'a'] = i; // track the lastIndex of character presence
        }
        
        Deque<Integer> st = new LinkedList<>(); // 单调栈里存的是字符的数字表示，比如 a 为 0、b 为 1
        
        for (int i = 0; i < s.length(); i++) {
            int curr = s.charAt(i) - 'a';
            if (st.contains(curr)) continue; // 栈里已有该字符，不需要再后续处理，因为该字符经历过后面的逻辑，必然其前后都已满足贪心的安排
            while (!st.isEmpty() && st.peek() > curr && i < lastIndex[st.peek()]) { // 组合型单调栈（尽量递增但不严格，因为还要满足 pop 的元素在之后还有，属于贪心思路）
                st.pop(); // pop out
            }
            st.push(curr); // add into stack
        }

        StringBuilder sb = new StringBuilder();
        while (!st.isEmpty()) sb.append((char) (st.pop() + 'a'));
        return sb.reverse().toString();
    }
}
