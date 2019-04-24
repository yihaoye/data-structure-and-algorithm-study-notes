/*
Given two strings A and B, find the minimum number of times A has to be repeated such that B is a substring of it. If no such solution, return -1.

For example, with A = "abcd" and B = "cdabcdab".

Return 3, because by repeating A three times (“abcdabcdabcd”), B is a substring of it; and B is not a substring of A repeated two times ("abcdabcd").

Note:
The length of A and B will be between 1 and 10000.
*/



// Other's Solution:
/*
    first of all, we need as to be at least as long as b:
    a: "abc" "abc"
    b: "abc abc" - in this case we need 2 copies of a;

    a:"abc" "abc" "abc"
    b: "c abc ab" - in this case we need 3 copies of a

    We can tell we need at least 1 extra copy, b.length() / a.length() + 1 works fine when a.len / a.len == 0
    But:
    a: "abc" "abc" "abc"
    b: "c abc a" - in this case we still need 3 copies of a, but a.len / b.len (5/3) only give you 1
    That's why we want to add at least 2 copies.
*/
class Solution {
    public int repeatedStringMatch(String a, String b) {
        String as = a;
        for (int rep = 1; rep <= b.length() / a.length() + 2; rep++, as += a)
            if (as.indexOf(b) != -1) return rep;
        return -1;
    }
}



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