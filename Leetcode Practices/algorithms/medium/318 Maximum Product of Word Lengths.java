/*
Given a string array words, return the maximum value of length(word[i]) * length(word[j]) where the two words do not share common letters. If no such two words exist, return 0.

 

Example 1:

Input: words = ["abcw","baz","foo","bar","xtfn","abcdef"]
Output: 16
Explanation: The two words can be "abcw", "xtfn".
Example 2:

Input: words = ["a","ab","abc","d","cd","bcd","abcd"]
Output: 4
Explanation: The two words can be "ab", "cd".
Example 3:

Input: words = ["a","aa","aaa","aaaa"]
Output: 0
Explanation: No such pair of words.
 

Constraints:

2 <= words.length <= 1000
1 <= words[i].length <= 1000
words[i] consists only of lowercase English letters.

*/



// My Solution:
class Solution {
    public int maxProduct(String[] words) {
        /*
            思路 - 遍历数组，将每个 word 的字符提取，然后存入 HashSet 中以和其他对比是否重复，但因为字符限定为小写英文字母，所以可以用一个 26 位二进制存储：比如存在 a 则将最左第一位设为 1，b 设第二位如此类推，当对比两个 word 有没有重复字符时只需将双方的二进制数相与即可，若没有重复则为 0
            时间复杂度 O(N^2)，空间复杂度 O(N)
        */
        int res = 0;
        int[] binArr = new int[words.length];
        for (int i=0; i<words.length; i++) {
            binArr[i] = convertToBinary(words[i]);
        }
        
        for (int i=0; i<words.length-1; i++) {
            for (int j=i+1; j<words.length; j++) {
                if ((binArr[i] & binArr[j]) == 0) {
                    res = Math.max(res, words[i].length() * words[j].length());
                }
            }
        }
        
        return res;
    }
    
    public int convertToBinary(String word) {
        int res = 0;
        for (char c : word.toCharArray()) {
            int tmp = 1 << (c-'a');
            res = res | tmp;
        }
        return res;
    }
}
