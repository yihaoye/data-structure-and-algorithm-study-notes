/**
Design a data structure that supports adding new words and finding if a string matches any previously added string.

Implement the WordDictionary class:

WordDictionary() Initializes the object.
void addWord(word) Adds word to the data structure, it can be matched later.
bool search(word) Returns true if there is any string in the data structure that matches word or false otherwise. word may contain dots '.' where dots can be matched with any letter.
 

Example:

Input
["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
[[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
Output
[null,null,null,null,false,true,true,true]

Explanation
WordDictionary wordDictionary = new WordDictionary();
wordDictionary.addWord("bad");
wordDictionary.addWord("dad");
wordDictionary.addWord("mad");
wordDictionary.search("pad"); // return False
wordDictionary.search("bad"); // return True
wordDictionary.search(".ad"); // return True
wordDictionary.search("b.."); // return True
 

Constraints:

1 <= word.length <= 25
word in addWord consists of lowercase English letters.
word in search consist of '.' or lowercase English letters.
There will be at most 3 dots in word for search queries.
At most 104 calls will be made to addWord and search.
 */



// My Solution:
class WordDictionary {
    class TrieNode {
        public TrieNode[] children;
        public boolean is_word;
        public TrieNode() {
            children = new TrieNode[26];
            is_word = false;
        }
    }
    
    private TrieNode root;

    public WordDictionary() {
        root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode curNode = this.root;
        for (int i=0; i<word.length(); i++) {
            if (curNode.children[word.charAt(i) - 'a'] == null) curNode.children[word.charAt(i) - 'a'] = new TrieNode();
            curNode = curNode.children[word.charAt(i) - 'a'];
        }
        curNode.is_word = true;
    }
    
    public boolean search(String word) {
        return search(word, 0, this.root);
    }
    
    private boolean search(String word, int curIndex, TrieNode curNode) {
        if (curNode == null) return false;
        if (word.charAt(curIndex) == '.') {
            for (TrieNode nextNode : curNode.children) {
                if (curIndex == word.length() - 1) {
                    if (nextNode != null && nextNode.is_word) return true;
                } else {
                    if (search(word, curIndex+1, nextNode)) return true;
                }
            }
            return false;
        } else {
            TrieNode nextNode = curNode.children[word.charAt(curIndex) - 'a'];
            if (nextNode == null) return false;
            if (curIndex == word.length() - 1) return nextNode.is_word;
            else return search(word, curIndex+1, nextNode);
        }
    }
}
/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
