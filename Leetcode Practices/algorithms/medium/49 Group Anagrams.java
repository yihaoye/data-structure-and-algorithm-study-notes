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
    long mod = (long)1e9 + 7;

    public List<List<String>> groupAnagrams(String[] strs) {
        // hashcode + hashmap
        Map<Long, Integer> map = new HashMap<>(); // hashcode -> res index
        List<List<String>> res = new ArrayList<>();
        for (String str : strs) {
            long hashcode = hash(str);
            if (!map.containsKey(hashcode)) {
                res.add(new ArrayList<>());
                map.put(hashcode, res.size() - 1);
            }
            int index = map.get(hashcode);
            res.get(index).add(str);
        }
        return res;
    }

    public long hash(String str) {
        long code = 0;
        int[] map = new int[26];
        for (char c : str.toCharArray()) map[c - 'a']++;
        for (int d : map) code = (code * 31 % mod + d) % mod;
        return code;
    }
}



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