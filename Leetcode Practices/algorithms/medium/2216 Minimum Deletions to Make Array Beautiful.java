/*
You are given a 0-indexed integer array nums. The array nums is beautiful if:

nums.length is even.
nums[i] != nums[i + 1] for all i % 2 == 0.
Note that an empty array is considered beautiful.

You can delete any number of elements from nums. When you delete an element, all the elements to the right of the deleted element will be shifted one unit to the left to fill the gap created and all the elements to the left of the deleted element will remain unchanged.

Return the minimum number of elements to delete from nums to make it beautiful.

 

Example 1:

Input: nums = [1,1,2,3,5]
Output: 1
Explanation: You can delete either nums[0] or nums[1] to make nums = [1,2,3,5] which is beautiful. It can be proven you need at least 1 deletion to make nums beautiful.
Example 2:

Input: nums = [1,1,2,2,3,3]
Output: 2
Explanation: You can delete nums[0] and nums[5] to make nums = [1,2,2,3] which is beautiful. It can be proven you need at least 2 deletions to make nums beautiful.
 

Constraints:

1 <= nums.length <= 105
0 <= nums[i] <= 105
*/



// Other's Solution:
class Solution {
    public int minDeletion(int[] nums) {
        /* 
            贪心算法 - 显然最佳答案中同一个数不会连续出现三次及以上，因此我们先考虑同一个数连续出现不超过两次时，贪心算法是否正确。
            在该简化问题中，如果同一个数 a(i)和 a(i+1) 连续出现两次，而且这两个数都要保留，那么 i 必须是奇数（下标从 0 开始）。
            如果 i 是偶数，那么我们必须从小等于 (i + 1) 的下标里删掉一个。当删除下标 j 时，原下标大于 j 的数都会受影响（原本连续出现的两个数都能保留的，
            结果前面删了一下，下标的奇偶性改变了）。这个影响只会让答案不优，因此为了最小化影响，我们直接删除下标 (i + 1) 就好。
            我们的贪心算法在简化问题中就在做这个事。回到原问题，如果出现同一个数 a(i), a(i+1), a(i+2), ... 连续出现超过两次，
            当 i 是奇数时，贪心算法会删掉下标大等于 (i + 2) 的部分；当 i 是偶数时，贪心算法会删掉下标大等于 (i + 1) 的部分。
            其实就是把连续出现的数减到两次，以及简化问题中的操作这两个步骤结合起来一起做。因此贪心算法正确。 - https://leetcode-cn.com/problems/minimum-deletions-to-make-array-beautiful/solution/liang-chong-jie-fa-by-tsreaper-02yo/
            Time: O(N), Space: O(N)
        */
        List<Integer> res = new ArrayList<>();
        for (int num : nums) {
            if (res.size() % 2 == 0 || num != res.get(res.size()-1)) {
                res.add(num);
            }
        }
        return nums.length - (res.size() - res.size() % 2);
    }
}
