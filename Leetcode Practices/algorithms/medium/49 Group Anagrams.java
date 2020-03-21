/*
Given an array of strings, group anagrams together.

Example:

Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
Output:
[
  ["ate","eat","tea"],
  ["nat","tan"],
  ["bat"]
]
Note:

All inputs will be in lowercase.
The order of your output does not matter.
*/



// My Solution:
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<List<String>>();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strs) {
            String sortedStr = sortStr(str);
            if (map.containsKey(sortedStr)) map.get(sortedStr).add(str);
            else map.put(sortedStr, new ArrayList<String>(Arrays.asList(str)));
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            res.add(entry.getValue());
        }
        
        return res;
    }
    
    public String sortStr(String str) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        
        return new String(chars);
    }
}