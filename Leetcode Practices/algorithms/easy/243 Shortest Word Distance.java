/*
Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.

Example:
Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

Input: word1 = “coding”, word2 = “practice”
Output: 3
Input: word1 = "makes", word2 = "coding"
Output: 1
Note:
You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
*/



// My Solution:
class Solution {
    public int shortestDistance(String[] words, String word1, String word2) {
        int res = words.length;
        int lastPos = -1;
        String lastWord = "";
        for (int i=0; i<words.length; i++) {
            if (words[i].equals(word1) || words[i].equals(word2)) {
                if (!words[i].equals(lastWord) && !lastWord.equals("")) res = i-lastPos < res ? i-lastPos : res;
                lastPos = i;
                lastWord = words[i];
            }
        }
        
        return res;
    }
}