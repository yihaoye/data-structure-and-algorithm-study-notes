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