/*
You're given strings J representing the types of stones that are jewels, and S representing the stones you have.  Each character in S is a type of stone you have.  You want to know how many of the stones you have are also jewels.

The letters in J are guaranteed distinct, and all characters in J and S are letters. Letters are case sensitive, so "a" is considered a different type of stone from "A".

Example 1:

Input: J = "aA", S = "aAAbbbb"
Output: 3
Example 2:

Input: J = "z", S = "ZZ"
Output: 0
Note:

S and J will consist of letters and have length at most 50.
The characters in J are distinct.
*/



// My Solution:
class Solution {
    public int numJewelsInStones(String J, String S) {
        Map<Character, Integer> jewelsMap = new HashMap<>();
        for (char j : J.toCharArray()) {
            jewelsMap.put(j, 0);
        }
        for (char s : S.toCharArray()) {
            if (jewelsMap.containsKey(s)) jewelsMap.put(s, jewelsMap.get(s) + 1);
        }
        int res = 0;
        for (Map.Entry<Character, Integer> entry : jewelsMap.entrySet()) {
            res += entry.getValue();
        }
        
        return res;
    }
}