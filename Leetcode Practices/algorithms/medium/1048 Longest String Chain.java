/**
You are given an array of words where each word consists of lowercase English letters.

wordA is a predecessor of wordB if and only if we can insert exactly one letter anywhere in wordA without changing the order of the other characters to make it equal to wordB.

For example, "abc" is a predecessor of "abac", while "cba" is not a predecessor of "bcad".
A word chain is a sequence of words [word1, word2, ..., wordk] with k >= 1, where word1 is a predecessor of word2, word2 is a predecessor of word3, and so on. A single word is trivially a word chain with k == 1.

Return the length of the longest possible word chain with words chosen from the given list of words.

 

Example 1:

Input: words = ["a","b","ba","bca","bda","bdca"]
Output: 4
Explanation: One of the longest word chains is ["a","ba","bda","bdca"].
Example 2:

Input: words = ["xbc","pcxbcf","xb","cxbc","pcxbc"]
Output: 5
Explanation: All the words can be put in a word chain ["xb", "xbc", "cxbc", "pcxbc", "pcxbcf"].
Example 3:

Input: words = ["abcd","dbqca"]
Output: 1
Explanation: The trivial word chain ["abcd"] is one of the longest word chains.
["abcd","dbqca"] is not a valid word chain because the ordering of the letters is changed.
 

Constraints:

1 <= words.length <= 1000
1 <= words[i].length <= 16
words[i] only consists of lowercase English letters.
 */



// Other's Solution:
class Solution {
    public int longestStrChain(String[] words) {
        /*
            按 word 长度排序 + DP. For each word, loop on all possible previous word with 1 letter missing.
            If find this previous word, update the longest chain for the current word. Finally return the longest word chain.
            Time: O(N*logN), Space: O(N)
        */
        Map<String, Integer> dp = new HashMap<>();
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        int res = 0;
        for (String word : words) {
            int longest = 0;
            for (int i = 0; i < word.length(); ++i) {
                String prev = word.substring(0, i) + word.substring(i + 1);
                longest = Math.max(longest, dp.getOrDefault(prev, 0) + 1);
            }
            dp.put(word, longest);
            res = Math.max(res, longest);
        }
        return res;
    }
}



// My Solution: (TLE)
class Solution {
    public int longestStrChain(String[] words) {
        // 排序（按字符长度）+多栈（每个栈对比栈顶并尝试压栈，然后计算最长的栈的长度，可以用一个哈希表包裹，键为栈顶字符串的长度为栈集合）
        int n = words.length;
        Map<Integer, Set<ArrayDeque<String>>> map = new HashMap<>();
        Arrays.sort(words, new Comparator<String>() {
            public int compare(String str1, String str2) {
                return str1.length() - str2.length();
            }
        });
        int res = 0;
        for (int i=0; i<n; i++) {
            Set<ArrayDeque<String>> set = map.getOrDefault(words[i].length()-1, new HashSet<ArrayDeque<String>>());
            boolean foundPredecessor = false;
            for (ArrayDeque<String> stack : set) {
                if (isPredecessor(stack.peek(), words[i])) {
                    foundPredecessor = true;
                    ArrayDeque<String> stackClone = stack.clone();
                    stackClone.push(words[i]);
                    res = Math.max(res, stackClone.size());
                    Set<ArrayDeque<String>> tmpSet = map.getOrDefault(words[i].length(), new HashSet<ArrayDeque<String>>());
                    tmpSet.add(stackClone);
                    map.put(words[i].length(), tmpSet);
                }
            }
            if (!foundPredecessor) {
                ArrayDeque<String> stack = new ArrayDeque<String>();
                stack.push(words[i]);
                res = Math.max(res, stack.size());
                Set<ArrayDeque<String>> tmpSet = map.getOrDefault(words[i].length(), new HashSet<ArrayDeque<String>>());
                tmpSet.add(stack);
                map.put(words[i].length(), tmpSet);
            }
        }
        
        return res;
    }
    
    private boolean isPredecessor(String wordA, String wordB) {
        // 双指针判断
        int aLen = wordA.length(), bLen = wordB.length(), found = 0;
        // if (bLen - aLen != 1) return false; // no need
        for (int i=0, j=0; i<aLen && j<bLen; i++, j++) {
            if (wordA.charAt(i) != wordB.charAt(j)) {
                if (found++ > 0) return false;
                if (wordA.charAt(i) != wordB.charAt(++j)) return false;
            }
        }
        return true;
    }
}
