/*
Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.

Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than 20,100.

The order of output does not matter.

Example 1:

Input:
s: "cbaebabacd" p: "abc"

Output:
[0, 6]

Explanation:
The substring with start index = 0 is "cba", which is an anagram of "abc".
The substring with start index = 6 is "bac", which is an anagram of "abc".
Example 2:

Input:
s: "abab" p: "ab"

Output:
[0, 1, 2]

Explanation:
The substring with start index = 0 is "ab", which is an anagram of "ab".
The substring with start index = 1 is "ba", which is an anagram of "ab".
The substring with start index = 2 is "ab", which is an anagram of "ab".
*/



// My Solution: (Sliding Window solution, inspired from https://www.jianshu.com/p/869f6d00d962)
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        char[] sArr = s.toCharArray(), pArr = p.toCharArray();
        if (sArr.length < pArr.length) return res;
        
        Map<Character, Integer> map = new HashMap<>(), cMap = new HashMap<>();
        for (char c : pArr) {
            map.put(c, map.get(c) != null ? map.get(c)+1 : 1);
        }
        cMap.putAll(map);
        
        int len = sArr.length, begin = 0, end = 0, count = map.size();
        while (end < len) {
            if (cMap.get(sArr[end]) == null) {
                end++;
                begin = end;
                count = cMap.size();
                cMap.putAll(map);
            } else if (cMap.get(sArr[end]) == 0) {
                while (sArr[end] != sArr[begin]) {
                    if (cMap.get(sArr[begin]) == 0) count++;
                    cMap.put(sArr[begin], cMap.get(sArr[begin])+1);
                    begin++;
                }
                count++;
                cMap.put(sArr[begin], cMap.get(sArr[begin])+1);
                begin++;
            } else { 
                cMap.put(sArr[end], cMap.get(sArr[end])-1);
                if (cMap.get(sArr[end]) == 0) count--;
                if (count == 0) {
                    res.add(begin);
                    if (cMap.get(sArr[begin]) == 0) count++;
                    cMap.put(sArr[begin], cMap.get(sArr[begin])+1);
                    begin++;
                }
                end++;
            }
        }
        
        return res;
    }
}