/*
Given a string s, you are allowed to convert it to a palindrome by adding characters in front of it. Find and return the shortest palindrome you can find by performing this transformation.

Example 1:

Input: "aacecaaa"
Output: "aaacecaaa"
Example 2:

Input: "abcd"
Output: "dcbabcd"
*/



// Other's Solution:
class Solution {
    public String shortestPalindrome(String s) {
        // KMP - https://leetcode.com/problems/shortest-palindrome/discuss/60113/Clean-KMP-solution-with-super-detailed-explanation
        String temp = s + "#" + new StringBuilder(s).reverse().toString();
        int[] table = getTable(temp);

        return new StringBuilder(s.substring(table[table.length - 1])).reverse().toString() + s; // get the maximum palin part in s starts from 0
    }

    public int[] getTable(String s) {
        int[] table = new int[s.length()]; // get lookup table - PMT

        // pointer that points to matched char in prefix part

        int index = 0;
        // skip index 0, we will not match a string with itself
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(index) == s.charAt(i)) {
                // we can extend match in prefix and postfix
                table[i] = table[i-1] + 1;
                index++;
            } else {
                // match failed, we try to match a shorter substring

                // by assigning index to table[i-1], we will shorten the match string length, and jump to the 
                // prefix part that we used to match postfix ended at i - 1
                index = table[i-1];

                while (index > 0 && s.charAt(index) != s.charAt(i)) {
                    // we will try to shorten the match string length until we revert to the beginning of match (index 1)
                    index = table[index-1];
                }

                // when we are here may either found a match char or we reach the boundary and still no luck
                // so we need check char match
                if (s.charAt(index) == s.charAt(i)) {
                    // if match, then extend one char 
                    index++;
                }
                table[i] = index;
            }
        }
        return table;
    }
}



// Other's Solution:
public class Solution {
    public String shortestPalindrome(String s) {
        if(s.length()<=1) return s;
        String new_s = s+"#"+new StringBuilder(s).reverse().toString();
        int[] position = new int[new_s.length()];
        
        for(int i=1;i<position.length;i++)
        {
            int pre_pos = position[i-1];
            while(pre_pos>0 && new_s.charAt(pre_pos)!=new_s.charAt(i))
                pre_pos = position[pre_pos-1];
            position[i] = pre_pos+((new_s.charAt(pre_pos)==new_s.charAt(i))?1:0);
        }
        
        return new StringBuilder(s.substring(position[position.length-1])).reverse().toString()+s;
    }
}
// https://leetcode.com/problems/shortest-palindrome/discuss/60216/A-KMP-based-Java-solution-with-explanation



// My Solution (KMP - DFA):
class Solution {
    private int[][] dfa; // the KMP automoton
    private String pat;
    
    public String shortestPalindrome(String s) {
        int len = s.length();
        if (len <= 1 || isPalindrome(s)) return s; // add isPalindrome(s) to improve performance for edge case like long string with same char or already palindrome itself
        String txt = getPalindrome(s);
        String res = "";
        kmp(s);
        while (txt.length() >= len) {
            if(kmpSearch(txt)) res = txt;
            // get palindrome shorter than last palindrome
            int subIndex = 1;
            while (subIndex < s.length() && s.charAt(subIndex) == s.charAt(subIndex-1)) subIndex++;
            s = s.substring(subIndex);
            txt = getPalindrome(s);
        }
        
        return res;
    }
    
    public boolean isPalindrome(String s) {
        StringBuilder strB = new StringBuilder(s);
        String reverseS = strB.reverse().toString();
        return s.equals(reverseS);
    }
    
    public String getPalindrome(String s) {
        int subIndex = 1;
        while (subIndex < s.length() && s.charAt(subIndex) == s.charAt(subIndex-1)) subIndex++;
        StringBuilder strB = new StringBuilder(s.substring(subIndex));
        strB.reverse().append(s);
        return strB.toString();
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



// My Solution (Time Limit Exceeded):
class Solution {
    public String shortestPalindrome(String s) {
        // 双指针
        int left = 0, right = s.length()-1;
        StringBuilder res = new StringBuilder();
        for (; right >= left; right--) {
            if (isPalindrome(s, left, right)) {
                for (int i=s.length()-1; i>right; i--) {
                    res.append(s.charAt(i));
                }
                res.append(s);
                return res.toString();
            }
        }
        return res.toString();
    }
    
    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }
}
