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



//My Answer: (but may exceed O(log(m+n)) )
//What about merge two array (A1,A2) into one (A3), then sort (A3) again, 
//then find the median (if A1.lendth = n1, A2.length = n2, A3.length = n1+n2, 
//if n1+n2 is odd, median = A3[(n1+n2)/2+1], if n1+n2 is even, median = (A3[(n1+n2)/2]+A3[(n1+n2)/2+1])/2 )



//Other's Answer:
class Solution {
public:
    double findMedianSortedArrays(vector<int>& nums1, vector<int>& nums2) {
        int m = nums1.size(), n = nums2.size();
        int total = m+n;
        if(total%2 == 1)
            return helper(nums1, m, nums2, n, total/2+1, 0, 0);
        else
            return (helper(nums1, m, nums2, n, total/2, 0, 0) + helper(nums1, m, nums2, n, total/2+1, 0, 0))/2;
    }
    double helper(vector<int>& nums1, int m, vector<int>& nums2, int n, int k, int start1, int start2){
        if(m > n)
            return helper(nums2, n, nums1, m, k, start2, start1);
        if(m == 0)
            return nums2[k-1];
        if(k == 1)
            return min(nums1[start1], nums2[start2]);
        int a = min(k/2, m);
        int b = k-a;
        if(nums1[a+start1-1] <= nums2[b+start2-1]){
            return helper(nums1, m-a, nums2, n, k-a, start1+a, start2);
        }
        else{
            return helper(nums1, m, nums2, n-b, k-b, start1, start2+b);
        }
    }
};



//Other's Answer 2: (Similar to Answer 1)
    /*--------------------------------------------
    *   日期：2014-01-16
    *   作者：SJF0115
    *   题目: Median of Two Sorted Arrays
    *   网址：http://oj.leetcode.com/problems/median-of-two-sorted-arrays/
    *   结果：AC
    *   来源：LeetCode
    *   总结：
    ------------------------------------------------*/
    #include <iostream>
    #include <stdio.h>
    using namespace std;

    class Solution {
    public:
        //求中位数
        double findMedianSortedArrays(int A[], int m, int B[], int n) {
            int total = (m + n);
            //判断奇偶性
            if(total &0x1){
                return find_kth(A,m,B,n,total/2+1);
            }
            else{
                double a = find_kth(A,m,B,n,total/2);
                double b = find_kth(A,m,B,n,total/2+1);
                return (a + b) / 2;
            }
        }
    private:
        //求第k个元素
        static double find_kth(int A[], int m, int B[], int n, int k) {
            if (m > n) {
                return find_kth(B, n, A, m, k);
            }
            if (m == 0) {
                return B[k - 1];
            }
            if (k == 1) {
                return min(A[0], B[0]);
            }
            //++
            int pa = min(k / 2, m);
            int pb = k - pa;
            //删除A数组的pa个
            if (A[pa - 1] < B[pb - 1]) {
                return find_kth(A + pa, m - pa, B, n, k - pa);
            }
            //删除B数组的pb个
            else if (A[pa - 1] > B[pb - 1]) {
                return find_kth(A, m, B + pb, n - pb, k - pb);
            }
            //A[pa - 1] = B[pb - 1] 则A[pa - 1]，B[pb - 1]为第k个
            else {
                return A[pa - 1];
            }
        }
    };
    int main() {
        double result;
        Solution solution;
        //int A[] = {1,4,6,7,9,17};
        //int B[] = {2,3,5,8,11,14};
        int A[] = {};
        int B[] = {1};
        result = solution.findMedianSortedArrays(A,0,B,1);
        printf("Result:%lf\n",result);
        return 0;
    }
/* Answer 2 explain:
思路：
这是一道非常经典的题。这题更通用的形式是，给定两个已经排序好的数组，找到两者所有元素中第 k 大的元素。O(m + n) 的解法比较直观，
直接 merge 两个数组，然后求第 k 大的元素。不过我们仅仅需要第 k 大的元素，是不需要“排序”这么复杂的操作的。
可以用一个计数器，记录当前已经找到第 m 大的元素了。同时我们使用两个指针 pA 和 pB，分别指向 A 和 B 数组的第一个元素，
使用类似于 merge sort 的原理，如果数组 A 当前元素小，那么 pA++，同时 m++；如果数组 B 当前元素小，那么 pB++，同时 m++。
最终当 m 等于 k 的时候，就得到了我们的答案，O(k)时间，O(1) 空间。但是，当 k 很接近 m + n 的时候，这个方法还是 O(m + n) 的。

有没有更好的方案呢？我们可以考虑从 k 入手。如果我们每次都能够删除一个一定在第 k 大元素之前的元素，那么我们需要进行 k 次。
但是如果每次我们都删除一半呢？由于 A 和 B 都是有序的，我们应该充分利用这里面的信息，类似于二分查找，也是充分利用了“有序”。
假设 A 和 B 的元素个数都大于 k/2，我们将 A 的第 k/2 个元素（即 A[k/2-1]）和 B 的第 k/2
个元素（即 B[k/2-1]）进行比较，有以下三种情况（为了简化这里先假设 k 为偶数，所得到的结论对于 k 是奇数也是成立的）：
A[k/2-1] == B[k/2-1]
A[k/2-1] > B[k/2-1]
A[k/2-1] < B[k/2-1]
如果 A[k/2-1] < B[k/2-1]，意味着 A[0] 到 A[k/2-1 的肯定在 A [ B 的 top k 元素的范围内，
换句话说，A[k/2-1 不可能大于 A [ B 的第 k 大元素。留给读者证明。因此，我们可以放心的删除 A 数组的这 k/2 个元素。
同理，当 A[k/2-1] > B[k/2-1] 时，可以删除 B 数组的 k/2 个元素。当 A[k/2-1] == B[k/2-1] 时，说明找到了第 k 大的元素，
直接返回 A[k/2-1] 或 B[k/2-1]即可。因此，我们可以写一个递归函数。那么函数什么时候应该终止呢？
当 A 或 B 是空时，直接返回 B[k-1] 或 A[k-1]；当 k=1 是，返回 min(A[0], B[0])；当 A[k/2-1] == B[k/2-1] 时，
返回 A[k/2-1] 或 B[k/2-1]
*/


