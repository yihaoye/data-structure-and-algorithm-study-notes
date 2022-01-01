/*
You are given a 0-indexed array of n integers arr.

The interval between two elements in arr is defined as the absolute difference between their indices. More formally, the interval between arr[i] and arr[j] is |i - j|.

Return an array intervals of length n where intervals[i] is the sum of intervals between arr[i] and each element in arr with the same value as arr[i].

Note: |x| is the absolute value of x.

 

Example 1:

Input: arr = [2,1,3,1,2,3,3]
Output: [4,2,7,2,4,4,5]
Explanation:
- Index 0: Another 2 is found at index 4. |0 - 4| = 4
- Index 1: Another 1 is found at index 3. |1 - 3| = 2
- Index 2: Two more 3s are found at indices 5 and 6. |2 - 5| + |2 - 6| = 7
- Index 3: Another 1 is found at index 1. |3 - 1| = 2
- Index 4: Another 2 is found at index 0. |4 - 0| = 4
- Index 5: Two more 3s are found at indices 2 and 6. |5 - 2| + |5 - 6| = 4
- Index 6: Two more 3s are found at indices 2 and 5. |6 - 2| + |6 - 5| = 5
Example 2:

Input: arr = [10,5,10,10]
Output: [5,0,3,4]
Explanation:
- Index 0: Two more 10s are found at indices 2 and 3. |0 - 2| + |0 - 3| = 5
- Index 1: There is only one 5 in the array, so its sum of intervals to identical elements is 0.
- Index 2: Two more 10s are found at indices 0 and 3. |2 - 0| + |2 - 3| = 3
- Index 3: Two more 10s are found at indices 0 and 2. |3 - 0| + |3 - 2| = 4
 

Constraints:

n == arr.length
1 <= n <= 105
1 <= arr[i] <= 105
*/



// My Solution:
class Solution {
    public long[] getDistances(int[] arr) {
        /*
            哈希表+顺序表，Key 为元素的值，Value 为同值的索引，每找到一个新的元素值时，只需添加其进哈希表及其顺序表，若遍历到的索引 i 的元素值已存在时哈希表时，取出其对应顺序表，然后将 j 加进该顺序表。遍历原数组结束后，遍历哈希表，每个 Key 取其 Value 顺序表，然后遍历顺序表，对于每个顺序表的值（即之前同值索引）j ，其结果 res[j] = j 右边所有索引的和 - j 左边所有索引的和 + j*(左边元素量 - 右边元素量)，又因为右边的原因，一个更简便的方法是实则顺序表不存储 j 而是 j 及左边所有索引的和（list.get(j-1) 即前缀和），当需要 j 值时 j = list.get(j) - list(j-1);
            时间复杂度 O(N)，空间复杂度 O(N)
        */
        long[] res = new long[arr.length];
        Map<Integer, List<Long>> map = new HashMap<>();
        for (int i=0; i<arr.length; i++) {
            if (map.containsKey(arr[i])) {
                List<Long> list = map.get(arr[i]); // value is preIndexesWithSameVal
                long preSum = list.get(list.size()-1);
                list.add((long)i + preSum);
            } else {
                List<Long> list = new ArrayList<>();
                list.add((long)i);
                map.put(arr[i], list);
            }
        }
        
        for (Map.Entry<Integer, List<Long>> entry : map.entrySet()) {
            List<Long> list = entry.getValue();
            for (int i=0; i<list.size(); i++) {
                if (i == 0) {
                    long j = list.get(i);
                    res[(int)j] = list.get(list.size()-1) - j * (long)list.size();
                } else {
                    long j = list.get(i) - list.get(i-1);
                    res[(int)j] = (list.get(list.size()-1) - list.get(i)) - list.get(i-1) + j * ((long)i-((long)list.size()-(long)i-1L));
                }
            }
        }
        
        return res;
    }
}
