/*
Given an integer array nums of size n, return the minimum number of moves required to make all array elements equal.

In one move, you can increment n - 1 elements of the array by 1.

 

Example 1:

Input: nums = [1,2,3]
Output: 3
Explanation: Only three moves are needed (remember each move increments two elements):
[1,2,3]  =>  [2,3,3]  =>  [3,4,3]  =>  [4,4,4]
Example 2:

Input: nums = [1,1,1]
Output: 0
 

Constraints:

n == nums.length
1 <= nums.length <= 105
-109 <= nums[i] <= 109
The answer is guaranteed to fit in a 32-bit integer.
*/




// My Solution (差分数组，但是 timeout):
class Solution {
    public int minMoves(int[] nums) {   
        if (nums.length < 2) return 0;
        
        Arrays.sort(nums);
        
        // create difference array 差分数组
        int[] diffArr = new int[nums.length];
        diffArr[0] = nums[0];
        for (int i=1; i<nums.length; i++) diffArr[i] = nums[i] - nums[i-1];
        // 预提升性能，对 edge case 提升较大，比如 [1, 1000000] etc
        int count = nums[nums.length-1]-nums[nums.length-2];
        diffArr[0] += count;
        diffArr[nums.length-1] -= count;
        
        LinkedList<Integer> stack = new LinkedList<>(); // 提升 findNotIncIndex 的性能效率
        for (int i=1; i<diffArr.length; i++) if (diffArr[i] > 0) stack.addFirst(i);
        
        int notIncIndex = findNotIncIndex(stack);
        while (notIncIndex != -1) {
            notIncIndex = oneMove(diffArr, notIncIndex, stack);
            count++;
        }
        
        return count;
    }
    
    public int findNotIncIndex(LinkedList<Integer> stack) { // first not zero from right to left
        if (!stack.isEmpty()) return stack.removeFirst();
        return -1; // nums elements are all the same now (差分数组除首元素外其余为0)
    }
    
    public int oneMove(int[] diffArr, int index, LinkedList<Integer> stack) { // increase all element in nums except element with the index, apply fiff array to improve performance
        if (index == 0) { // impossible for this question, so should always return -1
            return -1;
        } else if (index == diffArr.length-1) {
            diffArr[0]++;
            diffArr[index]--;
            if (diffArr[index] > 0) return index;
            else return findNotIncIndex(stack);
        } else {
            diffArr[index]--;
            if (diffArr[index] > 0) stack.addFirst(index);
            diffArr[index+1]++;
            return index+1;
        }
    } 
}
