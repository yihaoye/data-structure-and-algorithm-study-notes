/**
You are given two 0-indexed strings word1 and word2.

A move consists of choosing two indices i and j such that 0 <= i < word1.length and 0 <= j < word2.length and swapping word1[i] with word2[j].

Return true if it is possible to get the number of distinct characters in word1 and word2 to be equal with exactly one move. Return false otherwise.

 

Example 1:

Input: word1 = "ac", word2 = "b"
Output: false
Explanation: Any pair of swaps would yield two distinct characters in the first string, and one in the second string.
Example 2:

Input: word1 = "abcc", word2 = "aab"
Output: true
Explanation: We swap index 2 of the first string with index 0 of the second string. The resulting strings are word1 = "abac" and word2 = "cab", which both have 3 distinct characters.
Example 3:

Input: word1 = "abcde", word2 = "fghij"
Output: true
Explanation: Both resulting strings will have 5 distinct characters, regardless of which indices we swap.
 

Constraints:

1 <= word1.length, word2.length <= 10^5
word1 and word2 consist of only lowercase English letters.
 */



// My Solution:
class Solution {
    public boolean isItPossible(String word1, String word2) {
        // hashmap + dfs(backtrack)
        int[] map1 = new int[26], map2 = new int[26];
        int cnt1 = 0, cnt2 = 0;
        for (char c : word1.toCharArray()) {
            map1[c-'a']++;
            if (map1[c-'a'] == 1) cnt1++;
        }
        for (char c : word2.toCharArray()) {
            map2[c-'a']++;
            if (map2[c-'a'] == 1) cnt2++;
        }
        
        for (int i=0; i<26; i++) {
            if (map1[i] == 0) continue;
            map1[i]--; if (map1[i] == 0) cnt1--;
            map2[i]++; if (map2[i] == 1) cnt2++;
            for (int j=0; j<26; j++) {
                if (map2[j] == 0) continue;
                if (i == j && map2[j] == 1) continue;
                map1[j]++; if (map1[j] == 1) cnt1++;
                map2[j]--; if (map2[j] == 0) cnt2--;
                if (cnt1 == cnt2) return true;
                map1[j]--; if (map1[j] == 0) cnt1--;
                map2[j]++; if (map2[j] == 1) cnt2++;
            }
            map1[i]++; if (map1[i] == 1) cnt1++;
            map2[i]--; if (map2[i] == 0) cnt2--;
        }
        return false;
    }
}
