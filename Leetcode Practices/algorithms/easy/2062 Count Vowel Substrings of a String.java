/*
A substring is a contiguous (non-empty) sequence of characters within a string.

A vowel substring is a substring that only consists of vowels ('a', 'e', 'i', 'o', and 'u') and has all five vowels present in it.

Given a string word, return the number of vowel substrings in word.

 

Example 1:

Input: word = "aeiouu"
Output: 2
Explanation: The vowel substrings of word are as follows (underlined):
- "aeiouu"
- "aeiouu"
Example 2:

Input: word = "unicornarihan"
Output: 0
Explanation: Not all 5 vowels are present, so there are no vowel substrings.
Example 3:

Input: word = "cuaieuouac"
Output: 7
Explanation: The vowel substrings of word are as follows (underlined):
- "cuaieuouac"
- "cuaieuouac"
- "cuaieuouac"
- "cuaieuouac"
- "cuaieuouac"
- "cuaieuouac"
- "cuaieuouac"
 

Constraints:

1 <= word.length <= 100
word consists of lowercase English letters only.
*/



// Other's Solution:
class Solution {
    public int countVowelSubstrings(String word) {
        /*
            暴力解
            时间复杂度 O(N^2)，空间复杂度 O(1)
        */
        int i = 0, res = 0;
        Set<Character> s = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
        while (i < word.length() - 4) {
            int j = i;
            Set<Character> letters = new HashSet<>();
            while (j < word.length() && s.contains(word.charAt(j))) {
                letters.add(word.charAt(j));
                if (letters.size() == 5) res++;
                j++;
            }
            i++;
        }
        
        return res;
    }
}
