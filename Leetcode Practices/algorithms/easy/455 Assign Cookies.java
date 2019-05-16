/*
Assume you are an awesome parent and want to give your children some cookies. But, you should give each child at most one cookie. Each child i has a greed factor gi, which is the minimum size of a cookie that the child will be content with; and each cookie j has a size sj. If sj >= gi, we can assign the cookie j to the child i, and the child i will be content. Your goal is to maximize the number of your content children and output the maximum number.

Note:
You may assume the greed factor is always positive. 
You cannot assign more than one cookie to one child.

Example 1:
Input: [1,2,3], [1,1]

Output: 1

Explanation: You have 3 children and 2 cookies. The greed factors of 3 children are 1, 2, 3. 
And even though you have 2 cookies, since their size is both 1, you could only make the child whose greed factor is 1 content.
You need to output 1.
Example 2:
Input: [1,2], [1,2,3]

Output: 2

Explanation: You have 2 children and 3 cookies. The greed factors of 2 children are 1, 2. 
You have 3 cookies and their sizes are big enough to gratify all of the children, 
You need to output 2.
*/



// My Solution:
class Solution {
    public int findContentChildren(int[] g, int[] s) {
        // greedy algorithm
        // sort two and each time get min s to min g (s >= g), the left use Recursive/Iterative to handle, until one stuck, then return the sum res
        int res = 0;
        Arrays.sort(g);
        Arrays.sort(s);
        int lenG = g.length-1;
        int lenS = s.length-1;
        int indexG = 0;
        int indexS = 0;
        while (indexS <= lenS && indexG <= lenG) {
            if (s[indexS] >= g[indexG]) {
                res++;
                indexS++;
                indexG++;
            } else {
                indexS++;
            }
        }
        
        return res;
    }
}
// 这道题尝试过把数组转成ArrayList再排序等等，List会大大增加时间消耗。