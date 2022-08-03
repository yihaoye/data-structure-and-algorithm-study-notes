/**
Given an integer rowIndex, return the rowIndexth (0-indexed) row of the Pascal's triangle.

In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:

https://upload.wikimedia.org/wikipedia/commons/0/0d/PascalTriangleAnimated2.gif
 

Example 1:

Input: rowIndex = 3
Output: [1,3,3,1]
Example 2:

Input: rowIndex = 0
Output: [1]
Example 3:

Input: rowIndex = 1
Output: [1,1]
 

Constraints:

0 <= rowIndex <= 33
 

Follow up: Could you optimize your algorithm to use only O(rowIndex) extra space?
 */



// Other's Solution:
class Solution {
    public List<Integer> getRow(int rowIndex) {
        // https://leetcode.com/problems/pascals-triangle-ii/discuss/38420/Here-is-my-brief-O(k)-solution/36288
        List<Integer> res = new ArrayList<Integer>();
	    res.add(1);
        for (int i = 1; i <= rowIndex; i++) {
            for (int j = i-1; j >= 1; j--) {
                int tmp = res.get(j-1) + res.get(j);
                res.set(j, tmp);
            }
            res.add(1);
        }
        return res;
    }
}
