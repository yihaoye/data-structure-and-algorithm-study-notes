// Question:
/*
Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

Note:

The solution set must not contain duplicate triplets.

Example:

Given array nums = [-1, 0, 1, 2, -1, -4],

A solution set is:
[
  [-1, 0, 1],
  [-1, -1, 2]
]
*/

// Other's Solution:
class Solution {
    public List<List<Integer>> threeSum(int[] num) {
        Arrays.sort(num);
        List<List<Integer>> res = new LinkedList<>(); 
        for (int i = 0; i < num.length-2; i++) {
            if (i == 0 || (i > 0 && num[i] != num[i-1])) {
                int lo = i+1, hi = num.length-1, sum = 0 - num[i];
                while (lo < hi) {
                    if (num[lo] + num[hi] == sum) {
                        res.add(Arrays.asList(num[i], num[lo], num[hi]));
                        while (lo < hi && num[lo] == num[lo+1]) lo++;
                        while (lo < hi && num[hi] == num[hi-1]) hi--;
                        lo++; hi--;
                    } else if (num[lo] + num[hi] < sum) lo++;
                    else hi--;
            }
            }
        }
        return res;
    }
}


// My Solution: (not work yet)
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        // ss short for "solution set"
        List<List<Integer>> ss = new ArrayList<ArrayList<Integer>>(); 
        
        // four abstract cases: [0, 0, 0], [<0, 0, >0], [<0, <0, >0], [<0, >0, >0]
        // pre setting:
        int zeroCount = 0;
        List<Integer> negatives = new ArrayList<Integer>();
        List<Integer> positives = new ArrayList<Integer>();
        for (int num : nums) {
            if (num < 0) {
                negatives.add(num); // put in <0 array
            } else if (num == 0) {
                zeroCount++;
            } else {
                positives.add(num); // put in >0 array
            }
        }
        
        // case 1:
        if (zeroCount >= 3) {
            ss.add(new ArrayList<Integer>(0, 0, 0));
        }
        if (negatives.length == 0 || positives.length == 0) {
            return ss;
        }
        
        // more setting and sort (do quick or merge sort)
        Arrays.sort(negatives);
        Arrays.sort(positives);
        int preNum = 0;
        
        // case 2:
        for (int negative : negatives) {
            if (positives.indexOf(-negative) != -1 && negative != preNum) {
                ss.add(new ArrayList<Integer>(negative, 0, -negative));
            }
            preNum = negative; // prevent duplicate triplets.
        }
        
        // case 3:
        for (int positive : positives) {
            for (int[] otherTwo : findOtherTwo(positive, negatives)) {
                ss.add(new ArrayList<Integer>(positive, otherTwo[0], otherTwo[1]));
            }
        }
        
        // case 4:
        for (int negative : negatives) {
            for (int[] otherTwo : findOtherTwo(negative, positives)) {
                ss.add(new ArrayList<Integer>(negative, otherTwo[0], otherTwo[1]));
            }
        }
        
        return ss;
    }
    
    public List<List<Integer>> findOtherTwo(int num, int[] nums) {
        // nums is sortted int array
        List<List<Integer>> otherTwo = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < nums.length; i++) {
            if (num >0 && -2*nums[i] > num) {
                break;
            }
            if (num <0 && 2*nums[i] > -num) {
                break;
            }
            if (nums.indexOf(- num - nums[i]) != -1 && nums.indexOf(- num - nums[i]) > i) {
                otherTwo.add(new ArrayList<Integer>(nums[i], - num - nums[i]));
            } else {
                break;
            }
        }
        return otherTwo;
    }
}