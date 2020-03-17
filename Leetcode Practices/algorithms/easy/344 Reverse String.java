/*
Write a function that reverses a string. The input string is given as an array of characters char[].

Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.

You may assume all the characters consist of printable ascii characters.

 

Example 1:

Input: ["h","e","l","l","o"]
Output: ["o","l","l","e","h"]
Example 2:

Input: ["H","a","n","n","a","h"]
Output: ["h","a","n","n","a","H"]
*/



// Other's Solution:
public class Solution {
    public String reverseString(String s) {
        char[] word = s.toCharArray();
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            char temp = word[i];
            word[i] = word[j];
            word[j] = temp;
            i++;
            j--;
        }
        return new String(word);
    }
}


// My Solution:
class Solution {
    public void reverseString(char[] s) {
        // index is array middle left index
        int len = s.length;
        int index = len/2 - 1;
        if (len%2 == 1) {
            index = (len-1)/2 - 1;
        }
        
        char tmp;
        while (index >= 0) {
            tmp = s[index];
            s[index] = s[len-1-index]; // last index = len - 1
            s[len-1-index] = tmp;
            index--;
        }
    }
}