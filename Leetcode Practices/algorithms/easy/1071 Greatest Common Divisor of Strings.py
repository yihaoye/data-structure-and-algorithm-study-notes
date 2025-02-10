"""
For two strings s and t, we say "t divides s" if and only if s = t + t + t + ... + t + t (i.e., t is concatenated with itself one or more times).

Given two strings str1 and str2, return the largest string x such that x divides both str1 and str2.

 

Example 1:

Input: str1 = "ABCABC", str2 = "ABC"
Output: "ABC"
Example 2:

Input: str1 = "ABABAB", str2 = "ABAB"
Output: "AB"
Example 3:

Input: str1 = "LEET", str2 = "CODE"
Output: ""
 

Constraints:

1 <= str1.length, str2.length <= 1000
str1 and str2 consist of English uppercase letters.
"""



from math import gcd

class Solution:
    def gcdOfStrings(self, str1: str, str2: str) -> str:
        # 数学解 by ChatGPT - Time: O(log(min(a, b))), Space: O(1)
        # 如果不相等，说明 str1 和 str2 没有相同的“整除”子串，即没有公共的最大公约数字符串，返回空字符串
        # 假设 str1 和 str2 可以通过一个相同的子串 X 来重复生成，那么 str1、str2 都是若干个 X 的重复
        # 由于 X 的长度是相同的，str1 和 str2 也应该具有相同的重复模式
        # 因此，如果 str1 和 str2 有公共的子串 X，那么无论将 str1 放在前面还是将 str2 放在前面，它们的拼接应该是相同的
        if str1 + str2 != str2 + str1:
            return ""
        # 用 gcd 函数计算公共子串的最大长度
        # 如果 str1 和 str2 都可以由一个子串 X 重复构成，那么 X 的长度必须是 str1 和 str2 的长度的最大公约数
        # 换句话说，X 的长度必须能同时整除这两个字符串的长度，因此公约数是一定的，但是为什么也能保证最大公约数一定是 X 的重复（也可以是本身即重复 0 次）
        # 因为最大公约数总是可以整除任意公约数，所以可得结论
        return str1[:gcd(len(str1), len(str2))]
