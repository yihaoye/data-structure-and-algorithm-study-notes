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



// Other's Solution:
// 1. traverse the string record the last index of each char.
// 2. using pointer to record end of the current sub string.
// https://leetcode.cn/problems/partition-labels/solution/hua-fen-zi-mu-qu-jian-by-leetcode-solution/
class Solution {
    public List<Integer> partitionLabels(String s) {
        int[] last = new int[26];
        int length = s.length();
        for (int i=0; i<length; i++) last[s.charAt(i) - 'a'] = i;

        List<Integer> res = new ArrayList<Integer>();
        int start = 0, end = 0; // record the end index of the current sub string
        for (int i=0; i<length; i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (i == end) {
                res.add(end - start + 1);
                start = end + 1;
            }
        }
        return res;
    }
}



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



// My Solution 2:
class Solution {
    public List<Integer> partitionLabels(String s) {
        // 贪心 + 双指针（为每个出现的字符）
        // Time: O(N), Space: O(1)
        
        // 双指针：遍历第一遍构建一个链散列表，键为字符，值为该字符的最左端和最右端的索引（一个 2 元素数组即可，即代表该字符的 range），且因为链散列表按键的创建顺序，所以保证了每个键的左端索引按序递增（为后面的贪婪逻辑提供便利）
        Map<Character, int[]> charRangeMap = new LinkedHashMap<>();
        for (int i=0; i<s.length(); i++) {
            char curChar = s.charAt(i);
            if (charRangeMap.containsKey(curChar)) {
                int[] range = charRangeMap.get(curChar);
                range[1] = i; // reference update
            } else {
                int[] range = new int[]{i, i};
                charRangeMap.put(curChar, range);
            }
        }
        
        List<Integer> res = new ArrayList<>();
        // 贪婪：找出有重叠的区域，然后合并为一个区域，然后再找有没有重叠区域直到没有然后输出这一个区域，然后再找下一个
        int[] lastRange = null;
        for (int[] curRange : charRangeMap.values()) {
            if (lastRange == null) {
                lastRange = curRange;
                continue;
            }
            
            if (lastRange[1] < curRange[0]) {
                res.add(lastRange[1] - lastRange[0] + 1);
                lastRange = curRange;
            } else if (lastRange[1] < curRange[1]) {
                lastRange[1] = curRange[1]; // reference update
            }
        }
        if (lastRange != null) {
            res.add(lastRange[1] - lastRange[0] + 1);
        }
        
        return res;
    }
}
