/**
An integer array original is transformed into a doubled array changed by appending twice the value of every element in original, and then randomly shuffling the resulting array.

Given an array changed, return original if changed is a doubled array. If changed is not a doubled array, return an empty array. The elements in original may be returned in any order.

 

Example 1:

Input: changed = [1,3,4,2,6,8]
Output: [1,3,4]
Explanation: One possible original array could be [1,3,4]:
- Twice the value of 1 is 1 * 2 = 2.
- Twice the value of 3 is 3 * 2 = 6.
- Twice the value of 4 is 4 * 2 = 8.
Other original arrays could be [4,3,1] or [3,1,4].
Example 2:

Input: changed = [6,3,0,1]
Output: []
Explanation: changed is not a doubled array.
Example 3:

Input: changed = [1]
Output: []
Explanation: changed is not a doubled array.
 

Constraints:

1 <= changed.length <= 10^5
0 <= changed[i] <= 10^5

 */



// My Solution:
class Solution {
    public int[] findOriginalArray(int[] changed) {
        // TreeMap - 先记录每个数字及其统计次数，如果是 doubled array 的话肯定每个数都成对 - 且一个小的一个大的，为了把问题简化，我们从小数（统计数大于 0）开始遍历然后乘以 2 找对应的数的统计数是否大于等于小数的统计数，若是则 -= 小数统计数，否则说明数组不是 doubled array
        int len = changed.length;
        if (len % 2 != 0) return new int[]{};
        
        TreeMap<Integer, Integer> cnt = new TreeMap<>();
        for (int num : changed) {
            cnt.put(num, cnt.getOrDefault(num, 0) + 1);
        }
        
        int[] res = new int[len/2];
        int resIndex = 0;
        for (Integer originNum : cnt.keySet()) {
            if (originNum == 0) { // edge cases
                for (int i=0; i<cnt.get(originNum)/2; i++) res[resIndex++] = originNum;
                continue;
            }
                
            int originNumCnt = cnt.get(originNum), doubleNumCnt = cnt.getOrDefault(originNum*2, 0);
            if (originNumCnt == 0) continue;
            if (originNumCnt > 0 && doubleNumCnt >= originNumCnt) {
                cnt.put(originNum*2, doubleNumCnt - originNumCnt);
                for (int i=0; i<originNumCnt; i++) res[resIndex++] = originNum;
            } else {
                return new int[]{};
            }
        }
        return res;
    }
}
