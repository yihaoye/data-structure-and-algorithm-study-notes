/*
We are given two strings, A and B.

A shift on A consists of taking string A and moving the leftmost character to the rightmost position. For example, if A = 'abcde', then it will be 'bcdea' after one shift on A. Return True if and only if A can become B after some number of shifts on A.

Example 1:
Input: A = 'abcde', B = 'cdeab'
Output: true

Example 2:
Input: A = 'abcde', B = 'abced'
Output: false
Note:

A and B will have length at most 100.
*/



// My Solution (KMP, it is not efficient in this question but just for practice KMP):
class Solution {
    private int[][] dfa; // the KMP automoton
    private String pat;
    
    public boolean rotateString(String A, String B) {
        if (A.equals(B)) return true;
        if (A.equals("") || B.equals("") || A.length() != B.length()) return false;
        A += A;
        kmp(B);
        return kmpSearch(A);
    }
    
    public void kmp(String pat) {
        this.pat = pat;

        int R = 256;
        int m = pat.length();
        dfa = new int[R][m]; 
        dfa[pat.charAt(0)][0] = 1;
        for (int x = 0, j = 1; j < m; j++) {
            for (int c = 0; c < R; c++) dfa[c][j] = dfa[c][x];
            dfa[pat.charAt(j)][j] = j+1;
            x = dfa[pat.charAt(j)][x]; 
        } 
    } 

    public boolean kmpSearch(String txt) {
        int m = pat.length();
        int n = txt.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[txt.charAt(i)][j];
        }
        if (j == m) return true;
        return false;
    }
}