/*
Given an array with n objects colored red, white or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white and blue.

Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.

Note: You are not suppose to use the library's sort function for this problem.

Example:

Input: [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]
Follow up:

A rather straight forward solution is a two-pass algorithm using counting sort.
First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's, then 1's and followed by 2's.
Could you come up with a one-pass algorithm using only constant space?
*/



// Other's Solution:
class Solution {
    public void sortColors(int[] nums) {
        // one-pass
        int n0 = -1, n1 = -1, n2 = -1;
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] == 0) {
                nums[++n2] = 2; nums[++n1] = 1; nums[++n0] = 0;
            } else if (nums[i] == 1) {
                nums[++n2] = 2; nums[++n1] = 1;
            } else if (nums[i] == 2) {
                nums[++n2] = 2;
            }
        }
    }
}
/*
绝妙的题解：
此题解的核心思想就是当遍历到第 i 个元素时会将 [0...i]（此前 [0...i-1] 已经过上一轮更新排序）中的最多 3 个元素进行更新，
若 nums[i] 为 0 则在上一次最后一个 (latest) 2 后面的元素更新为 2，上一次最后一个 1 后面的元素更新为 1，上一次最后一个 0 后面的元素更新为 0 (注意这里的 2、1、0 顺序是重要的，因为当之前尚没有 2 或没有 1 时，0 会将它们覆盖掉 - 此时它们的最后一个索引均相同，此理同适用下面)，
若 nums[i] 为 1 则在上一次最后一个 2 后面的元素更新为 2，上一次最后一个 1 后面的元素更新为 1，
若 nums[i] 为 2 则在上一次最后一个 2 后面的元素更新为 2，
这里可以留意，当 2 或 1 只要出现过一次后，它们的 latest 索引就不会和 0 相同而是领先一位，2 与 1 之间的关系同理，所以当 nums 遍历到 lower 值时，lower 值虽然会覆盖掉一个 higher 值的元素但同时 higher 值也会往后更新一位，而因为顺序总是 2、1、0，所以总体总是正确更新、往右移，
而且总是有 nums 遍历到 i 时，更新和右移总是会到达 i，所以遍历到最后一位时接下来的更新和处理就会到达最后一位，即把 nums 数组全部更新、处理完毕。
*/



// My Solution:
class Solution {
    public void sortColors(int[] nums) {
        // two-pass
        int[] colors = new int[3];
        for (int num : nums) colors[num]++;
        for (int i=0; i<nums.length; i++) {
            if (colors[0]-- > 0) nums[i] = 0;
            else if (colors[1]-- > 0) nums[i] = 1;
            else if (colors[2]-- > 0) nums[i] = 2;
        }
    }
}