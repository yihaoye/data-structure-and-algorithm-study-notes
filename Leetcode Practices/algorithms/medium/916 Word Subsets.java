/**
You are given two string arrays words1 and words2.

A string b is a subset of string a if every letter in b occurs in a including multiplicity.

For example, "wrr" is a subset of "warrior" but is not a subset of "world".
A string a from words1 is universal if for every string b in words2, b is a subset of a.

Return an array of all the universal strings in words1. You may return the answer in any order.

 

Example 1:

Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["e","o"]
Output: ["facebook","google","leetcode"]
Example 2:

Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["l","e"]
Output: ["apple","google","leetcode"]
 

Constraints:

1 <= words1.length, words2.length <= 10^4
1 <= words1[i].length, words2[i].length <= 10
words1[i] and words2[i] consist only of lowercase English letters.
All the strings of words1 are unique.
 */



// My Solution:
class Solution {
    public List<String> wordSubsets(String[] words1, String[] words2) {
        // 哈希表（数组）- 把两边的字符串都转换成字符表及其出现次数，顺序没有关系
        List<String> res = new ArrayList<>();
        int n = words1.length, m = words2.length;
        int[] dict = new int[26];
        for (String word : words2) {
            int[] tmpDict = new int[26];
            for (char c : word.toCharArray()) {
                tmpDict[c-'a']++;
                dict[c-'a'] = Math.max(dict[c-'a'], tmpDict[c-'a']);
            }
        }
        for (String word : words1) {
            int[] tmpDict = new int[26];
            for (char c : word.toCharArray()) {
                tmpDict[c-'a']++;
            }
            boolean isValid = true;
            for (int i=0; i<26; i++) {
                if (tmpDict[i] < dict[i]) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) res.add(word);
        }
        return res;
    }
}
