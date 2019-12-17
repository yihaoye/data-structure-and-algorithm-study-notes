/*
Let's define a function f(s) over a non-empty string s, which calculates the frequency of the smallest character in s. For example, if s = "dcce" then f(s) = 2 because the smallest character is "c" and its frequency is 2.

Now, given string arrays queries and words, return an integer array answer, where each answer[i] is the number of words such that f(queries[i]) < f(W), where W is a word in words.

 

Example 1:

Input: queries = ["cbd"], words = ["zaaaz"]
Output: [1]
Explanation: On the first query we have f("cbd") = 1, f("zaaaz") = 3 so f("cbd") < f("zaaaz").
Example 2:

Input: queries = ["bbb","cc"], words = ["a","aa","aaa","aaaa"]
Output: [1,2]
Explanation: On the first query only f("bbb") < f("aaaa"). On the second query both f("aaa") and f("aaaa") are both > f("cc").
 

Constraints:

1 <= queries.length <= 2000
1 <= words.length <= 2000
1 <= queries[i].length, words[i].length <= 10
queries[i][j], words[i][j] are English lowercase letters.
*/



// My Solution 1:
class Solution {
    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        int[] answer = new int[queries.length];
        int[] qs = new int[queries.length];
        int[] ws = new int[words.length];
        qs = convertToIntArr(queries);
        ws = convertToIntArr(words);
        java.util.Arrays.sort(ws);
        for (int i=0; i<queries.length; i++) {
            answer[i] = calAnsweri(qs[i], ws);
        }
        return answer;
    }
    
    public int calAnsweri(int q, int[] ws) {
        int answeri;
        for (answeri=0; answeri<ws.length; answeri++) {
            if (q < ws[answeri]) break;
        }
        return ws.length-answeri;
    }
    
    public int[] convertToIntArr(String[] strArr) {
        int[] intArr = new int[strArr.length];
        for (int i=0; i<strArr.length; i++) {
            intArr[i] = f(strArr[i]);
        }
        return intArr;
    }
    
    public int f(String str) {
        char[] cArr = str.toCharArray();
		java.util.Arrays.sort(cArr);
        int sum = 0;
        for (Character c : cArr) {
            if (c != cArr[0]) return sum;
            sum++;
        }
        return sum;
    }
}