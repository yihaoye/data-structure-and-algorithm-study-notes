/*
X is a good number if after rotating each digit individually by 180 degrees, we get a valid number that is different from X.  Each digit must be rotated - we cannot choose to leave it alone.

A number is valid if each digit remains a digit after rotation. 0, 1, and 8 rotate to themselves; 2 and 5 rotate to each other; 6 and 9 rotate to each other, and the rest of the numbers do not rotate to any other number and become invalid.

Now given a positive number N, how many numbers X from 1 to N are good?

Example:
Input: 10
Output: 4
Explanation: 
There are four good numbers in the range [1, 10] : 2, 5, 6, 9.
Note that 1 and 10 are not good numbers, since they remain unchanged after rotating.
Note:

N  will be in range [1, 10000].
*/



// My Solution:
class Solution {
    int[] goodDigits = {2, 5, 6, 9};
    int[] validDigits = {0, 1, 8};
    // rotatedDigit must contain at least one good digit and >= 0 valid digit (as rest). 
    
    public int rotatedDigits(int N) {
        int count = 0;
        for (int i=1; i<=N; i++) {
            if (isGoodNumber(i)) count++;
        }
        return count;
    }
    
    public boolean isGoodNumber(int num) {
        int digit=0, countGood=0;
        while (num != 0) {
            digit = num%10;
            num /= 10;
            if (!isGoodDigit(digit) && !isValidDigit(digit)) return false;
            if (isGoodDigit(digit)) countGood++;
        }
        if (countGood == 0) return false;
        return true;
    }
    
    public boolean isGoodDigit(int digit) {
        for (int goodDigit : goodDigits) if (digit == goodDigit) return true;
        return false;
    }
    
    public boolean isValidDigit(int digit) {
        for (int validDigit : validDigits) if (digit == validDigit) return true;
        return false;
    }
}