/**
You are given a string s and an integer k. You can choose one of the first k letters of s and append it at the end of the string..

Return the lexicographically smallest string you could have after applying the mentioned step any number of moves.

 

Example 1:

Input: s = "cba", k = 1
Output: "acb"
Explanation: 
In the first move, we move the 1st character 'c' to the end, obtaining the string "bac".
In the second move, we move the 1st character 'b' to the end, obtaining the final result "acb".
Example 2:

Input: s = "baaca", k = 3
Output: "aaabc"
Explanation: 
In the first move, we move the 1st character 'b' to the end, obtaining the string "aacab".
In the second move, we move the 3rd character 'c' to the end, obtaining the final result "aaabc".
 

Constraints:

1 <= k <= s.length <= 1000
s consist of lowercase English letters.
 */



// Other's Solution:
class Solution {        
    public String orderlyQueue(String S, int K) {
        // Math - https://leetcode.com/problems/orderly-queue/solutions/165878/c-java-python-sort-string-or-rotate-string/
        // https://leetcode.com/problems/orderly-queue/solutions/165915/when-k-1-you-can-reorder-any-way-you-like-proof/
        if (K > 1) {
            char S2[] = S.toCharArray();
            Arrays.sort(S2);
            return new String(S2);
        }
        String res = S;
        for (int i = 1; i < S.length(); i++) {
            String tmp = S.substring(i) + S.substring(0, i);
            if (res.compareTo(tmp) > 0) res = tmp;
        }
        return res;
    }
}
