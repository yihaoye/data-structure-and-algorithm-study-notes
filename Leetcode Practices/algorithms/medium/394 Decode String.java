/*
Given an encoded string, return its decoded string.

The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.

You may assume that the input string is always valid; there are no extra white spaces, square brackets are well-formed, etc.

Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there will not be input like 3a or 2[4].

 

Example 1:

Input: s = "3[a]2[bc]"
Output: "aaabcbc"
Example 2:

Input: s = "3[a2[c]]"
Output: "accaccacc"
Example 3:

Input: s = "2[abc]3[cd]ef"
Output: "abcabccdcdcdef"
 

Constraints:

1 <= s.length <= 30
s consists of lowercase English letters, digits, and square brackets '[]'.
s is guaranteed to be a valid input.
All the integers in s are in the range [1, 300].

*/



// My Solution:
class Solution {
    public String decodeString(String s) {
        // 双端栈，遍历到 ] 时取出栈中数据（直到 [）并取出 [ 前数字（直到字母或栈为空）进行计算，计算后的数据存回栈中，最后栈中剩下的将是纯字母，拼接并返回该字符串
        // Time: (N*K), Space: (N*K)
        ArrayDeque<Character> queue = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == ']') {
                StringBuilder tempStrB = new StringBuilder();
                int times = 0, tens = 1;
                while (queue.peekLast() != '[') tempStrB.append(queue.pollLast());
                tempStrB = tempStrB.reverse();
                queue.pollLast(); // remove '['
                while (!queue.isEmpty() && Character.isDigit(queue.peekLast())) {
                    times = (queue.pollLast()-'0') * tens + times;
                    tens *= 10;
                }
                if (times == 0) times = 1;
                String tempStr = tempStrB.toString();
                while (times-- > 0) {
                    for (char tempC : tempStr.toCharArray()) queue.offer(tempC);
                }
            } else {
                queue.offer(c);
            }
        }
        
        StringBuilder strB = new StringBuilder();
        while (!queue.isEmpty()) strB.append(queue.poll());
        
        return strB.toString();
    }
}



// My Solution:
class Solution {
    public String decodeString(String s) {
        /*
            遍历字符串，递归解题
            时间复杂度 O(N)，空间复杂度 O(N)
        */
        StringBuilder res = new StringBuilder(), subStrB = new StringBuilder();
        int leftBrackets = 0, digit = 0;
        for (char c : s.toCharArray()) {
            int diff = c - '0';
            if (leftBrackets == 0 && diff < 10 && diff >= 0) { // 只处理本层数字
                digit = digit * 10 + diff;
                continue;
            }
            
            if (leftBrackets == 0 && c != '[' && c != ']') {
                res.append(c);
            } else if (leftBrackets == 0 && c == '[') {
                leftBrackets++;
            } else if (leftBrackets == 1 && c == ']') {
                leftBrackets--;
                String subStr = decodeString(subStrB.toString()); // flat the sub string
                subStrB.setLength(0);
                do subStrB.append(subStr); while (--digit > 0);
                res.append(subStrB.toString());
                subStrB.setLength(0);
            } else {
                if (c == '[') leftBrackets++;
                if (c == ']') leftBrackets--;
                subStrB.append(c);
            }
        }
        // if (subStrB.length() > 0) res.append(subStrB.toString());
        return res.toString();
    }
}
