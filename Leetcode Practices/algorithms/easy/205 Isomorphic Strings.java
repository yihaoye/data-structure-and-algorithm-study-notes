/*
Given two strings s and t, determine if they are isomorphic.

Two strings are isomorphic if the characters in s can be replaced to get t.

All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character but a character may map to itself.

Example 1:

Input: s = "egg", t = "add"
Output: true
Example 2:

Input: s = "foo", t = "bar"
Output: false
Example 3:

Input: s = "paper", t = "title"
Output: true
Note:
You may assume both s and t have the same length.
*/



// My Solution:
class Solution {
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) return false;
        Map<Character, List<Integer>> sMap = setMap(s);
        Map<Character, List<Integer>> tMap = setMap(t);
        Iterator sIt = sMap.entrySet().iterator();
        Iterator tIt = tMap.entrySet().iterator();
        while (sIt.hasNext()) {
            Map.Entry sPair = (Map.Entry)sIt.next();
            Map.Entry tPair = (Map.Entry)tIt.next();
            if (!sPair.getValue().equals(tPair.getValue())) return false;
        }
        return true;
    }
    
    public Map<Character, List<Integer>> setMap(String str) {
        Map<Character, List<Integer>> map = new LinkedHashMap<Character, List<Integer>>();
        char[] cArray = str.toCharArray();
        for (int i=0; i<cArray.length; i++) {
            if (map.containsKey(cArray[i])) map.get(cArray[i]).add(i);
            else map.put(cArray[i], new ArrayList<Integer>(Arrays.asList(i)));
        }
        
        return map;
    }
}



// Other's Solution:
class Solution {
    public boolean isIsomorphic(String s, String t) {
        /*
            哈希表 - https://leetcode-cn.com/problems/isomorphic-strings/solution/tong-gou-zi-fu-chuan-by-leetcode-solutio-s6fd/
            时间复杂度 O(N)，空间复杂度 O(M)
        */
        Map<Character, Character> s2t = new HashMap<Character, Character>();
        Map<Character, Character> t2s = new HashMap<Character, Character>();
        for (int i=0; i<s.length(); i++) {
            char sChar = s.charAt(i), tChar = t.charAt(i);
            if ((s2t.containsKey(sChar) && s2t.get(sChar) != tChar) || (t2s.containsKey(tChar) && t2s.get(tChar) != sChar)) {
                return false;
            }
            s2t.put(sChar, tChar);
            t2s.put(tChar, sChar);
        }
        return true;
    }
}
