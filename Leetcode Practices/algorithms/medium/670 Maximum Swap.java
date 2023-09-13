/*
You are given an integer num. You can swap two digits at most once to get the maximum valued number.

Return the maximum valued number you can get.

 

Example 1:

Input: num = 2736
Output: 7236
Explanation: Swap the number 2 and the number 7.
Example 2:

Input: num = 9973
Output: 9973
Explanation: No swap.
 

Constraints:

0 <= num <= 10^8
*/



// My Solution:
class Solution {
    public int maximumSwap(int num) {
        // Greedy, Time:O(N)
        int[] digits = new int[10]; Arrays.fill(digits, Integer.MAX_VALUE);
        int res = num;
        List<Integer> list = new ArrayList<>();
        while (num > 0) {
          list.add(num % 10);
          num /= 10;
        }
        for (int i=0; i<list.size(); i++) digits[list.get(i)] = i; // 最后得出每个数字最高位的索引

        for (int i=0; i<list.size(); i++) { // 从 num 的低位（个位）往高位遍历
          for (int j=0; j<list.get(i); j++) {
            if (digits[j] < i || digits[j] == Integer.MAX_VALUE) continue;
            // 如果有数字 j 比 num[i] 小且位置比 i 高的，就调换
            int tmp = list.get(digits[j]); list.set(digits[j], list.get(i)); list.set(i, tmp);
            res = Math.max(res, listToInt(list));
            // swap back
            list.set(i, list.get(digits[j])); list.set(digits[j], tmp);
          }
        }

        return res;
    }

    public int listToInt(List<Integer> list) {
      int res = 0; int shift = 1;
      for (int d : list) { // 注意，list 个位在最左
        res += d * shift;
        shift *= 10;
      }
      return res;
    }
}
