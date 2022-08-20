/**
Given two strings s and p, return an array of all the start indices of p's anagrams in s. You may return the answer in any order.

An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

 

Example 1:

Input: s = "cbaebabacd", p = "abc"
Output: [0,6]
Explanation:
The substring with start index = 0 is "cba", which is an anagram of "abc".
The substring with start index = 6 is "bac", which is an anagram of "abc".
Example 2:

Input: s = "abab", p = "ab"
Output: [0,1,2]
Explanation:
The substring with start index = 0 is "ab", which is an anagram of "ab".
The substring with start index = 1 is "ba", which is an anagram of "ab".
The substring with start index = 2 is "ab", which is an anagram of "ab".
 

Constraints:

1 <= s.length, p.length <= 3 * 104
s and p consist of lowercase English letters.
 */



// Other's Solution:
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        /*
            双指针+哈希
            Time: O(N), Space: O(1)
        */
        List<Integer> list = new ArrayList<>();
        if (s == null || s.length() == 0 || p == null || p.length() == 0) return list;
        int[] hash = new int[256]; // character hash
        for (char c : p.toCharArray()) hash[c]++;

        int left = 0, right = 0, count = p.length();
        while (right < s.length()) {
            // move right everytime, if the character exists in p's hash, decrease the count
            // current hash value >= 1 means the character is existing in p
            if (hash[s.charAt(right++)]-- >= 1) count--;

            // when the count is down to 0, means we found the right anagram
            // then add window's left to result list
            if (count == 0) list.add(left);

            // if we find the window's size equals to p, then we have to move left (narrow the window) to find the new match window
            // ++ to reset the hash because we kicked out the left
            // only increase the count if the character is in p
            // the count >= 0 indicate it was original in the hash, cuz it won't go below 0
            if (right - left == p.length() && hash[s.charAt(left++)]++ >= 0) count++;
        }
        return list;
    }
}



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



// My Solution 2: (TLE)
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        // 滑动窗口（定长双指针）+ 哈希表
        List<Integer> res = new ArrayList<>();
        if (p.length() > s.length()) return res;

        Map<Character, Integer> cMap = new HashMap<>();
        for (char c : p.toCharArray()) {
            cMap.put(c, cMap.getOrDefault(c, 0) + 1);
        }

        int left = 0, right = p.length() - 1;
        while (right < s.length()) {
            left = process(s, left, right, cMap, res);
            right = left + p.length() - 1;
        }

        return res;
    }

    public int process(String s, int left, int right, Map<Character, Integer> cMap, List<Integer> res) {
        Map<Character, List<Integer>> visitedMap = new HashMap<>();
        for (int i=left; i<=right; i++) {
            if (!cMap.containsKey(s.charAt(i))) {
                putBackMap(cMap, visitedMap);
                return i + 1;
            }

            int cCount = cMap.getOrDefault(s.charAt(i), 0);
            if (cCount == 0) {
                putBackMap(cMap, visitedMap);
                return visitedMap.get(s.charAt(i)).get(0) + 1; // find first s.charAt(i) index
            }
            
            cMap.put(s.charAt(i), cCount - 1);
            List<Integer> charIdxs = visitedMap.getOrDefault(s.charAt(i), new ArrayList<>());
            charIdxs.add(i);
            visitedMap.put(s.charAt(i), charIdxs);
        }
        res.add(left);
        putBackMap(cMap, visitedMap);

        return left + 1;
    }
    
    public void putBackMap(Map<Character, Integer> cMap, Map<Character, List<Integer>> visitedMap) {
        for (Map.Entry<Character, List<Integer>> entry : visitedMap.entrySet()) { // put back all
            cMap.put(entry.getKey(), cMap.get(entry.getKey()) + entry.getValue().size());
        }
        return;
    }
}
