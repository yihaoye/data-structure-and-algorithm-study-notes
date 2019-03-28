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


// Other's Solution 1:
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> res  = new HashSet<>();
        if (nums.length == 0) return new ArrayList<>(res);
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum == 0) res.add(Arrays.asList(nums[i], nums[j++], nums[k--]));
                else if (sum > 0) k--;
                else if (sum < 0) j++;
            }
        }
        return new ArrayList<>(res);
    }
}


// Other's Solution 2:
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new LinkedList<>(); 
        for (int i = 0; i < nums.length-2; i++) {
            if (i == 0 || (i > 0 && nums[i] != nums[i-1])) {
                int lo = i+1, hi = nums.length-1, sum = 0 - nums[i];
                while (lo < hi) {
                    if (nums[lo] + nums[hi] == sum) {
                        res.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
                        while (lo < hi && nums[lo] == nums[lo+1]) lo++;
                        while (lo < hi && nums[hi] == nums[hi-1]) hi--;
                        lo++; hi--;
                    } else if (nums[lo] + nums[hi] < sum) lo++;
                    else hi--;
                }
            }
        }
        return res;
    }
}


// My Solution: 
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        // ss short for "solution set"
        List<List<Integer>> ss = new LinkedList<>(); 
        if (nums.length < 3) {
            return ss;
        }
        
        // sort (do quick or merge sort)
        Arrays.sort(nums);
        
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
            if (zeroCount > 0 && positives.indexOf(-negative) != -1 && negative != preNum) {
                ss.add(Arrays.asList(negative, 0, -negative));
            }
            preNum = negative; // prevent duplicate triplets.

            // case 4:
            ss = addOtherTwo(negative, positives, ss);
        }
        
        // case 3:
        for (int positive : positives) {
            ss = addOtherTwo(positive, negatives, ss);
        }
        
        return ss;
    }
    
    public List<List<Integer>> addOtherTwo(int num, List<Integer> nums, List<List<Integer>> ss) {
        // nums is sortted int array
        for (int i = 0; i < nums.size(); i++) {
            if (num >0 && -2*nums.get(i) < num) {
                break;
            }
            if (num <0 && 2*nums.get(i) > -num) {
                break;
            }
            if ((2*nums.get(i) == -num && Collections.frequency(nums, - num - nums.get(i)) > 1) || (2*nums.get(i) != -num && Collections.frequency(nums, - num - nums.get(i)) > 0)) {
                if (!ss.contains(Arrays.asList(num, nums.get(i), - num - nums.get(i)))) {
                    ss.add(Arrays.asList(num, nums.get(i), - num - nums.get(i)));
                }
            }
        }
        return ss;
    }
}