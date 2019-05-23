/*
A string S of lowercase letters is given. We want to partition this string into as many parts as possible so that each letter appears in at most one part, and return a list of integers representing the size of these parts.

Example 1:
Input: S = "ababcbacadefegdehijhklij"
Output: [9,7,8]
Explanation:
The partition is "ababcbaca", "defegde", "hijhklij".
This is a partition so that each letter appears in at most one part.
A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
Note:

S will have length in range [1, 500].
S will consist of lowercase letters ('a' to 'z') only.
*/



// My Solution:
class Solution {
    public List<Integer> partitionLabels(String S) {
        // greedy, split the shortest part (its containning letters only appear in this part)
        char[] cArr = S.toCharArray();
        List<Integer> res = new ArrayList<>();
        
        int tempPart = 0; // which part now
        int[] tempRank = {0, 0}; // current part rank
        for (int i=0; i < cArr.length; i++) {
            if (i > tempRank[1]) {
                res.add(tempRank[1] - tempRank[0] + 1);
                tempRank[0] = i;
                tempRank[1] = i;
            }
            int lli = lastLetterIndex(cArr[i], cArr);
            if (lli > tempRank[1]) tempRank[1] = lli;
        }
        res.add(tempRank[1] - tempRank[0] + 1);
        
        return res;
    }
    
    public int lastLetterIndex(char c, char[] cArr) {
        int len = cArr.length;
        for (int i=len-1; i >= 0; i--) {
            if (c == cArr[i]) return i;
        }
        return -1;
    }
}