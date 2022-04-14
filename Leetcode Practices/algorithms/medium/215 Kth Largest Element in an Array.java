/**
Given an integer array nums and an integer k, return the kth largest element in the array.

Note that it is the kth largest element in the sorted order, not the kth distinct element.

 

Example 1:

Input: nums = [3,2,1,5,6,4], k = 2
Output: 5
Example 2:

Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
Output: 4
 

Constraints:

1 <= k <= nums.length <= 104
-104 <= nums[i] <= 104
 */



// Other's Solution:
class Solution {
    Random random = new Random();

    public int findKthLargest(int[] nums, int k) {
        /*
            基于快速排序的选择方法 - https://leetcode-cn.com/problems/kth-largest-element-in-an-array/solution/shu-zu-zhong-de-di-kge-zui-da-yuan-su-by-leetcode-/
            时间复杂度：O(N)，证明过程参考《算法导论》9.2：期望为线性的选择算法。
            空间复杂度：O(logN)，递归栈空间代价期望。
        */
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    public int quickSelect(int[] arr, int l, int r, int index) {
        int q = randomPartition(arr, l, r);
        if (q == index) return arr[q];
        else return q < index ? quickSelect(arr, q + 1, r, index) : quickSelect(arr, l, q - 1, index);
    }

    public int randomPartition(int[] arr, int l, int r) {
        int i = random.nextInt(r - l + 1) + l;
        swap(arr, i, r);
        return partition(arr, l, r);
    }

    public int partition(int[] arr, int l, int r) {
        int x = arr[r], i = l - 1;
        for (int j = l; j < r; ++j) {
            if (arr[j] <= x) swap(arr, ++i, j);
        }
        swap(arr, i + 1, r);
        return i + 1;
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
