/*
Given a string s, partition s such that every substring of the partition is a palindrome. Return all possible palindrome partitioning of s.

A palindrome string is a string that reads the same backward as forward.

 

Example 1:

Input: s = "aab"
Output: [["a","a","b"],["aa","b"]]
Example 2:

Input: s = "a"
Output: [["a"]]
 

Constraints:

1 <= s.length <= 16
s contains only lowercase English letters.
*/



// My Solution (dfs):
class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        tempList.add(String.valueOf(s.charAt(0))); // 1 <= s.length, no need to worry it is empty
        dfs(res, tempList, s, 1); // 2nd param tempList - every element in the list should be palindrome except the last element
        
        return res;
    }
    
    public void dfs(List<List<String>> res, List<String> tempList, String s, int startIndex) {
        if (startIndex == s.length()) {
            if (isPalindrome(tempList.get(tempList.size() - 1))) {
                res.add(new ArrayList<>(tempList));
            }
            return;
        }
        String tempListLast = tempList.get(tempList.size() - 1);
        tempList.set(tempList.size() - 1, tempListLast + s.charAt(startIndex));
        dfs(res, tempList, s, startIndex + 1);
        tempList.set(tempList.size() - 1, tempListLast);
        if (!isPalindrome(tempList.get(tempList.size() - 1))) return;
        tempList.add(String.valueOf(s.charAt(startIndex)));
        dfs(res, tempList, s, startIndex + 1);
        tempList.remove(tempList.size() - 1);
    }
    
    public boolean isPalindrome(String str) {
        StringBuffer sbr = new StringBuffer(str);
        return str.equals(sbr.reverse().toString());
    }
}



// Other's Solution:
class Solution {
    public List<List<String>> partition(String s) {
       List<List<String>> list = new ArrayList<>();
       backtrack(list, new ArrayList<>(), s, 0);
       return list;
    }

    public void backtrack(List<List<String>> list, List<String> tempList, String s, int start){
       if(start == s.length())
          list.add(new ArrayList<>(tempList));
       else{
          for(int i = start; i < s.length(); i++){
             if(isPalindrome(s, start, i)){
                tempList.add(s.substring(start, i + 1));
                backtrack(list, tempList, s, i + 1);
                tempList.remove(tempList.size() - 1);
             }
          }
       }
    }

    public boolean isPalindrome(String s, int low, int high){
       while(low < high)
          if(s.charAt(low++) != s.charAt(high--)) return false;
       return true;
    } 
}
