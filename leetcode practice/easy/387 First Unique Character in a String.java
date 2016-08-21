//Question:
/*
Given a string, find the first non-repeating character in it and return it's index. If it doesn't exist, return -1.

Examples:

s = "leetcode"
return 0.

s = "loveleetcode",
return 2.

Note: You may assume the string contain only lowercase letters. 
*/





//My Answer:
public class Solution {
    public int firstUniqChar(String s) {
        LinkedHashMap<Character, Integer> map = new LinkedHashMap<Character, Integer>();
        char[] charArray = s.toCharArray();

        for(char key : charArray){
            if(map.containsKey(key)){
                map.put(key, map.get(key) + 1);
            }else{
                map.put(key, 1);
            }
        }
        
        for (Character key : map.keySet()) {
            if(map.get(key)==1){
                for(int i=0; i<charArray.length; i++){
                    if(charArray[i] == key){
                        return i;
                    }
                }
            }
        }
        
        return -1;
    }
}