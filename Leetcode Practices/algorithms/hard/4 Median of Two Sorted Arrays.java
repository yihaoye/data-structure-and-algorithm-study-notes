//Question:
/*
There are two sorted arrays nums1 and nums2 of size m and n respectively.

Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

Example 1:

nums1 = [1, 3]
nums2 = [2]

The median is 2.0

Example 2:

nums1 = [1, 2]
nums2 = [3, 4]

The median is (2 + 3)/2 = 2.5
*/



// Other's Solution:
class Solution {
    public double findMedianSortedArrays(int[] A, int[] B) {
        // 二分搜索 - https://leetcode.com/problems/median-of-two-sorted-arrays/solutions/2496/concise-java-solution-based-on-binary-search/
        // if (aMid < bMid) Keep [aRight + bLeft]    
        // else Keep [bRight + aLeft]
        // Time: O(log(m + n))
        int m = A.length, n = B.length;
        int l = (m + n + 1) / 2;
        int r = (m + n + 2) / 2;
        return (getkth(A, 0, B, 0, l) + getkth(A, 0, B, 0, r)) / 2.0;
    }

    public double getkth(int[] A, int aStart, int[] B, int bStart, int k) { // 寻找 A、B 剩余数组元素 (A[aStart...aLen]、B[bStart...bLen]) 的第 k 值（由小到大排序）
        if (aStart > A.length - 1) return B[bStart + k - 1];            
        if (bStart > B.length - 1) return A[aStart + k - 1];                
        if (k == 1) return Math.min(A[aStart], B[bStart]);
        
        int aMid = Integer.MAX_VALUE, bMid = Integer.MAX_VALUE;
        if (aStart + k/2 - 1 < A.length) aMid = A[aStart + k/2 - 1]; 
        if (bStart + k/2 - 1 < B.length) bMid = B[bStart + k/2 - 1];        
        
        if (aMid < bMid) return getkth(A, aStart + k/2, B, bStart, k - k/2); // Check: aRight + bLeft 
        else return getkth(A, aStart, B, bStart + k/2, k - k/2); // Check: bRight + aLeft
    }
}



// Other's Answer:
import java.util.*;

public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int total = nums1.length + nums2.length;
        /**
         * 假设两个数组长度之和是奇数，则中间数是第 total/2+1 个数
         * 假设两个数组长度之和是奇数，则中间数是第 total/2 个数 和 第 total/2+1 个数的平均值
         */
        if (total%2 == 1) {
            return findk(nums1, nums1.length, nums2, nums2.length, total/2+1);
        } else {
            // 为什么传进去的数组不会被修改？原因是findk中调用的是java.util.Arrays.copyOf()实现深拷贝
            // ToDO 偶数情况的寻找第 total/2+1 个数字可以在 total/2 上再做一次查找就可以，怎么实现这个优化？
            return (findk(nums1, nums1.length, nums2, nums2.length, total/2) +
                    findk(nums1, nums1.length, nums2, nums2.length, total/2+1)) / 2.0;
        }
    }

    // 递归查找第K个值
    public double findk(int[] a, int m, int[] b, int n, int k) {
        /**
         * 处理该问题中出现的递归的3个边界条件
         */
        // 保证任何时候，数组a的长度小于等于数组b，简化条件判断
        if (m>n)
            return findk(b, n, a, m, k);
        // 当数组a空，则返回数组b的第K个值，数组索引是k-1
        if (m==0)
            return b[k-1];
        // 当返回第一个最小值的时候(k=1),返回数组a和数组b最小值中较小的一个
        if (k==1)
            return Math.min(a[0], b[0]);

        /**
         * pa+pb = k,如果a[pa-1] < b[pb-1],显然可证：a[pa-1]一定小于第k个值,
         * 又因为数组a有序，因此数组的第0到第pa-1个元素均小于第K个值，可以截断，
         * 同时k = k-pa, 缩小一半的查找空间。
         * 
         * 同理可证，a[pa-1] > b[pb-1] 时，可截断b[0……pb-1]部分，k = k-pb。
         */
        int pa = 0, pb = 0;
        pa = Math.min(k/2, m); pb = k - pa;
        if (a[pa-1] < b[pb-1]){
            // java.util.Arrays.copyOf()实现深拷贝，内部实现是调用System.arraycopy
            // copyOfRange(int[] original, int from, int to)
            // Copies the specified range of the specified array into a new array.
            a = Arrays.copyOfRange(a, pa, m);
            return findk(a, m-pa, b, n, k-pa);
        } else if (a[pa-1] > b[pb-1]) {
            // 同上一条注释
            b = Arrays.copyOfRange(b, pb, n);
            return findk(a, m, b, n-pb, k-pb);
        } else {
            return a[pa-1];
        }
    }

    // 测试模块
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] sum1 = {3,4};
        int[] sum2 = {1,2};
        double result = solution.findMedianSortedArrays(sum1, sum2);
        System.out.println(result);
    }
}
/* Explain:
题目要求

    There are two sorted arrays nums1 and nums2 of size m and n respectively. 
    Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
    题目翻译：给定两个有序数组nums1和sums2，长度分别是m和n，求出两个数组的中间值，要求算法的复杂度是O(log(m+n))。

题目分析

    该问题可以抽象为一个数学问题：求第K小的值(或者第K大的值)。假若先合并两个数组，复杂度是O(m+n)，不符合要求。
    题目的复杂度要求O(log (m+n))给了我们提示：可以用二分查找来提高查找效率。
    算法设计采用递归二分查找，每一次递归截断一半的查找空间。

函数的伪码如下findk(int[] a, int m, int[] b, int n, int k)

    假如数组a长度大于数组b，交换两个数组，保证任何时候，数组a的长度小于等于数组b，简化条件判断
    当数组a空，则返回数组b的第k个值，数组索引是k-1
    当返回第一个最小值的时候(k=1)，返回数组a和数组b最小值中较小的一个
    截断：pa = Math.min(k/2, m)；pb = k - pa； 可得pa+pb = k，如果a[pa-1] < b[pb-1]，显然可证：a[pa-1]一定小于第k个值，
    又因为数组a有序，因此数组的第0到第pa-1个元素均小于第K个值，可以截断，同时k = k-pa，缩小一半的查找空间。
    同理可证，a[pa-1] > b[pb-1]时，可截断b[0……pb-1]部分，k = k-pb

文／SiyueLin（简书作者）
原文链接：http://www.jianshu.com/p/a370b640cbd5
著作权归作者所有，转载请联系作者获得授权，并标注“简书作者”。
*/