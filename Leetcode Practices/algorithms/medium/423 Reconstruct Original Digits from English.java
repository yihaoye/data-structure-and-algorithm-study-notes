/*
Given a string s containing an out-of-order English representation of digits 0-9, return the digits in ascending order.

 

Example 1:

Input: s = "owoztneoer"
Output: "012"

Example 2:

Input: s = "fviefuro"
Output: "45"
 

Constraints:

1 <= s.length <= 105
s[i] is one of the characters ["e","g","f","i","h","o","n","s","r","u","t","w","v","x","z"].
s is guaranteed to be valid.
*/



// My Solution:
class Solution {
    public String originalDigits(String s) {
        /*
            一些数字的英文单词有独有字符，其他一些虽然没有但是可以减去前面那些数字的统计而得出
            时间复杂度 O(N)，空间复杂度 O(1)
        */
        // 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 (计算顺序 order matters)
        int[] digits = new int[10];
        for (Character c : s.toCharArray()) {
            switch (c) {
                case 'z': // z; ('z', 'e', 'r', 'o') 0
                    digits[0]++;
                    break;
                case 'o': // o - [0] - [2] - [4]; ('o', 'n', 'e') 1
                    digits[1]++;
                    break;
                case 'w': // w; ('t', 'w', 'o') 2
                    digits[2]++;
                    break;
                case 'h': // h - [8]; ('t', 'h', 'r', 'e', 'e') 3
                    digits[3]++;
                    break;
                case 'u': // u; ('f', 'o', 'u', 'r') 4
                    digits[4]++;
                    break;
                case 'f': // f - [4]; ('f', 'i', 'v', 'e') 5
                    digits[5]++;
                    break;
                case 'x': // x; ('s', 'i', 'x') 6
                    digits[6]++;
                    break;
                case 'v': // v - [5]; ('s', 'e', 'v', 'e', 'n') 7
                    digits[7]++;
                    break;
                case 'g': // g; ('e', 'i', 'g', 'h', 't') 8
                    digits[8]++;
                    break;
                case 'i': // i - [6] - [8] - [5]; ('n', 'i', 'n', 'e') 9
                    digits[9]++;
                    break;
                default:
                    break;
            }
        }
        digits[1] = digits[1] - digits[0] - digits[2] - digits[4];
        digits[3] = digits[3] - digits[8];
        digits[5] = digits[5] - digits[4];
        digits[7] = digits[7] - digits[5];
        digits[9] = digits[9] - digits[6] - digits[8] - digits[5];
        StringBuilder strB = new StringBuilder();
        for (int i=0; i<digits.length; i++) {
            while (digits[i]-- > 0) strB.append(i);
        }
        
        return strB.toString();
    }
}
