/*
Write an algorithm to determine if a number n is "happy".

A happy number is a number defined by the following process: Starting with any positive integer, replace the number by the sum of the squares of its digits, and repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1. Those numbers for which this process ends in 1 are happy numbers.

Return True if n is a happy number, and False if not.

Example: 

Input: 19
Output: true
Explanation: 
12 + 92 = 82
82 + 22 = 68
62 + 82 = 100
12 + 02 + 02 = 1
*/



// My Solution (inspired by topic/tag):
class Solution {
    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        set.add(n);
        while (true) {
            if (n == 1) return true;
            int i = fun(n);
            if (set.contains(i)) return false;
            set.add(i);
            n = i;
        }
    }
    
    public int fun(int n) {
        int res = 0;
        while (n > 0) {
            int i = n%10;
            res += i*i;
            n = n/10;
        }
        return res;
    }
}