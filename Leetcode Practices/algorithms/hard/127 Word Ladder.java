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



// My Solution: (性能较差)
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
                    if (check(preWord, word) == 1) {
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
    
    public int check(String str1, String str2) {
        int diff = 0;
        for (int i=0; i<str1.length(); i++) {
            if (str1.charAt(i) != str2.charAt(i)) diff++;
        }
        return diff;
    }
}
