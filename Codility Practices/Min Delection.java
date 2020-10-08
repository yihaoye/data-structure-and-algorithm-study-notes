/*
S 字符串有 N 个字符，C 数组有 N 个整数 int
S 的每个字符与 C 的每个整数一一对应，即删除 S 的第 i 个字符时，需要把 C[i] 的相应整数计入“删减sum”
注意：S 的任意字符被删减后并不会改变 S 的任何字符与 C 数组内整数的原对应关系。
问：若使得 S 被删减为同字符不相邻的字符串，最小“删减sum”应是多少？
例子：
    "aabbcc" [1,2,3,4,5,6] 则删减后字符应为 "abc" 且 sum 应为 1+3+5=9
    "ababab" [5,6,7,8,9,10] 则删减后字符应为 "ababab" 且 sum 应为 0
*/



// My Solution: (贪婪算法)
// import java.util.*;
class Solution {
    public int solution(String S, int[] C) {
        // write your code in Java SE 8
        int len = C.length;
        if (len == 1) return 0;
        
        int tempMax = C[0];
        int sum1 = 0;
        int sum2 = C[0];
        for (int i = 1; i < len; i++) {
            if (S.charAt(i-1) != S.charAt(i)) {
                sum1 += tempMax;
                tempMax = C[i];
            } else if (tempMax < C[i]) {
                tempMax = C[i];
            }
            if (i == len-1) {
                sum1 += tempMax;
            }
            sum2 += C[i];
        }
        
        return sum2-sum1;
    }
}
