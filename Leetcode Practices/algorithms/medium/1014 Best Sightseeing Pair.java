/**
You are given an integer array values where values[i] represents the value of the ith sightseeing spot. Two sightseeing spots i and j have a distance j - i between them.

The score of a pair (i < j) of sightseeing spots is values[i] + values[j] + i - j: the sum of the values of the sightseeing spots, minus the distance between them.

Return the maximum score of a pair of sightseeing spots.

 

Example 1:

Input: values = [8,1,5,2,6]
Output: 11
Explanation: i = 0, j = 2, values[i] + values[j] + i - j = 8 + 5 + 0 - 2 = 11
Example 2:

Input: values = [1,2]
Output: 2
 

Constraints:

2 <= values.length <= 5 * 104
1 <= values[i] <= 1000

 */



// Other's Solution:
class Solution {
    public int maxScoreSightseeingPair(int[] values) {
        // DP - https://www.youtube.com/watch?v=iTyEa35Ve-U&list=PLLuMmzMTgVK6krji67w8tEAAud71nQQFe&index=5
        // score = values[i] + values[j] + i - j = (values[i] + i) + (values[j] - j);
        // for each i and j, (values[i] + i) and (values[j] - j) is always the same no matter pair with who
        // need maintain 2 var, maxScore[k] and maxValue(values[k] + k)
        // Time: O(N), Space: O(1)
        int maxScore = Integer.MIN_VALUE, maxValue = Integer.MIN_VALUE;
        for (int i=0; i<values.length; i++) {
            maxScore = Math.max(maxScore, maxValue + values[i] - i);
            maxValue = Math.max(maxValue, values[i] + i);
        }
        return maxScore;
    }
}
