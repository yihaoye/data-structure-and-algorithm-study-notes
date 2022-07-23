/**
Given a string s and an array of strings words, return the number of words[i] that is a subsequence of s.

A subsequence of a string is a new string generated from the original string with some characters (can be none) deleted without changing the relative order of the remaining characters.

For example, "ace" is a subsequence of "abcde".
 

Example 1:

Input: s = "abcde", words = ["a","bb","acd","ace"]
Output: 3
Explanation: There are three strings in words that are a subsequence of s: "a", "acd", "ace".
Example 2:

Input: s = "dsahjpjauf", words = ["ahjpjau","ja","ahbwzgqnuk","tnmlanowax"]
Output: 2
 

Constraints:

1 <= s.length <= 5 * 10^4
1 <= words.length <= 5000
1 <= words[i].length <= 50
s and words[i] consist of only lowercase English letters.
 */



// Other's Solution:
class Solution {
    public int numMatchingSubseq(String s, String[] words) {
        // https://leetcode.com/problems/number-of-matching-subsequences/discuss/117634/Efficient-and-simple-go-through-words-in-parallel-with-explanation
        // Time: O(N+M)
        Queue<int[]>[] waiting = new Queue[128];
        for (int c = 0; c <= 'z'; c++) waiting[c] = new LinkedList();
        for (int i = 0; i < words.length; i++) waiting[words[i].charAt(0)].add(new int[]{i, 1});
        for (char c : s.toCharArray()) {
            int loopTime = waiting[c].size();
            while (loopTime-- > 0) {
                int[] advance = waiting[c].poll();
                waiting[advance[1] < words[advance[0]].length() ? words[advance[0]].charAt(advance[1]++) : 0].add(advance);
            }
        }
        return waiting[0].size();
    }
}
