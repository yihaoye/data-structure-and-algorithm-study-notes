/*
From any string, we can form a subsequence of that string by deleting some number of characters (possibly no deletions).

Given two strings source and target, return the minimum number of subsequences of source such that their concatenation equals target. If the task is impossible, return -1.

 

Example 1:

Input: source = "abc", target = "abcbc"
Output: 2
Explanation: The target "abcbc" can be formed by "abc" and "bc", which are subsequences of source "abc".
Example 2:

Input: source = "abc", target = "acdbc"
Output: -1
Explanation: The target string cannot be constructed from the subsequences of source string due to the character "d" in target string.
Example 3:

Input: source = "xyz", target = "xzyxz"
Output: 3
Explanation: The target string can be constructed as follows "xz" + "y" + "xz".
 

Constraints:

Both the source and target strings consist of only lowercase English letters from "a"-"z".
The lengths of source and target string are between 1 and 1000.
*/



// My Solution 1:
class Solution {
    public int shortestWay(String source, String target) {
        // reverse thinking: minimum times for complete subsequence to complete target = x*source, for example: abcbc => abcabc
        char[] sArr = source.toCharArray();
        int[] res = new int[]{0};
        int nextCheckIndex = 0;
        for (char targetChar : target.toCharArray()) {
            nextCheckIndex = findCharInSource(targetChar, nextCheckIndex, sArr, res);
            if (nextCheckIndex == -1) return -1;
        }
        if (nextCheckIndex != 0) res[0]++;
        
        return res[0];
    }
    
    public int findCharInSource(char targetChar, int startIndex, char[] sArr, int[] res) {
        for (int i=startIndex; i< sArr.length; i++) {
            if (sArr[i] == targetChar) {
                if (i == sArr.length-1) {
                    res[0]++;
                    return 0;
                }
                return i+1;
            }
        }
        if (startIndex == 0) return -1;
        res[0]++;
        for (int i=0; i< startIndex; i++) {
            if (sArr[i] == targetChar) return i+1;
        }
        return -1;
    }
}