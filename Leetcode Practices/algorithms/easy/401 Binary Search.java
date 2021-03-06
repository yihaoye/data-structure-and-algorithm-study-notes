/*
A binary watch has 4 LEDs on the top which represent the hours (0-11), and the 6 LEDs on the bottom represent the minutes (0-59).

Each LED represents a zero or one, with the least significant bit on the right.


For example, the above binary watch reads "3:25".

Given a non-negative integer n which represents the number of LEDs that are currently on, return all possible times the watch could represent.

Example:

Input: n = 1
Return: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"]
Note:
The order of output does not matter.
The hour must not contain a leading zero, for example "01:00" is not valid, it should be "1:00".
The minute must be consist of two digits and may contain a leading zero, for example "10:2" is not valid, it should be "10:02".
*/



// Other's Solution 1: (Backtracking)
public class Solution {
    public List<String> readBinaryWatch(int num) {
        List<String> res = new ArrayList<>();
        int[] nums1 = new int[]{8, 4, 2, 1}, nums2 = new int[]{32, 16, 8, 4, 2, 1};
        for(int i = 0; i <= num; i++) {
            List<Integer> list1 = generateDigit(nums1, i);
            List<Integer> list2 = generateDigit(nums2, num - i);
            for(int num1: list1) {
                if(num1 >= 12) continue;
                for(int num2: list2) {
                    if(num2 >= 60) continue;
                    res.add(num1 + ":" + (num2 < 10 ? "0" + num2 : num2));
                }
            }
        }
        return res;
    }

    private List<Integer> generateDigit(int[] nums, int count) {
        List<Integer> res = new ArrayList<>();
        generateDigitHelper(nums, count, 0, 0, res);
        return res;
    }

    private void generateDigitHelper(int[] nums, int count, int pos, int sum, List<Integer> res) {
        if(count == 0) {
            res.add(sum);
            return;
        }
        
        for(int i = pos; i < nums.length; i++) {
            generateDigitHelper(nums, count - 1, i + 1, sum + nums[i], res);    
        }
    }
}



// Other's Solution 2:
class Solution {
    public List<String> readBinaryWatch(int num) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 60; j++) {
                if (Integer.bitCount(i) + Integer.bitCount(j) == num) {
                    result.add(String.format("%d:%02d", i, j));
                }
            }
        }
        return result;
    }
}



// My Solution 1: (Really bad performance for scale in both time and space, so it is not practical)
class Solution {
    public List<String> readBinaryWatch(int num) {
        List<Integer> digitsMap = new ArrayList<>(Arrays.asList(8*100, 4*100, 2*100, 1*100, 32, 16, 8, 4, 2, 1));
        Set<String> set = new HashSet<>();
        num = num > digitsMap.size() ? digitsMap.size() : num; // ensure num <=  digitsMap.length
        for (int combination : combinationsFromDigitsMap(digitsMap, num)) {
            if (combination/100 >= 12 || combination%100 >=60) continue;
            set.add(parse(combination));
        }
        List<String> res = new ArrayList<>();
        for (String combination : set) {
            res.add(combination);
        }
        
        return res;
    }
    
    public List<Integer> combinationsFromDigitsMap(List<Integer> digitsMap, int num) {
        List<Integer> res = new ArrayList<>();
        
        if (num == 1) {
            for (int i : digitsMap) {
                res.add(i);
            }
        } else if (num > 5) { // Handle Time Limit Exceeded, if not consider time limit for leetcode, we can remove this block
            for (int i : digitsMap) {
                List<Integer> temp = new ArrayList<>(digitsMap);
                temp.remove(temp.indexOf(i));
                for (int j : combinationsFromDigitsMap(temp, 10-num-1)) {
                    res.add(1563-i-j); // 8*100+4*100+2*100+1*100+32+16+8+4+2+1-i-j
                }
            }
        } else if (num > 1) {
            for (int i : digitsMap) {
                List<Integer> temp = new ArrayList<>(digitsMap);
                temp.remove(temp.indexOf(i));
                for (int j : combinationsFromDigitsMap(temp, num-1)) {
                    res.add(i+j);
                }
            }
        } else {
            res.add(0);
        }
        
        return res;
    }
    
    public String parse(int combination) {
        return combination/100 + ":" + (combination%100 < 10 ? "0"+combination%100 : combination%100);
    }
}



// My Solution 2: (Backtracking)
class Solution {
    Integer[] hourMap = new Integer[]{8, 4, 2, 1}, minMap = new Integer[]{32, 16, 8, 4, 2, 1};
    List<String> res = new ArrayList<>();
    
    public List<String> readBinaryWatch(int num) {
        for (int i=0; i <= num; i++) {
            Set<Integer> setH = new HashSet<>(), setM = new HashSet<>(); 
            backTrack(hourMap, i, 0, 0, setH); // if i > hourMap.length, return null
            backTrack(minMap, num-i, 0, 0, setM);
            for(int h: setH) { // if i > hourMap.length, since null, no loop
                if(h >= 12) continue;
                for(int m: setM) { // if num-i > minMap.length, since null, no loop too, will not add anything
                    if(m >= 60) continue;
                    res.add(String.format("%d:%02d", h, m));
                }
            }
        }
        
        return res;
    }

    public void backTrack(Integer[] tMap, int num, int nextIndex, int preSum, Set<Integer> setT) {
        if (num > tMap.length) {
            return;
        } else if (num >= 1) {
            for (int i=nextIndex; i < tMap.length; i++) {
                backTrack(tMap, num-1, i+1, preSum+tMap[i], setT);
            }
        } else if (num == 0) {
            setT.add(preSum);
        }
    }
    
    // public void backTrack(Integer[] tMap, int num, int preSum, Set<Integer> setT) { // 这个不是真回溯，回溯不走重复路
    //     if (num > tMap.length) {
    //         return;
    //     } else if (num >= 1) {
    //         for (int t : tMap) {
    //              // 这段不是真回溯，回溯不走重复路
    //             List<Integer> temp = new ArrayList<Integer>(Arrays.asList(tMap));
    //             temp.remove(temp.indexOf(t));
    //             backTrack(temp.toArray(new Integer[temp.size()]), num-1, preSum+t, setT);
    //         }
    //     } else if (num == 0) {
    //         setT.add(preSum);
    //     }
    // }
}