/**
You are given the strings key and message, which represent a cipher key and a secret message, respectively. The steps to decode message are as follows:

Use the first appearance of all 26 lowercase English letters in key as the order of the substitution table.
Align the substitution table with the regular English alphabet.
Each letter in message is then substituted using the table.
Spaces ' ' are transformed to themselves.
For example, given key = "happy boy" (actual key would have at least one instance of each letter in the alphabet), we have the partial substitution table of ('h' -> 'a', 'a' -> 'b', 'p' -> 'c', 'y' -> 'd', 'b' -> 'e', 'o' -> 'f').
Return the decoded message.

 

Example 1:
https://assets.leetcode.com/uploads/2022/05/08/ex1new4.jpg

Input: key = "the quick brown fox jumps over the lazy dog", message = "vkbs bs t suepuv"
Output: "this is a secret"
Explanation: The diagram above shows the substitution table.
It is obtained by taking the first appearance of each letter in "the quick brown fox jumps over the lazy dog".

Example 2:
https://assets.leetcode.com/uploads/2022/05/08/ex2new.jpg

Input: key = "eljuxhpwnyrdgtqkviszcfmabo", message = "zwx hnfx lqantp mnoeius ycgk vcnjrdb"
Output: "the five boxing wizards jump quickly"
Explanation: The diagram above shows the substitution table.
It is obtained by taking the first appearance of each letter in "eljuxhpwnyrdgtqkviszcfmabo".
 

Constraints:

26 <= key.length <= 2000
key consists of lowercase English letters and ' '.
key contains every letter in the English alphabet ('a' to 'z') at least once.
1 <= message.length <= 2000
message consists of lowercase English letters and ' '.
 */



// My Solution:
class Solution {
    public String decodeMessage(String key, String message) {
        key = key.trim();
        char[] decode = new char[26];
        char i = 'a';
        for (char c : key.toCharArray()) {
            if (c == ' ' || decode[c-'a'] != 0) continue; // or decode[c-'a'] != '\0'
            decode[c-'a'] = i;
            i++;
        }
        StringBuilder strB = new StringBuilder();
        for (int j=0; j<message.length(); j++) {
            if (message.charAt(j) == ' ') strB.append(' ');
            else strB.append(decode[message.charAt(j)-'a']);
        }
        return strB.toString();
    }
}
