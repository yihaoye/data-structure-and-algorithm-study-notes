// Question:
/*
Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.

Example 1:

Input: "babad"
Output: "bab"
Note: "aba" is also a valid answer.
Example 2:

Input: "cbbd"
Output: "bb"
*/


// 马拉车算法 O(N) by ChatGPT
class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        // 预处理字符串，插入特殊字符
        StringBuilder modifiedString = new StringBuilder("#");
        for (char c : s.toCharArray()) {
            modifiedString.append(c).append("#");
        }

        int n = modifiedString.length();
        int[] radius = new int[n];  // 记录以每个位置为中心的回文串半径

        int center = 0;  // 当前处理过程中最右的回文串的中心
        int rightBoundary = 0;  // 当前处理过程中最右的回文串的右边界

        int maxRadius = 0;  // 最长回文串半径
        int maxCenter = 0;  // 最长回文串中心位置

        for (int i = 0; i < n; i++) {
            if (i < rightBoundary) {
                // 利用已知回文串信息，避免重复计算
                int mirror = 2 * center - i; // i 关于 center 的对称点
                radius[i] = Math.min(rightBoundary - i, radius[mirror]); // 防止超出最右边界，取最小值，避免重复计算，取已知回文串的半径
            }

            // 尝试扩展回文串
            int left = i - (radius[i] + 1);
            int right = i + (radius[i] + 1);

            while (left >= 0 && right < n && modifiedString.charAt(left) == modifiedString.charAt(right)) { // 这里的判断条件意思是：如果左边界和右边界没有超出字符串范围，并且左右边界的字符相等，那么就可以继续扩展回文串
                radius[i]++;
                left--;
                right++;
            }

            // 如果当前回文串的右边界超过了最右边界，那么就更新最右边界和中心位置
            if (i + radius[i] > rightBoundary) {
                center = i;
                rightBoundary = i + radius[i];
            }

            // 如果当前回文串的半径大于最长回文串的半径，那么就更新最长回文串的半径和中心位置
            if (radius[i] > maxRadius) {
                maxRadius = radius[i];
                maxCenter = i;
            }
        }

        // 从原始字符串中还原最长回文串
        int start = (maxCenter - maxRadius) / 2;
        int end = start + maxRadius;
        return s.substring(start, end);
    }
}


// Other's Solution:
public class Solution {
    private int lo, maxLen;
    
    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2)
            return s;
        
        for (int i = 0; i < len-1; i++) {
             extendPalindrome(s, i, i);  //assume odd length, try to extend Palindrome as possible
             extendPalindrome(s, i, i+1); //assume even length.
        }
        return s.substring(lo, lo + maxLen);
    }
    
    private void extendPalindrome(String s, int j, int k) {
        while (j >= 0 && k < s.length() && s.charAt(j) == s.charAt(k)) {
            j--;
            k++;
        }
        if (maxLen < k - j - 1) {
            lo = j + 1;
            maxLen = k - j - 1;
        }
    }
}


// My Solution:
class Solution {
    public String longestPalindrome(String s) {
        if (isPalindrome(s)) {
            return s;
        } else {
            String s1 = longestPalindrome(s.substring(0, s.length()-1));
            String s2 = longestPalindrome(s.substring(1, s.length()));
            return (s1.length() >= s2.length()) ? s1 : s2;
        }
    }
    
    public Boolean isPalindrome(String str) {
        if (str.length() <= 1) {
        } else if (str.charAt(0) == str.charAt(str.length()-1)) {
            if (str.length() == 2) {
                return true;
            }
            str = str.substring(1, str.length()-1);
            return isPalindrome(str);
        } else {
            return false;
        }
        return true;
    }
}



// My Solution 2:
class Solution {
    public String longestPalindrome(String s) {
        // dp - dp[l][r] is true if [l..r] is valid palindrome
        int n = s.length(), maxStart = 0, maxEnd = 0;
        boolean[][] dp = new boolean[n][n];
        for (int r=0; r<n; r++) {
            dp[r][r] = true;
            for (int l=1; l<=r; l++) {
                if ((dp[l][r-1] || l == r) && s.charAt(l-1) == s.charAt(r)) { // l == r && s[r-1] == s[r] -> [r-1..r] = true
                    dp[l-1][r] = true;
                    if (r - l + 1 > maxEnd - maxStart) {
                        maxStart = l - 1;
                        maxEnd = r;
                    }
                }
            }
        }

        return s.substring(maxStart, maxEnd + 1);
    }
}
