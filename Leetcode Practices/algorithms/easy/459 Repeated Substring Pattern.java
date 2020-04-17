/*
Given a non-empty string check if it can be constructed by taking a substring of it and appending multiple copies of the substring together. You may assume the given string consists of lowercase English letters only and its length will not exceed 10000.

 

Example 1:

Input: "abab"
Output: True
Explanation: It's the substring "ab" twice.
Example 2:

Input: "aba"
Output: False
Example 3:

Input: "abcabcabcabc"
Output: True
Explanation: It's the substring "abc" four times. (And the substring "abcabc" twice.)
*/



// My Solution after a bit inspired (KMP):
class Solution {
    public boolean repeatedSubstringPattern(String s) {
        int len = s.length();
        if (len <= 1) return false;
        
        // 本题使用 KMP 的一半，比如 Partial Match Table
        // 构建好上面的工具后遍历该 Table，若符合条件
        // (比如若为 AXX 的重复) 则必然 Table values 为 0,0,x,x,x,x,3,x,x,6,x,x,9,...
        int[] pmt = PMT(s);
        int subStrLen = len-pmt[len];
        if (len == subStrLen || len%subStrLen != 0) return false;
        
        for (int i=len; i>subStrLen+1; i-=subStrLen) {
            if (i == subStrLen*2) return pmt[i] == subStrLen;
            if (pmt[i]-pmt[i-subStrLen] != subStrLen) return false;
        }
        
        return true;
    }
    
    public static int[] PMT(String pat) {
        int pmt[] = new int[pat.length()+1];
        pmt[0] = 0;
        pmt[1] = 0;
        for (int i=1, j=0; i < pat.length(); i++) {
            while (j > 0 && pat.charAt(i) != pat.charAt(j)) j = pmt[j];
            if (pat.charAt(i) == pat.charAt(j)) j++;
            pmt[i+1] = j;
        }
        return pmt;
    }
}



// Other's Solution (KMP):
class Solution2 {
    public boolean repeatedSubstringPattern(String s) {
        int len = s.length();
        int[] pmt = PMT(s);
        
        // 若 pmt[len] > 0 && len%(len-pmt[len]) == 0 则必然 pmt[len-1] -> pmt[len-subStrLen],（subStrLen 即 len-pmt[len], 以下图解假设 subStrLen == 3）
        // 
        // 因为 PMT 属性，必有 pmt[0...len-subStrLen-1] == pmt[subStrLen...len-1],
        // o,o,o,o,o,o,o,o,o,o,o,o,x,x,x
        // x,x,x,o,o,o,o,o,o,o,o,o,o,o,o
        //
        // 则当然其子集 pmt[0...subStrLen-1] == pmt[subStrLen...2*subStrLen-1] 也成立,
        // o,o,o,x,x,x,x,x,x,x,x,x,x,x,x
        // x,x,x,o,o,o,x,x,x,x,x,x,x,x,x
        // 
        // 结合上面两个可得
        // x,x,x,o,o,o,x,x,x,x,x,x,x,x,x
        // x,x,x,x,x,x,o,o,o,x,x,x,x,x,x
        //
        // 同理可往后得
        // x,x,x,x,x,x,o,o,o,x,x,x,x,x,x
        // x,x,x,x,x,x,x,x,x,o,o,o,x,x,x
        //
        // 再往后得
        // x,x,x,x,x,x,x,x,x,o,o,o,x,x,x
        // x,x,x,x,x,x,x,x,x,x,x,x,o,o,o
        // 
        // 以上全部结合得（若 len%(len-pmt[len]) != 0，则以下的最后一行不能刚好匹配至 len-1）
        // o,o,o,x,x,x,x,x,x,x,x,x,x,x,x
        // x,x,x,o,o,o,x,x,x,x,x,x,x,x,x
        // x,x,x,x,x,x,o,o,o,x,x,x,x,x,x
        // x,x,x,x,x,x,x,x,x,o,o,o,x,x,x
        // x,x,x,x,x,x,x,x,x,x,x,x,o,o,o
        //
        // 若字符串加长还可以如上继续每 subStrLen 地往后推...
        //
        // 最终可得出结论：符合 Repeated Substring Pattern
        return pmt[len] > 0 && len%(len-pmt[len]) == 0;
    }
    
    public static int[] PMT(String pat) {
        int pmt[] = new int[pat.length()+1];
        pmt[0] = 0;
        pmt[1] = 0;
        for (int i=1, j=0; i < pat.length(); i++) {
            while (j > 0 && pat.charAt(i) != pat.charAt(j)) j = pmt[j];
            if (pat.charAt(i) == pat.charAt(j)) j++;
            pmt[i+1] = j;
        }
        return pmt;
    }
}
// https://www.youtube.com/watch?v=uKr9qIZMtzw&t=1040s