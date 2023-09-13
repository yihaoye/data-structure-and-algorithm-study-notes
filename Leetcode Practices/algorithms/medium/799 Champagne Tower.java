/*
We stack glasses in a pyramid, where the first row has 1 glass, the second row has 2 glasses, and so on until the 100th row.  Each glass holds one cup of champagne.

Then, some champagne is poured into the first glass at the top.  When the topmost glass is full, any excess liquid poured will fall equally to the glass immediately to the left and right of it.  When those glasses become full, any excess champagne will fall equally to the left and right of those glasses, and so on.  (A glass at the bottom row has its excess champagne fall on the floor.)

For example, after one cup of champagne is poured, the top most glass is full.  After two cups of champagne are poured, the two glasses on the second row are half full.  After three cups of champagne are poured, those two cups become full - there are 3 full glasses total now.  After four cups of champagne are poured, the third row has the middle glass half full, and the two outside glasses are a quarter full, as pictured below.


https://s3-lc-upload.s3.amazonaws.com/uploads/2018/03/09/tower.png
Now after pouring some non-negative integer cups of champagne, return how full the jth glass in the ith row is (both i and j are 0-indexed.)

 

Example 1:

Input: poured = 1, query_row = 1, query_glass = 1
Output: 0.00000
Explanation: We poured 1 cup of champange to the top glass of the tower (which is indexed as (0, 0)). There will be no excess liquid so all the glasses under the top glass will remain empty.

Example 2:

Input: poured = 2, query_row = 1, query_glass = 1
Output: 0.50000
Explanation: We poured 2 cups of champange to the top glass of the tower (which is indexed as (0, 0)). There is one cup of excess liquid. The glass indexed as (1, 0) and the glass indexed as (1, 1) will share the excess liquid equally, and each will get half cup of champange.

Example 3:

Input: poured = 100000009, query_row = 33, query_glass = 17
Output: 1.00000
 

Constraints:
0 <= poured <= 10^9
0 <= query_glass <= query_row < 100
*/



// Other's Solution:
class Solution {
    public double champagneTower(int poured, int query_row, int query_glass) {
        // DP - https://leetcode.com/problems/champagne-tower/solutions/1818599/full-visual-explanation-dp-beginner-friendly-easy-and-simple-c/
        double[] currRow = new double[]{ poured };
        
        for (int i=0; i<=query_row; i++) { // we need to make the dp matrix only till query row. No need to do after that
            double[] nextRow = new double[i + 2]; // If we are at row 0, row 1 will have 2 glasses. So next row will have currRow number + 2 number of glasses.
            for (int j=0; j<=i; j++) { // each row will have currRow number + 1 number of glasses.
                if (currRow[j] < 1) continue;
                // if the champagne from the current glass is being overflowed.
                nextRow[j] += (currRow[j] - 1) / 2.0; // fill the left glass with the overflowing champagne
                nextRow[j + 1] += (currRow[j] - 1) / 2.0; // fill the right glass with the overflowing champagne
                currRow[j] = 1; // current glass will store only 1 cup of champagne
            }
            if (i != query_row) currRow = nextRow; // change the currRow for the next iteration. But if we have already reached the query_row, then the next iteration will not even take place, so the currRow is the query_row itself. So don't change as we need the currRow only.
        }
        
        return currRow[query_glass];
    }
}
