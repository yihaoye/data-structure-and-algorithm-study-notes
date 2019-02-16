// Question:
/*
    Write a function to find the longest common prefix string amongst an array of strings.

    If there is no common prefix, return an empty string "".

    Example 1:

    Input: ["flower","flow","flight"]
    Output: "fl"
    Example 2:

    Input: ["dog","racecar","car"]
    Output: ""
    Explanation: There is no common prefix among the input strings.
    Note:

    All given inputs are in lowercase letters a-z.
*/


// My Answer:
class Solution {
    public String longestCommonPrefix(String[] strs) {
        String result = "";
        int i = 0;
        if (strs.length <= 1) {
            return result;
        }
        char[][] charArrays = new char[strs.length][];
        for (String str : strs) {
            if (str == "") {
                return result;
            }
            charArrays[i] = str.toCharArray();
            i++;
        }
        int j = 0;
        while (true) {
            for (int k = 0; k < i-1; k++) {
                // if (charArrays[k].length < j || charArrays[k+1].length < j) {
                //     return result;
                // }
                if (charArrays[k][j] != charArrays[k+1][j]) {
                    return result;
                }
            }
            result += charArrays[0][j];
            j++;
        }
    }
}