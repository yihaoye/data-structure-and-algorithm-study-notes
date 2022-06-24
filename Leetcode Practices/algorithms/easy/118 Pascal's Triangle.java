/**
Given an integer numRows, return the first numRows of Pascal's triangle.

In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:

https://upload.wikimedia.org/wikipedia/commons/0/0d/PascalTriangleAnimated2.gif
 

Example 1:

Input: numRows = 5
Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
Example 2:

Input: numRows = 1
Output: [[1]]
 

Constraints:

1 <= numRows <= 30
 */



// My Solution:
class Solution {
    public List<List<Integer>> generate(int numRows) {
        // 模拟+迭代
        // Time: O(N), Space: O(N), N - numRows 所决定的总元素数量
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>() {{ add(1); }});
        while (--numRows > 0) {
            List<Integer> lastLayer = res.get(res.size()-1);
            List<Integer> newLayer = new ArrayList<>();
            newLayer.add(1);
            for (int i=1; i<lastLayer.size(); i++) {
                newLayer.add(lastLayer.get(i-1) + lastLayer.get(i));
            }
            newLayer.add(1);
            res.add(newLayer);
        }
        return res;
    }
}
