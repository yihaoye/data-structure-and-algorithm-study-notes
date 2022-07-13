/**
Given a non-empty array of non-negative integers nums, the degree of this array is defined as the maximum frequency of any one of its elements.

Your task is to find the smallest possible length of a (contiguous) subarray of nums, that has the same degree as nums.

 

Example 1:

Input: nums = [1,2,2,3,1]
Output: 2
Explanation: 
The input array has a degree of 2 because both elements 1 and 2 appear twice.
Of the subarrays that have the same degree:
[1, 2, 2, 3, 1], [1, 2, 2, 3], [2, 2, 3, 1], [1, 2, 2], [2, 2, 3], [2, 2]
The shortest length is 2. So return 2.
Example 2:

Input: nums = [1,2,2,3,1,4,2]
Output: 6
Explanation: 
The degree is 3 because the element 2 is repeated 3 times.
So [2,2,3,1,4,2] is the shortest subarray, therefore returning 6.
 

Constraints:

nums.length will be between 1 and 50,000.
nums[i] will be an integer between 0 and 49,999.
 */



// My Solution:
class Solution {
    public int findShortestSubArray(int[] nums) {
        // HashMap+自定义对象 - 找贡献出最高 degree 右左右索引距离最短的数
        // Time: O(N), Space: O(M)
        int resNum = -1, maxDegree = 0, lenOfResNum = 50001; // resNum must has maxDegree and with minLen
        Map<Integer, Num> map = new HashMap<>(); // key: num, value num's Object
        for (int i=0; i<nums.length; i++) {
            Num numObj = map.getOrDefault(nums[i], new Num(0, i, i));
            numObj.count++;
            numObj.right = i;
            map.put(nums[i], numObj);
            if (numObj.count > maxDegree || (numObj.count == maxDegree && numObj.right - numObj.left + 1 < lenOfResNum)) {
                resNum = nums[i];
                maxDegree = numObj.count;
                lenOfResNum = numObj.right - numObj.left + 1;
            }
        }
        return lenOfResNum;
    }
    
    class Num {
        int count = 0;
        int left = -1;
        int right = -1;
        
        public Num(int count, int left, int right) {
            this.count = count;
            this.left = left;
            this.right = right;
        }
    }
}
