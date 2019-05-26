/*
Given a sorted array of integers nums and integer values a, b and c. Apply a quadratic function of the form f(x) = ax2 + bx + c to each element x in the array.

The returned array must be in sorted order.

Expected time complexity: O(n)

Example 1:

Input: nums = [-4,-2,2,4], a = 1, b = 3, c = 5
Output: [3,9,15,33]
Example 2:

Input: nums = [-4,-2,2,4], a = -1, b = 3, c = 5
Output: [-23,-5,1,7]
*/



// My Solution:
class Solution {
    public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        // Two Pointer solution (since expect O(n), which mean can not use sort, but bucket sort may still work)
        // Also need to use math tool: 
        // Monotonicity of Quadratic Function (2ax+b)=0 (x=-b/2a i.e. the peak/bottom), so if a>0 then trend is "V" while if a<0 trend is "A" (or say "^"), but if a==0, then if b>0 trend is "/" if b<0 trend is "\", if a==b==0, then result is always c.
        int len = nums.length;
        int[] res = new int[len];
        if (a != 0) {
            int midIndex = 0;
            for (int i=0; i<len; i++) {
                if (nums[i] <= (float)-b/(2*(float)a)) midIndex = i;
                nums[i] = exeQuadratic(nums[i], a, b, c);
            }
            if (a > 0) {
                processV(res, nums, midIndex, len);
            } else {
                processA(res, nums, midIndex, len);
            }
        } else {
            if (b != 0) {
                if (b > 0) {
                    for (int i=0; i<len; i++) {
                        res[i] = nums[i]*b + c;
                    }
                } else {
                    for (int i=0; i<len; i++) {
                        res[i] = nums[len-1-i]*b + c;
                    }
                }
            } else {
                for (int i=0; i<len; i++) {
                    res[i] = c;
                }
            }
        }
        
        return res;
    }
    
    public int exeQuadratic(int x, int a, int b, int c) {
        return a*x*x + b*x + c;
    }
    
    public void processA(int[] res, int[] nums, int midIndex, int len) {
        for (int i=0, j=0, k=len-1; i<len; i++) {
            if (j > midIndex) {
                res[i] = nums[k];
                k--;
            } else if (k <= midIndex) {
                res[i] = nums[j];
                j++;
            } else {
                if (nums[j] <= nums[k]) {
                    res[i] = nums[j];
                    j++;
                } else {
                    res[i] = nums[k];
                    k--;
                }
            }
        }
    }
    
    public void processV(int[] res, int[] nums, int midIndex, int len) {
        for (int i=0, j=midIndex, k=midIndex+1; i<len; i++) {
            if (j < 0) {
                res[i] = nums[k];
                k++;
            } else if (k == len) {
                res[i] = nums[j];
                j--;
            } else {
                if (nums[j] <= nums[k]) {
                    res[i] = nums[j];
                    j--;
                } else {
                    res[i] = nums[k];
                    k++;
                }
            }
        }
    }
}