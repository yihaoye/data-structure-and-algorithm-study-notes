/*
The DNA sequence is composed of a series of nucleotides abbreviated as 'A', 'C', 'G', and 'T'.

For example, "ACGAATTCCG" is a DNA sequence.
When studying DNA, it is useful to identify repeated sequences within the DNA.

Given a string s that represents a DNA sequence, return all the 10-letter-long sequences (substrings) that occur more than once in a DNA molecule. You may return the answer in any order.

 

Example 1:

Input: s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
Output: ["AAAAACCCCC","CCCCCAAAAA"]
Example 2:

Input: s = "AAAAAAAAAAAAA"
Output: ["AAAAAAAAAA"]
 

Constraints:

1 <= s.length <= 105
s[i] is either 'A', 'C', 'G', or 'T'.
*/




// Other's Solution:
class Solution {
    /*
        滑动窗口+哈希表+位运算：https://leetcode-cn.com/problems/repeated-dna-sequences/solution/zhong-fu-de-dnaxu-lie-by-leetcode-soluti-z8zn/
        follow up：滚动哈希
        时间复杂度 O(N)，空间复杂度 O(N)
    */
    static final int L = 10; // follow algorithm allow range [0, 16] since integer binary length is 32

    Map<Character, Integer> bin = new HashMap<Character, Integer>() {{
        put('A', 0);
        put('C', 1);
        put('G', 2);
        put('T', 3);
    }};

    public List<String> findRepeatedDnaSequences(String s) {
        List<String> res = new ArrayList<String>();
        int n = s.length();
        if (n <= L) return res;
        int subStr = 0;
        for (int i=0; i<L-1; ++i) subStr = (subStr << 2) | bin.get(s.charAt(i)); // generate first 9 chars' binary

        Map<Integer, Integer> cnt = new HashMap<Integer, Integer>();
        for (int i=0; i<=n-L; ++i) {
            subStr = ((subStr << 2) | bin.get(s.charAt(i+L-1))) & ((1 << (L*2)) - 1); // 通过位运算位移在 O(1) 内获取下一个 10 位字符串的自定义 hashCode
            cnt.put(subStr, cnt.getOrDefault(subStr, 0) + 1);
            if (cnt.get(subStr) == 2) res.add(s.substring(i, i+L));
        }
        return res;
    }
}
