/**
A string s is called good if there are no two different characters in s that have the same frequency.

Given a string s, return the minimum number of characters you need to delete to make s good.

The frequency of a character in a string is the number of times it appears in the string. For example, in the string "aab", the frequency of 'a' is 2, while the frequency of 'b' is 1.

 

Example 1:

Input: s = "aab"
Output: 0
Explanation: s is already good.
Example 2:

Input: s = "aaabbbcc"
Output: 2
Explanation: You can delete two 'b's resulting in the good string "aaabcc".
Another way it to delete one 'b' and one 'c' resulting in the good string "aaabbc".
Example 3:

Input: s = "ceabaacb"
Output: 2
Explanation: You can delete both 'c's resulting in the good string "eabaab".
Note that we only care about characters that are still in the string at the end (i.e. frequency of 0 is ignored).
 

Constraints:

1 <= s.length <= 10^5
s contains only lowercase English letters.
 */



// My Solution:
class Solution {
    public int minDeletions(String s) {
        // 排序+模拟（填两个邻接数的差值空缺，diff=count[i-1]-count[i], if (countCount[i-1]-1<=diff) { res += gaussian(countCount[i-1]-1); } else { res += gaussian(diff)+(countCount[i-1]-1-diff)*diff; countCount[i] += countCount[i-1]-1-diff; }）
        int[] chars = new int[26];
        for (char c : s.toCharArray()) {
            chars[c-'a']++;
        }
        Arrays.sort(chars);
        int[][] count = new int[26][2]; // { countOfChar, countOfCountOfChar }
        for (int i=25, j=0; i>=0; i--) {
            if (chars[i] != 0) {
                if (chars[i] == count[j][0]) {
                    count[j][1] += 1;
                } else {
                    if (count[j][0] != 0) j++;
                    count[j][0] = chars[i];
                    count[j][1] += 1;
                }
            } else {
                break;
            }
        }
        int res = 0;
        for (int i=0; i<25; i++) {
            if (count[i][0] == 0) break;
            
            int diff = count[i][0] - count[i+1][0];
            if (count[i][1] <= diff) {
                res += gaussian(count[i][1]-1);
            } else {
                res += gaussian(diff) + (count[i][1] - 1 - diff) * diff;
                count[i+1][1] += count[i][1] - diff;
            }
        }
        if (count[25][0] != 0) res += gaussian(count[25][1]-1);
        
        return res;
    }
    
    private int gaussian(int num) {
        return (num + 1) * num / 2;
    }
}
