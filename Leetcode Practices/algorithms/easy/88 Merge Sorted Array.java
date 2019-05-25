/*
Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.

Note:

The number of elements initialized in nums1 and nums2 are m and n respectively.
You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.
Example:

Input:
nums1 = [1,2,3,0,0,0], m = 3
nums2 = [2,5,6],       n = 3

Output: [1,2,2,3,5,6]
*/



// My Solution:
// Use Two Pointer
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] tempNums1 = new int[m];
        for (int i=0; i<m; i++) tempNums1[i] = nums1[i];
        for (int i=0, j=0, k=0; i<m+n; i++) {
            if (k >= n) {
                nums1[i] = tempNums1[j];
                j++;
            } else if (j >= m) {
                nums1[i] = nums2[k];
                k++;
            } else {
                if (tempNums1[j] <= nums2[k]) {
                    nums1[i] = tempNums1[j];
                    j++;
                } else {
                    nums1[i] = nums2[k];
                    k++;
                }
            }
        }
    }
}