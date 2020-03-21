/*
Given a pattern and a string str, find if str follows the same pattern.

Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in str.

Example 1:

Input: pattern = "abba", str = "dog cat cat dog"
Output: true
Example 2:

Input:pattern = "abba", str = "dog cat cat fish"
Output: false
Example 3:

Input: pattern = "aaaa", str = "dog cat cat dog"
Output: false
Example 4:

Input: pattern = "abba", str = "dog dog dog dog"
Output: false
Notes:
You may assume pattern contains only lowercase letters, and str contains lowercase letters that may be separated by a single space.
*/



// My Solution:
class Solution {
    public boolean wordPattern(String pattern, String str) {
        String[] strArray = str.split(" ");
        char[] pArray = pattern.toCharArray();
        if (strArray.length != pArray.length) return false;
        Map<Character, String> map = new HashMap<Character, String>();
        for (int i=0; i<strArray.length; i++) {
            if (map.containsKey(pArray[i])) {
                if (!map.get(pArray[i]).equals(strArray[i])) return false;
            } else if (map.containsValue(strArray[i])) {
                return false;
            } else {
                map.put(pArray[i], strArray[i]);
            }
        }
        return true;
    }
}