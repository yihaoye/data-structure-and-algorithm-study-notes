/*
Implement function ToLowerCase() that has a string parameter str, and returns the same string in lowercase.


Example 1:

Input: "Hello"
Output: "hello"
Example 2:

Input: "here"
Output: "here"
Example 3:

Input: "LOVELY"
Output: "lovely"
*/



// My Solution:
class Solution {
    public String toLowerCase(String str) {
        // use ASCII way
        StringBuilder res = new StringBuilder(); 
        for (char c : str.toCharArray()) {
            if ((int)c > 64 && (int)c < 91) c = (char)((int)c + 32);
            res.append(c);
        }
        
        return res.toString();
    }
}