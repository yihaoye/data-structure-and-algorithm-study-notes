/**
You are given a 0-indexed integer array nums of length n.

A split at an index i where 0 <= i <= n - 2 is called valid if the product of the first i + 1 elements and the product of the remaining elements are coprime.

For example, if nums = [2, 3, 3], then a split at the index i = 0 is valid because 2 and 9 are coprime, while a split at the index i = 1 is not valid because 6 and 3 are not coprime. A split at the index i = 2 is not valid because i == n - 1.
Return the smallest index i at which the array can be split validly or -1 if there is no such split.

Two values val1 and val2 are coprime if gcd(val1, val2) == 1 where gcd(val1, val2) is the greatest common divisor of val1 and val2.

 

Example 1:
https://assets.leetcode.com/uploads/2022/12/14/second.PNG

Input: nums = [4,7,8,15,3,5]
Output: 2
Explanation: The table above shows the values of the product of the first i + 1 elements, the remaining elements, and their gcd at each index i.
The only valid split is at index 2.

Example 2:
https://assets.leetcode.com/uploads/2022/12/14/capture.PNG

Input: nums = [4,7,15,8,3,5]
Output: -1
Explanation: The table above shows the values of the product of the first i + 1 elements, the remaining elements, and their gcd at each index i.
There is no valid split.
 

Constraints:

n == nums.length
1 <= n <= 10^4
1 <= nums[i] <= 10^6
 */



// My Solution:
class Solution {    
    public int findValidSplit(int[] nums) {
        // 数学(数论) + 前缀质因素分解哈希表 + 优化
        Map<Integer, Integer> all = new HashMap<>(); // <prime, last_index_num_can_mod_it> of all
        for (int i=0; i<nums.length; i++) {
            updatePrimes(i, nums[i], all, null);
        }
        
        Map<Integer, Integer> tmp = new HashMap<>(); // <prime, last_index_num_can_mod_it> of current
        Set<Integer> diff = new HashSet<>(); // diff keys
        for (int i=0; i<nums.length-1; i++) {
            updatePrimes(i, nums[i], tmp, diff);
            if (check(all, tmp, diff)) return i;
        }
        return -1;
    }
    
    public void updatePrimes(int idx, int num, Map<Integer, Integer> map, Set<Integer> diff) {
        for (int i=2; i<=1000; i+=1+(i%2)) { // num 分解质因素
            if (num < i) break;
            if (num % i == 0) {
                map.put(i, idx);
                if (diff != null) diff.add(i);
                while (num % i == 0) num /= i;
            }
        }
        if (num > 1) {
            map.put(num, idx);
            if (diff != null) diff.add(num);
        }
    }
    
    public boolean check(Map<Integer, Integer> all, Map<Integer, Integer> tmp, Set<Integer> diff) {
        Set<Integer> diffCopy = new HashSet<>();
        while (diff.size() > 0) {
            int key = diff.iterator().next();
            diff.remove(key);
            if ((int) tmp.get(key) == (int) all.get(key)) { // no need to keep anymore to improve performance
                tmp.remove(key);
                all.remove(key);
            } else {
                diffCopy.add(key);
            }
        }
        diff = diffCopy;
        if (tmp.size() == 0) return true;
        return false;
    }
}
