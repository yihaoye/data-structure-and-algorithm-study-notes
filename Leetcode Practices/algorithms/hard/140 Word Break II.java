/**
Given a string s and a dictionary of strings wordDict, add spaces in s to construct a sentence where each word is a valid dictionary word. Return all such possible sentences in any order.

Note that the same word in the dictionary may be reused multiple times in the segmentation.

 

Example 1:

Input: s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
Output: ["cats and dog","cat sand dog"]
Example 2:

Input: s = "pineapplepenapple", wordDict = ["apple","pen","applepen","pine","pineapple"]
Output: ["pine apple pen apple","pineapple pen apple","pine applepen apple"]
Explanation: Note that you are allowed to reuse a dictionary word.
Example 3:

Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
Output: []
 

Constraints:

1 <= s.length <= 20
1 <= wordDict.length <= 1000
1 <= wordDict[i].length <= 10
s and wordDict[i] consist of only lowercase English letters.
All the strings of wordDict are unique.
 */



// My Solution:
class Solution {
    Map<Integer, TrieNode> mem; // s[i..j], <(int)(i << 5 | j), TrieNode>
    Set<String> wordSet;

    public List<String> wordBreak(String s, List<String> wordDict) {
        // set + trie + hashmap + 记忆化搜索 + 递归
        mem = new HashMap<>();
        wordSet = new HashSet<>(wordDict);

        TrieNode root = new TrieNode(null, dfs(s, 0));
        for (TrieNode child : root.children) {
            root.convert.addAll(child.convert);
        }

        return root.convert;
    }

    public List<TrieNode> dfs(String s, int start) {
        List<TrieNode> res = new ArrayList<>();
        String tmpCurStr = "";
        int idx = start;
        while (idx < s.length()) {
            int compress = (start << 5) | idx;
            tmpCurStr += s.charAt(idx++);
            if (mem.containsKey(compress)) {
                res.add(mem.get(compress));
            } else if (wordSet.contains(tmpCurStr)) {
                List<TrieNode> children = dfs(s, idx);
                if (children.size() > 0 || idx == s.length()) {
                    TrieNode node = new TrieNode(tmpCurStr, children);
                    if (children.size() == 0) node.convert.add(node.word);
                    for (TrieNode child : children) {
                        for (String tmp : child.convert) node.convert.add(node.word + " " + tmp);
                    }
                    res.add(node);
                    mem.put(compress, node);
                }
            }
        }
        return res;
    }

    class TrieNode {
        String word;
        List<TrieNode> children;
        List<String> convert;

        public TrieNode(String word, List<TrieNode> children) {
            this.word = word;
            this.children = children;
            this.convert = new ArrayList<>();
        }
    }
}
