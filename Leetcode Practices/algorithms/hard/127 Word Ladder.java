/**
A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:

Every adjacent pair of words differs by a single letter.
Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
sk == endWord
Given two words, beginWord and endWord, and a dictionary wordList, return the number of words in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.

 

Example 1:

Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
Output: 5
Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> cog", which is 5 words long.
Example 2:

Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
Output: 0
Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
 

Constraints:

1 <= beginWord.length <= 10
endWord.length == beginWord.length
1 <= wordList.length <= 5000
wordList[i].length == beginWord.length
beginWord, endWord, and wordList[i] consist of lowercase English letters.
beginWord != endWord
All the words in wordList are unique.
 */



// Other's Solution:
class Solution {    
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // https://leetcode.com/problems/word-ladder/discuss/1764371/A-very-highly-detailed-EXPLANATION
        Set<String> set = new HashSet<>(wordList);
        if (!set.contains(endWord)) return 0;
        
        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);
        
        Set<String> visited = new HashSet<>();
        queue.add(beginWord);
        
        int changes = 1;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String word = queue.poll();
                if (word.equals(endWord)) return changes;
                
                for (int j = 0; j < word.length(); j++) {
                    for (int k = 'a'; k <= 'z'; k++) {
                        char arr[] = word.toCharArray();
                        arr[j] = (char) k;
                        
                        String str = new String(arr);
                        if (set.contains(str) && !visited.contains(str)) {
                            queue.add(str);
                            visited.add(str);
                        }
                    }
                }
            }
            ++changes;
        }
        return 0;
    }
}



// My Solution: (性能略差)
class Solution {    
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // BFS (pruning) + HashMap
        Set<String> wordSet = new HashSet<>();
        for (String word : wordList) {
            wordSet.add(word);
        }
        if (!wordSet.contains(endWord)) return 0;
                
        int res = 0;
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        while (!queue.isEmpty()) {
            res++;
            Set<String> removes = new HashSet<>();
            Queue<String> tmpQueue = new LinkedList<>();
            while (!queue.isEmpty()) {
                String preWord = queue.poll();
                for (String word : wordSet) {
                    if (check(preWord, word)) {
                        if (endWord.equals(word)) return res + 1;
                        tmpQueue.offer(word);
                        removes.add(word);
                    }
                }
                wordSet.removeAll(removes);
            }
            queue = tmpQueue;
        }
        return 0;
    }
    
    public boolean check(String str1, String str2) {
        int diff = 0;
        for (int i=0; i<str1.length(); i++) {
            if (str1.charAt(i) != str2.charAt(i)) diff++;
            if (diff > 1) return false; // 性能优化
        }
        return diff == 1 ? true : false;
    }
}



// My Solution 2:
class Solution {
    public Set<Long> xorSet;
    
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // BFS + hash + bit operation (xor) + HashSet
        Set<Long> wordHashCodes = new HashSet<>();
        for (String word : wordList) {
            wordHashCodes.add(hash(word));
        }
        
        Long endWordHashCode = hash(endWord);
        if (!wordHashCodes.contains(endWordHashCode)) return 0;
        Long beginWordHashCode = hash(beginWord);
        
        xorSet = new HashSet<>();
        for (long i=1L; i<=31L; i++) {
            for (long j=0L, k=i; j<10L; j++, k=k<<5) 
                xorSet.add(k);
        }
        
        int res = 0;
        Queue<Long> queue = new LinkedList<>();
        queue.offer(beginWordHashCode);
        while (!queue.isEmpty()) {
            res++;
            int qSize = queue.size();
            Set<Long> removes = new HashSet<>();
            while (qSize-- > 0) {
                Long preWordHashCode = queue.poll();
                for (Long wordHashCode : wordHashCodes) {
                    if (oneCharDiff(preWordHashCode, wordHashCode)) {
                        if (endWordHashCode.equals(wordHashCode)) return res + 1;
                        removes.add(wordHashCode);
                        queue.offer(wordHashCode);
                    }
                }
                wordHashCodes.removeAll(removes);
            }
        }
        return 0;
    }
    
    public long hash(String word) {
        int len = word.length();
        long hashCode = 0L;
        for (int i=0; i<10 && i<len; i++) {
            hashCode = hashCode << 5 | (long) (word.charAt(i) - 'a');
        }
        return hashCode;
    }
    
    public boolean oneCharDiff(long hash1, long hash2) {
        long xor = hash1 ^ hash2;
        if (xorSet.contains(xor)) return true;
        return false;
    }
}
