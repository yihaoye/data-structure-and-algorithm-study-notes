/*
Given an arbitrary ransom note string and another string containing letters from all the magazines, write a function that will return true if the ransom note can be constructed from the magazines ; otherwise, it will return false.

Each letter in the magazine string can only be used once in your ransom note.

Note:
You may assume that both strings contain only lowercase letters.

canConstruct("a", "b") -> false
canConstruct("aa", "ab") -> false
canConstruct("aa", "aab") -> true
*/



// My Solution:
class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] alphas = new int[26];
        for (char c : ransomNote.toCharArray()) {
            alphas[c-'a']++;
        }
        for (char c : magazine.toCharArray()) {
            if (alphas[c-'a'] > 0) alphas[c-'a']--;
        }
        for (int alpha : alphas) {
            if (alpha != 0) return false;
        }
        
        return true;
    }
}