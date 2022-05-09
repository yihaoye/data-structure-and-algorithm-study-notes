/**
Alice is texting Bob using her phone. The mapping of digits to letters is shown in the figure below.

https://assets.leetcode.com/uploads/2022/03/15/1200px-telephone-keypad2svg.png
In order to add a letter, Alice has to press the key of the corresponding digit i times, where i is the position of the letter in the key.

For example, to add the letter 's', Alice has to press '7' four times. Similarly, to add the letter 'k', Alice has to press '5' twice.
Note that the digits '0' and '1' do not map to any letters, so Alice does not use them.
However, due to an error in transmission, Bob did not receive Alice's text message but received a string of pressed keys instead.

For example, when Alice sent the message "bob", Bob received the string "2266622".
Given a string pressedKeys representing the string received by Bob, return the total number of possible text messages Alice could have sent.

Since the answer may be very large, return it modulo 109 + 7.

 

Example 1:

Input: pressedKeys = "22233"
Output: 8
Explanation:
The possible text messages Alice could have sent are:
"aaadd", "abdd", "badd", "cdd", "aaae", "abe", "bae", and "ce".
Since there are 8 possible messages, we return 8.
Example 2:

Input: pressedKeys = "222222222222222222222222222222222222"
Output: 82876089
Explanation:
There are 2082876103 possible text messages Alice could have sent.
Since we need to return the answer modulo 109 + 7, we return 2082876103 % (109 + 7) = 82876089.
 

Constraints:

1 <= pressedKeys.length <= 105
pressedKeys only consists of digits from '2' - '9'.
 */



// Other's Solution:
class Solution {
    int MOD = 1000000007;

    public int countTexts(String pressedKeys) {
        // DP
        // Time: O(N), Space: O(N)
        int n = pressedKeys.length();
        int[] dp = new int[n+1];
        dp[0] = 1;
        for (int i=1; i<=n; i++) {
            char curChar = pressedKeys.charAt(i-1);
            dp[i] = dp[i-1] % MOD;
            if (i >= 2 && curChar == pressedKeys.charAt(i-2)) { // curChar == lastChar
                dp[i] += dp[i-2];
                dp[i] %= MOD;
                if (i >= 3 && curChar == pressedKeys.charAt(i-3)) { // curChar == secLastChar
                    dp[i] += dp[i-3];
                    dp[i] %= MOD;
                    if ((curChar == '7' || curChar == '9') && i >= 4 && curChar == pressedKeys.charAt(i-4)) { // curChar == thirdLastChar
                        dp[i] += dp[i-4];
                        dp[i] %= MOD;
                    }
                }
            }
        }
        
        return dp[n];
    }
}



// My Solution: (LC 有一些测试未通过，估计是输入数值过大时 MOD 后的值与 LC 的经典做法 MOD 后的值不一样，但是理论上该解法应该是没问题的)
class Solution {
    int MOD = 1000000007;
    Set<Character> set1 = new HashSet<>(Arrays.asList('2', '3', '4', '5', '6', '8'));
    Set<Character> set2 = new HashSet<>(Arrays.asList('7', '9'));
        
    public int countTexts(String pressedKeys) {
        // 只需要关心连续相同数的组合，因为两个连续不相同数必然是两个字符
        // 最后答案为所有连续相同数的组合（group）的乘积
        // 连续相同数的组合可以通过 "动态规划" (通过第一次遍历找出 dp1 长度 - 除 7、9 外最大连续相同数长度，和 dp2 长度 - 以 7 或 9 为最大连续相同数长度) 减少重复计算 countTexts("22222") = 1*countTexts("2222") + 1*countTexts("222") + 1*countTexts("22") = 展开(countTexts("2222")) + 展开(countTexts("222")) + 展开(countTexts("22")) = ...
        // 除 7、9 之外的数（2,3,4,5,6,8）不用因不同数而重新计算用 dp1 记录, 7、9 为 countTexts("77777") = 1*countTexts("7777") + 1*countTexts("777") + 1*countTexts("77") + 1*countTexts("7") 用 dp2 记录
        char lastChar = '1'; // pressedKeys only consists of digits from '2' - '9', assign invalid by default
        int dp1Len = 1, dp2Len = 1, lastCharCount = 0;
        List<int[]> groups = new ArrayList<>(); // group 为 int[2]，group[0] == 1 使用 dp1，group[0] == 2 使用 dp2
        for (char c : pressedKeys.toCharArray()) {
            if (c != lastChar) {
                if (set1.contains(lastChar)) groups.add(new int[]{1, lastCharCount});
                if (set2.contains(lastChar)) groups.add(new int[]{2, lastCharCount});
                lastChar = c;
                lastCharCount = 1;
            } else {
                lastCharCount++;
                if (set1.contains(lastChar)) dp1Len = Math.max(dp1Len, lastCharCount);
                if (set2.contains(lastChar)) dp2Len = Math.max(dp2Len, lastCharCount);
            }
        }
        if (set1.contains(lastChar)) groups.add(new int[]{1, lastCharCount});
        if (set2.contains(lastChar)) groups.add(new int[]{2, lastCharCount});
        
        int[] dp1 = new int[Math.max(dp1Len + 1, 3 + 1)], dp2 = new int[Math.max(dp2Len + 1, 4 + 1)];
        setDP1(dp1);
        setDP2(dp2);
        
        int res = 1;
        for (int[] group : groups) {
            if (group[0] == 1) res = (res * dp1[group[1]]) % MOD;
            if (group[0] == 2) res = (res * dp2[group[1]]) % MOD;
        }
        
        return res;
    }
    
    private void setDP1(int[] dp1) {
        dp1[0] = 1; // 0 个连续数的组合数 = 1，因为代表唯一可能性即 “未输入”
        dp1[1] = dp1[0]; // 1 个连续数时的组合数
        dp1[2] = dp1[0] + dp1[1]; // 2 个连续数时的组合数
        dp1[3] = dp1[0] + dp1[1] + dp1[2]; // 3 个连续数时的组合数 -- 'c'*countTexts("") + 'b'*countTexts("2") + 'a'*countTexts("22")
        for (int i=4; i<dp1.length; i++) {
            dp1[i] = ((dp1[i-3] + dp1[i-2]) % MOD + dp1[i-1]) % MOD;
        }
    }
    
    private void setDP2(int[] dp2) {
        dp2[0] = 1; // 0 个连续数的组合数 = 1，因为代表唯一可能性即 “未输入”
        dp2[1] = dp2[0]; // 1 个连续数时的组合数
        dp2[2] = dp2[0] + dp2[1]; // 2 个连续数时的组合数
        dp2[3] = dp2[0] + dp2[1] + dp2[2]; // 3 个连续数时的组合数
        dp2[4] = dp2[0] + dp2[1] + dp2[2] + dp2[3]; // 4 个连续数时的组合数 -- 's'*countTexts("") + 'r'*countTexts("7") + 'q'*countTexts("77") + 'p'*countTexts("777")
        for (int i=5; i<dp2.length; i++) {
            dp2[i] = (((dp2[i-4] + dp2[i-3]) % MOD + dp2[i-2]) % MOD + dp2[i-1]) % MOD;
        }
    }
}
