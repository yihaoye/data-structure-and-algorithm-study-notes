/*
Given two strings A and B, find the minimum number of times A has to be repeated such that B is a substring of it. If no such solution, return -1.

For example, with A = "abcd" and B = "cdabcdab".

Return 3, because by repeating A three times (“abcdabcdabcd”), B is a substring of it; and B is not a substring of A repeated two times ("abcdabcd").

Note:
The length of A and B will be between 1 and 10000.
*/



// My Solution:
class Solution {
    public int repeatedStringMatch(String A, String B) {
        if (A.indexOf(B) != -1) return 1;
        
        Set<Character> aCharSet = new HashSet<Character>();
        Set<Character> bCharSet = new HashSet<Character>();
        for(char c : A.toCharArray()) aCharSet.add(c);
        for(char c : B.toCharArray()) bCharSet.add(c);
        if (!aCharSet.equals(bCharSet)) return -1;
        
        int times = 1;
        String C = A;
        while (true) {
            C += A;
            times++;
            if (C.indexOf(B) != -1) break;
            if (C.length() >= B.length()*3) return -1;
        }
        return times;
    }
}