/**
You are given a positive integer num. You may swap any two digits of num that have the same parity (i.e. both odd digits or both even digits).

Return the largest possible value of num after any number of swaps.

 

Example 1:

Input: num = 1234
Output: 3412
Explanation: Swap the digit 3 with the digit 1, this results in the number 3214.
Swap the digit 2 with the digit 4, this results in the number 3412.
Note that there may be other sequences of swaps but it can be shown that 3412 is the largest possible number.
Also note that we may not swap the digit 4 with the digit 1 since they are of different parities.
Example 2:

Input: num = 65875
Output: 87655
Explanation: Swap the digit 8 with the digit 6, this results in the number 85675.
Swap the first digit 5 with the digit 7, this results in the number 87655.
Note that there may be other sequences of swaps but it can be shown that 87655 is the largest possible number.
 

Constraints:

1 <= num <= 109
 */



// My Solution:
class Solution {
    public int largestInteger(int num) {
        List<Integer> oddList = new ArrayList<>();
        List<Integer> evenList = new ArrayList<>();
        String str = String.valueOf(num);
        for (char c : str.toCharArray()) {
            int i = c - '0';
            if (i % 2 == 0) evenList.add(i);
            else oddList.add(i);
        }
        oddList.sort(Comparator.reverseOrder());
        evenList.sort(Comparator.reverseOrder());
        int res = 0, oddIndex = 0, evenIndex = 0;
        for (char c : str.toCharArray()) {
            int i = c - '0';
            if (i % 2 == 0) {
                res = res * 10 + evenList.get(evenIndex++);
            } else {
                res = res * 10 + oddList.get(oddIndex++);
            }
        }
        return res;
    }
}
