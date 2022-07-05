/**
Given an m x n board of characters and a list of strings words, return all words on the board.

Each word must be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

 

Example 1:
https://assets.leetcode.com/uploads/2020/11/07/search1.jpg

Input: board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]], words = ["oath","pea","eat","rain"]
Output: ["eat","oath"]

Example 2:
https://assets.leetcode.com/uploads/2020/11/07/search2.jpg

Input: board = [["a","b"],["c","d"]], words = ["abcb"]
Output: []
 

Constraints:

m == board.length
n == board[i].length
1 <= m, n <= 12
board[i][j] is a lowercase English letter.
1 <= words.length <= 3 * 10^4
1 <= words[i].length <= 10
words[i] consists of lowercase English letters.
All the strings of words are unique.
 */



// Other's Solution:
class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        // 回溯法+字典树 - https://leetcode.com/problems/word-search-ii/discuss/59780/Java-15ms-Easiest-Solution-(100.00)
        Set<String> res = new HashSet<>(); // use set to de-duplicate since all the strings of words are unique 
        TrieNode root = buildTrie(words);
        for (int i = 0; i < board.length; i++) 
            for (int j = 0; j < board[0].length; j++) 
                dfs(board, i, j, root, res);

        return new ArrayList<String>(res);
    }

    public void dfs(char[][] board, int i, int j, TrieNode p, Set<String> res) {
        char c = board[i][j];
        if (c == '#' || p.next[c - 'a'] == null) return;
        p = p.next[c - 'a'];
        if (p.word != null) res.add(p.word); // found one

        board[i][j] = '#'; // 设置 flag 记录当前路径已访问过 [i,j]，回溯法避免后续重复无限访问
        if (i > 0) dfs(board, i - 1, j ,p, res);
        if (j > 0) dfs(board, i, j - 1, p, res);
        if (i < board.length - 1) dfs(board, i + 1, j, p, res);
        if (j < board[0].length - 1) dfs(board, i, j + 1, p, res);
        board[i][j] = c;
    }

    public TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String w : words) {
            TrieNode p = root;
            for (char c : w.toCharArray()) {
                int i = c - 'a';
                if (p.next[i] == null) p.next[i] = new TrieNode();
                p = p.next[i];
            }
            p.word = w;
        }
        return root;
    }

    class TrieNode {
        TrieNode[] next = new TrieNode[26];
        String word;
    }
}
