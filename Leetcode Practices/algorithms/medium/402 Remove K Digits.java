/**
Given string num representing a non-negative integer num, and an integer k, return the smallest possible integer after removing k digits from num.

 

Example 1:

Input: num = "1432219", k = 3
Output: "1219"
Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.
Example 2:

Input: num = "10200", k = 1
Output: "200"
Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.
Example 3:

Input: num = "10", k = 2
Output: "0"
Explanation: Remove all the digits from the number and it is left with nothing which is 0.
 

Constraints:

1 <= k <= num.length <= 105
num consists of only digits.
num does not have any leading zeros except for the zero itself.

 */



// Other's Solution:
class Solution {
    public String removeKdigits(String num, int k) {
        // 数学+栈 - https://leetcode.cn/problems/remove-k-digits/solution/yi-zhao-chi-bian-li-kou-si-dao-ti-ma-ma-zai-ye-b-5/
        // Time: O(N), Space: O(N)
        ArrayDeque<Character> stack = new ArrayDeque<>();
        for (char digit : num.toCharArray()) {
            while (k > 0 && !stack.isEmpty() && stack.peekLast()-'0' > digit-'0') {
                stack.pollLast();
                k -= 1;
            }
            stack.offer(digit);
        }
        
        StringBuilder strB = new StringBuilder();
        boolean headZero = stack.peek() == '0';
        while (stack.size() > k) {
            if (headZero && stack.peek() == '0') {
                    stack.poll();
            } else {
                if (headZero) headZero = false;
                strB.append(stack.poll());
            }
        }
        if (strB.length() == 0) return "0";
        return strB.toString();
    }
}
