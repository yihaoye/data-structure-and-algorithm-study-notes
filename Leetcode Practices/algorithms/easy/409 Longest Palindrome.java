//Question:
/*
Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes that can be built with those letters.

This is case sensitive, for example "Aa" is not considered a palindrome here.

Note:
Assume the length of given string will not exceed 1,010.

Example:

Input:
"abccccdd"

Output:
7

Explanation:
One longest palindrome that can be built is "dccaccd", whose length is 7.
*/





//My Solution:
public class Solution {
    public int longestPalindrome(String s) {
        char[] cArray = s.toCharArray();
        int MaxOdd=0;
        int lonPal=0;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for(int i=0; i<cArray.length; i++){
            int value = map.containsKey(cArray[i]) ? map.get(cArray[i]) : 0;
            map.put(cArray[i], value+1);
        }
        
        for (Integer value : map.values()) {
            System.out.println(value);
            if(value%2==0){
                lonPal+=value;
            }else{
                if(MaxOdd<=value){
                    if(MaxOdd>2)
                        lonPal+=MaxOdd-1;
                    MaxOdd=value;
                }else if(value>2){
                    lonPal+=value-1;
                }
                //MaxOdd = (MaxOdd<=value) ? value : MaxOdd;
            }
        }
        lonPal+=MaxOdd;
        
        return lonPal;
    }
}