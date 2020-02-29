/*
Given an unsorted integer array, find the smallest missing positive integer.

Example 1:

Input: [1,2,0]
Output: 3
Example 2:

Input: [3,4,-1,1]
Output: 2
Example 3:

Input: [7,8,9,11,12]
Output: 1
Note:

Your algorithm should run in O(n) time and uses constant extra space.
*/



// My Solution (use nums.length space):
class Solution {
    public int firstMissingPositive(int[] nums) {
        // the smallest missing positive integer must within range [1, nums.length+1]
        int len = nums.length;
        int[] bucket = new int[len];
        for (int i=0; i<len; i++) {
            if (nums[i] <= len && nums[i] >= 1) bucket[nums[i]-1] = 1;
        }
        for (int i=0; i<len; i++) {
            if (bucket[i] == 0) return i+1;
        }
        return len+1;
    }
}



// Other's Solution (O(n) time, O(1) space):
public class Solution {
    public int firstMissingPositive(int[] nums) {
        // The key here is to use swapping to keep constant space and also make use of the length of the array, which means there can be at most n positive integers. 
        // So each time we encounter an valid integer, find its correct position and swap. Otherwise we continue.
        int i = 0;
        while(i < nums.length){
            if(nums[i] == i+1 || nums[i] <= 0 || nums[i] > nums.length) i++;
            else if(nums[i] != nums[nums[i]-1]) swap(nums, i, nums[i]-1);  
            else i++;
        }
        // Q: why if(nums[i] != nums[nums[i]-1]) instead of if(i != nums[i]-1) ? Aren't they the same?
        // A: Almost the same, but like [3,2,3,4], when i = 0, A[0] = 3, which should be put on position i = 2 where also A[2] = 3. Thus there is no need to do swap and go to else statement i++ for next check or it will go into endless loop
        //    首先，所求答案 the smallest missing positive integer 一定在范围 [1, nums.length+1] 里
        //    `else if(nums[i] != nums[nums[i]-1]) swap(nums, i, nums[i]-1);` 其实意思就是经过 if 的筛选第 i 轮里的值必定是某个 nums 的 valid 的索引 - 索引为 nums[i]-1 的元素的正确值且必定不是索引 i 元素里的正确值，此时对比检查索引为 nums[i]-1 的元素的值是否正确（而不是检查索引为 i 的元素值是否正确 - 这一行为在 if 里已经执行了，只是那里如果检测错误不做寻找数组是否存在正确值更正，更正是通过这里被动进行的），不正确则与 nums[i] 互换进行更正，nums[i]-1 不可能大于 nums.length 或小于 0 (前面 skip 了) 所以不会有数组索引错误。
        //    注意 else if 里不进行 i++ 是因为被换来的 nums[nums[i]-1] 的值可能是 nums[i] 的正确值或第 3 个索引的正确值，所以最后再检查一次如果是第 3 个索引的唯一正确值就换给该索引元素，否则该索引将失去更正机会，若是索引 i 的值则会在 if 第 1 行里 skip 掉了，如果既不是索引 i 或第 3 索引的值则说明 <= 0 或 >= nums.length 从而进入最后的 else 行 skip 掉。
        //    nums[i] 有可能有 3 种情况获取正确值，即原值就是正确的，或者在之前 (< 现轮 i) 某轮被检测到不正确而更正，或者之后某轮被检测到不正确而更正。如果这 3 次都没被检测出来证明原数组里没有该索引应有的数值，且如果该索引是第 1 个这种情况的索引，那它的索引键+1就是最小缺失正整数。

        i = 0;
        while(i < nums.length && nums[i] == i+1) i++;
        return i+1;
    }
    
    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
