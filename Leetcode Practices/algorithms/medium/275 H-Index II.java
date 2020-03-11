/*
Given an array of citations sorted in ascending order (each citation is a non-negative integer) of a researcher, write a function to compute the researcher's h-index.

According to the definition of h-index on Wikipedia: "A scientist has index h if h of his/her N papers have at least h citations each, and the other N − h papers have no more than h citations each."

Example:

Input: citations = [0,1,3,5,6]
Output: 3 
Explanation: [0,1,3,5,6] means the researcher has 5 papers in total and each of them had 
             received 0, 1, 3, 5, 6 citations respectively. 
             Since the researcher has 3 papers with at least 3 citations each and the remaining 
             two with no more than 3 citations each, her h-index is 3.
Note:

If there are several possible values for h, the maximum one is taken as the h-index.

Follow up:

This is a follow up problem to H-Index, where citations is now guaranteed to be sorted in ascending order.
Could you solve it in logarithmic time complexity?
*/



// My Solution:
class Solution {
    public int hIndex(int[] citations) {
        int len = citations.length;
        if (len == 0) return 0;
        if (citations[0] >= len) return len;
        if (citations[len-1] == 0) return 0;
        
        // Binary Search
        return binarySearch(citations, 0, len-1);
    }
    
    public int binarySearch(int[] citations, int start, int end) {
        int mid = 0, len = end+1;
        while (start < end) {
            mid = start + (end - start) / 2;
            if (citations[mid] > len-mid) end = mid;
            else start = mid + 1;
        }
        if (citations[start-1] == len-start+1) start--;
        return len-start;
    }
}



// Other's Solution:
class Solution {
    public int hIndex(int[] citations) {
        int len = citations.length;
        int lo = 0, hi = len - 1;
        while (lo <= hi) {
            int med = (hi + lo) / 2;
            if (citations[med] == len - med) {
                return len - med;
            } else if (citations[med] < len - med) {
                lo = med + 1;
            } else { 
                // (citations[med] > len-med), med may be qualified as a hIndex,
                // but we have to continue to search for a higher one.
                hi = med - 1;
            }
        }
        return len - lo;
    }
}
/*
更多解析：
因为本题目输入数组已是排序好的，h 的前半段定义是数组里有 h 个元素大于等于 h，即是从后往前数第 j 个元素时该元素的值 >= j，凡是满足元素值 >= j 都是符合 h 前半段定义的，
又且因为追求最大的那个满足条件的 h，就意味着是从后往前数最后一个满足元素值 >= j 的元素（与数组长度的差），此时 j 是从后往前数的索引数，不能往前再进一步了（因为是整数再进一步就不满足条件了），(亦即顺序里第一个 citations[i] >= len-i 的)。
实例：
[0,1,3,5,6]
从后往前第 1 个元素的值为 6 >= 1，符合 h 的前半段定义，此时 h 为 1（但其实不符合 h 后半段定义 - 即其余 len-h 个元素的值皆 <= h）
从后往前第 2 个元素的值为 5 >= 2，符合 h 的前半段定义，此时 h 为 2（但其实不符合 h 后半段定义）
从后往前第 3 个元素的值为 3 >= 3，符合 h 的完整定义，此时 h 为 3
从后往前第 4 个元素的值为 1 < 4，不符合 h 的定义
从后往前第 5 个元素的值为 0 < 5，不符合 h 的定义
所以 h 为 3

当我们从后往前遍历数组时会发现，在那个元素之后的所有数都满足 citations[i] > len-i，因为 citations[i] 是递增的、len-i 是递减的。(len-i 就是上面的 j，只不过 i 是正向的索引 j 是反向的索引)
*/