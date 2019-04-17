/*
Write a function that takes a string as input and reverse only the vowels of a string.

Example 1:

Input: "hello"
Output: "holle"
Example 2:

Input: "leetcode"
Output: "leotcede"
Note:
The vowels does not include the letter "y".
*/



// My Solution:
class Solution {
    public String reverseVowels(String s) {
        List<Character> vowels = new ArrayList<>(Arrays.asList('a','e','i','o','u','A','E','I','O','U'));
        StringBuilder res = new StringBuilder();
        Stack<Character> stack = new Stack<Character>();
        char[] charArr = s.toCharArray();
        for (char c : charArr) {
            if (vowels.contains(c)) {
                stack.push(c);
            }
        }
        for (char c : charArr) {
            if (vowels.contains(c)) {
                res.append(stack.pop());
            } else {
                res.append(c);
            }
        }
        
        return res.toString();
    }    
}