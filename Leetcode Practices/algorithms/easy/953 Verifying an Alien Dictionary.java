/*
In an alien language, surprisingly they also use english lowercase letters, but possibly in a different order. The order of the alphabet is some permutation of lowercase letters.

Given a sequence of words written in the alien language, and the order of the alphabet, return true if and only if the given words are sorted lexicographicaly in this alien language.

 

Example 1:

Input: words = ["hello","leetcode"], order = "hlabcdefgijkmnopqrstuvwxyz"
Output: true
Explanation: As 'h' comes before 'l' in this language, then the sequence is sorted.
Example 2:

Input: words = ["word","world","row"], order = "worldabcefghijkmnpqstuvxyz"
Output: false
Explanation: As 'd' comes after 'l' in this language, then words[0] > words[1], hence the sequence is unsorted.
Example 3:

Input: words = ["apple","app"], order = "abcdefghijklmnopqrstuvwxyz"
Output: false
Explanation: The first three characters "app" match, and the second string is shorter (in size.) According to lexicographical rules "apple" > "app", because 'l' > '∅', where '∅' is defined as the blank character which is less than any other character (More info).
 

Constraints:

1 <= words.length <= 100
1 <= words[i].length <= 20
order.length == 26
All characters in words[i] and order are English lowercase letters.
*/



// My Solution:
class Solution {
    public boolean isAlienSorted(String[] words, String order) {
        int i = 0;
        int[][] indexsArr = new int[words.length][20];
        for (String word : words) {
            toIndexs(word, order, indexsArr, i);
            i++;
        }
        for (int j=0; j<i-1; j++) {
            if (!compareIndexs(indexsArr[j], indexsArr[j+1])) return false;
        }
        return true;
    }
    
    public void toIndexs(String word, String order, int[][] indexsArr, int i) {
        char[] wordArr = word.toCharArray();
        for (int j=0; j<wordArr.length; j++) {
            indexsArr[i][j] = order.indexOf(wordArr[j]) + 1;
        }
    }
    
    public boolean compareIndexs(int[] indexsA, int[] indexsB) {
        int i = 0;
        while (indexsA[i] != 0 && indexsB[i] != 0 && i<20) {
            if (indexsA[i] < indexsB[i]) return true;
            if (indexsA[i] > indexsB[i]) return false;
            i++;
        }
        if (i == 20 || indexsA[i] == indexsB[i] || indexsB[i] != 0) return true;
        else return false;
    }
}



// Other's Solution: (Complexity - Time O(NS) Space O(1))
class Solution {
    int[] mapping = new int[26];
    public boolean isAlienSorted(String[] words, String order) {
        for (int i = 0; i < order.length(); i++)
            mapping[order.charAt(i) - 'a'] = i;
        for (int i = 1; i < words.length; i++)
            if (bigger(words[i - 1], words[i]))
                return false;
        return true;
    }

    boolean bigger(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        for (int i = 0; i < n && i < m; ++i)
            if (s1.charAt(i) != s2.charAt(i))
                return mapping[s1.charAt(i) - 'a'] > mapping[s2.charAt(i) - 'a'];
        return n > m;
    }
}
/*
Build a transform mapping from order,
Find all alien words with letters in normal order.

For example, if we have order = "xyz..."
We can map the word "xyz" to "abc" or "123"

Then we check if all words are in sorted order.
*/