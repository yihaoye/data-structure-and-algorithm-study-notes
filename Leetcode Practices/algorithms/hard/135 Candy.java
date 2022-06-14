/**
There are n children standing in a line. Each child is assigned a rating value given in the integer array ratings.

You are giving candies to these children subjected to the following requirements:

Each child must have at least one candy.
Children with a higher rating get more candies than their neighbors.
Return the minimum number of candies you need to have to distribute the candies to the children.

 

Example 1:

Input: ratings = [1,0,2]
Output: 5
Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
Example 2:

Input: ratings = [1,2,2]
Output: 4
Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
The third child gets 1 candy because it satisfies the above two conditions.
 

Constraints:

n == ratings.length
1 <= n <= 2 * 104
0 <= ratings[i] <= 2 * 104
 */



// Other's Solution:
class Solution {
    public int candy(int[] ratings) {
        // 贪心 - https://leetcode.cn/problems/candy/solution/fen-fa-tang-guo-by-leetcode-solution-f01p/
        // Time: O(N), Space: O(1)
        int n = ratings.length, inc = 1, dec = 0, pre = 1, res = 1;
        for (int i=1; i<n; i++) {
            if (ratings[i] >= ratings[i-1]) {
                dec = 0;
                pre = ratings[i] == ratings[i-1] ? 1 : pre + 1; // example 2: if neighbors children has same candy, the later child could be assigned less candy => if (ratings[i] == ratings[i-1]) pre = 1;
                res += pre;
                inc = pre;
            } else {
                dec++;
                if (dec == inc) dec++;
                res += dec;
                pre = 1;
            }
        }
        return res;
    }
}
