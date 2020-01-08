/*
Koko loves to eat bananas.  There are N piles of bananas, the i-th pile has piles[i] bananas.  The guards have gone and will come back in H hours.

Koko can decide her bananas-per-hour eating speed of K.  Each hour, she chooses some pile of bananas, and eats K bananas from that pile.  If the pile has less than K bananas, she eats all of them instead, and won't eat any more bananas during this hour.

Koko likes to eat slowly, but still wants to finish eating all the bananas before the guards come back.

Return the minimum integer K such that she can eat all the bananas within H hours.

 

Example 1:

Input: piles = [3,6,7,11], H = 8
Output: 4
Example 2:

Input: piles = [30,11,23,4,20], H = 5
Output: 30
Example 3:

Input: piles = [30,11,23,4,20], H = 6
Output: 23
 

Note:

1 <= piles.length <= 10^4
piles.length <= H <= 10^9
1 <= piles[i] <= 10^9
*/



// My Solution 1 (binary search):
class Solution {
    public int minEatingSpeed(int[] piles, int H) {
        int minK = 0, maxK = 0;
        // get piles max value as max speed
        for (int i=0; i<piles.length; i++) {
            if (piles[i] > maxK) maxK = piles[i];
        }
        
        while (minK <= maxK) {
            if (minK == maxK || maxK == 1) return maxK; // edge cases, if min speed can be 1 then prevent validate next midK since it will result in 0
            int midK = minK + (maxK - minK) / 2;
            if (validSpeed(piles, H, midK)) maxK = midK;
            else minK = midK + 1;
        }
        
        return minK - 1;
    }
    
    public boolean validSpeed(int[] piles, int H, int K) {
        int sum = 0;
        for (int pile : piles) {
            sum += pile/K;
            if (pile < K || pile%K != 0) sum += 1;
        }
        return sum <= H;
    }
}