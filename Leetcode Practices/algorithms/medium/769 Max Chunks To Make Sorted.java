/**
You are given an integer array arr of length n that represents a permutation of the integers in the range [0, n - 1].

We split arr into some number of chunks (i.e., partitions), and individually sort each chunk. After concatenating them, the result should equal the sorted array.

Return the largest number of chunks we can make to sort the array.

 

Example 1:

Input: arr = [4,3,2,1,0]
Output: 1
Explanation:
Splitting into two or more chunks will not return the required result.
For example, splitting into [4, 3], [2, 1, 0] will result in [3, 4, 0, 1, 2], which isn't sorted.
Example 2:

Input: arr = [1,0,2,3,4]
Output: 4
Explanation:
We can split into two chunks, such as [1, 0], [2, 3, 4].
However, splitting into [1, 0], [2], [3], [4] is the highest number of chunks possible.
 

Constraints:

n == arr.length
1 <= n <= 10
0 <= arr[i] < n
All the elements of arr are unique.
 */



// My Solution 2:
class Solution {
    public int maxChunksToSorted(int[] arr) {
        // 贪心 + 排序 + 双指针 + 哈希表 - 先对原来 arr 做一次排序
        // Time: O(N*logN), Space: O(N)
        int n = arr.length, res = 0;
        int[] sortArr = arr.clone();
        Arrays.sort(sortArr);

        for (int r=n-1; r>=0;) {
            res++;
            r = firstLeftWithSameSet(arr, sortArr, r) - 1;
        }
        return res;
    }

    // 找到第一个 left 索引，使得 arrA[left..right] 与 arrB[left..right] 的元素集合一样（元素可能重复所以使用哈希表）
    public int firstLeftWithSameSet(int[] arrA, int[] arrB, int right) {
        Map<Integer, Integer> setA = new HashMap<>(), setB = new HashMap<>();
        for (int left=right; left>0; left--) {
            // setB 如果包含 arrA[left] 且 >0 则 -1，若 setB 不包含 arrA[left] 则添加进 setA
            if (setB.containsKey(arrA[left])) {
                int preVal = setB.get(arrA[left]);
                if (preVal == 1) setB.remove(arrA[left]);
                else setB.put(arrA[left], preVal - 1);
            } else {
                setA.put(arrA[left], setA.getOrDefault(arrA[left], 0) + 1);
            }

            // setA 如果包含 arrB[left] 且 >0 则 -1，若 setA 不包含 arrB[left] 则添加进 setB
            if (setA.containsKey(arrB[left])) {
                int preVal = setA.get(arrB[left]);
                if (preVal == 1) setA.remove(arrB[left]);
                else setA.put(arrB[left], preVal - 1);
            } else {
                setB.put(arrB[left], setB.getOrDefault(arrB[left], 0) + 1);
            }

            // setA 和 setB 均为空，说明至此 arrA[left..right] 和 arrB[left..right] 的元素全部互相抵消了
            if (setA.size() == 0 && setB.size() == 0) {
                return left;
            }
        }

        return 0; // 最差情况 left == 0 必然可以全部抵消
    }
}



// My Solution 1:
class Solution {
    public int maxChunksToSorted(int[] arr) {
        // 贪心 + 排序 + 双指针 - 先对原来 arr 做一次排序
        // Time: O(N^2), Space: O(N)
        int n = arr.length, res = 0;
        int[] sortArr = new int[n];
        for (int i=0; i<n; i++) sortArr[i] = arr[i];
        Arrays.sort(sortArr);

        for (int r=n-1; r>=0;) {
            for (int l=r; l>=0; l--) {
                int[] subA = Arrays.copyOfRange(arr, l, r + 1);
                int[] subB = Arrays.copyOfRange(sortArr, l, r + 1);
                Arrays.sort(subA);
                if (Arrays.equals(subA, subB)) {
                    res++;
                    r = l - 1;
                    break;
                }
            }
        }
        return res;
    }
}



// Other's Solution:
class Solution {
    public int maxChunksToSorted(int[] arr) {
        // https://leetcode.cn/problems/max-chunks-to-make-sorted/solution/zui-duo-neng-wan-cheng-pai-xu-de-kuai-i-by-leetcod/
        // Time: O(N), Space: O(1)
        int ans = 0, max = 0;
        for (int i = 0; i < arr.length; ++i) {
            max = Math.max(max, arr[i]);
            if (max == i) ans++;
        }
        return ans;
    }
}
