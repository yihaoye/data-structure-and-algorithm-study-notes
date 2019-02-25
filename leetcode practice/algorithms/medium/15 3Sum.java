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
        // sort (do quick or merge sort)
        Arrays.sort(nums);
        // ss short for "solution set"
        List<List<Integer>> ss = new LinkedList<>(); 
        
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
            ss.add(Arrays.asList(0, 0, 0));
        }
        if (negatives.size() == 0 || positives.size() == 0) {
            return ss;
        }
        
        // more setting
        int preNum = 0;
        
        // case 2 and case 4:
        for (int negative : negatives) {
            // case 2:
            if (positives.indexOf(-negative) != -1 && negative != preNum) {
                ss.add(Arrays.asList(negative, 0, -negative));
            }
            preNum = negative; // prevent duplicate triplets.

            // case 4:
            for (List<Integer> otherTwo : findOtherTwo(negative, positives)) {
                ss.add(Arrays.asList(negative, otherTwo.get(0), otherTwo.get(1)));
            }
        }
        
        // case 3:
        for (int positive : positives) {
            for (List<Integer> otherTwo : findOtherTwo(positive, negatives)) {
                ss.add(Arrays.asList(otherTwo.get(0), otherTwo.get(1), positive));
            }
        }
        
        return ss;
    }
    
    public List<List<Integer>> findOtherTwo(int num, List<Integer> nums) {
        // nums is sortted int array
        List<List<Integer>> otherTwo = new LinkedList<>();
        for (int i = 0; i < nums.size(); i++) {
            if (num >0 && -2*nums.get(i) > num) {
                break;
            }
            if (num <0 && 2*nums.get(i) > -num) {
                break;
            }
            if (nums.indexOf(- num - nums.get(i)) != -1 && nums.indexOf(- num - nums.get(i)) > i) {
                otherTwo.add(Arrays.asList(nums.get(i), - num - nums.get(i)));
            } else {
                break;
            }
        }
        return otherTwo;
    }
}