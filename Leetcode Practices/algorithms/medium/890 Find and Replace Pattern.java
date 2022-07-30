/**
Given a list of strings words and a string pattern, return a list of words[i] that match pattern. You may return the answer in any order.

A word matches the pattern if there exists a permutation of letters p so that after replacing every letter x in the pattern with p(x), we get the desired word.

Recall that a permutation of letters is a bijection from letters to letters: every letter maps to another letter, and no two letters map to the same letter.

 

Example 1:

Input: words = ["abc","deq","mee","aqq","dkd","ccc"], pattern = "abb"
Output: ["mee","aqq"]
Explanation: "mee" matches the pattern because there is a permutation {a -> m, b -> e, ...}. 
"ccc" does not match the pattern because {a -> c, b -> c, ...} is not a permutation, since a and b map to the same letter.
Example 2:

Input: words = ["a","b","c"], pattern = "a"
Output: ["a","b","c"]
 

Constraints:

1 <= pattern.length <= 20
1 <= words.length <= 50
words[i].length == pattern.length
pattern and words[i] are lowercase English letters.
 */



// My Solution:
class Solution {
    public int mod = (int) 1e9 + 7;
    
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        // 自定义哈希 - 把所有第一个出现的字母映射为 0，第二个映射为 1，如此类推
        // Time: O(N*M), Space: O(M)
        List<String> res = new ArrayList<>();
        int pHash = hash(pattern);
        for (String word : words) {
            if (pHash == hash(word)) res.add(word);
        }
        return res;
    }
    
    // s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
    // https://hg.openjdk.java.net/jdk7u/jdk7u6/jdk/file/8c2c5d63a17e/src/share/classes/java/lang/String.java#l1436
    public int hash(String str) {
        int res = 0, len = str.length(), nextMapInt = 0;
        Map<Character, Integer> cMap = new HashMap<>();
        for (char c : str.toCharArray()) {
            if (!cMap.containsKey(c)) cMap.put(c, nextMapInt++);
            res = (res * 31 % mod + cMap.get(c)) % mod;
        }
        return res;
    }
}



// My Solution 2: (性能稍微不如上面的 Solution)
class Solution {
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        // 自定义哈希 - 把所有第一个出现的字母映射为 a，第二个映射为 b，如此类推
        List<String> res = new ArrayList<>();
        int pHash = hash(pattern);
        for (String word : words) {
            if (pHash == hash(word)) res.add(word);
        }
        return res;
    }
    
    public int hash(String str) {
        StringBuilder strB = new StringBuilder();
        Map<Character, Character> cMap = new HashMap<>();
        char nextMapChar = 'a';
        for (char c : str.toCharArray()) {
            if (!cMap.containsKey(c)) {
                cMap.put(c, nextMapChar++);
            }
            strB.append(cMap.get(c));
        }
        return strB.toString().hashCode();
    }
}
