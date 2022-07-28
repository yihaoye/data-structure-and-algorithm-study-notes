/**
Given an n x n binary matrix image, flip the image horizontally, then invert it, and return the resulting image.

To flip an image horizontally means that each row of the image is reversed.

For example, flipping [1,1,0] horizontally results in [0,1,1].
To invert an image means that each 0 is replaced by 1, and each 1 is replaced by 0.

For example, inverting [0,1,1] results in [1,0,0].
 

Example 1:

Input: image = [[1,1,0],[1,0,1],[0,0,0]]
Output: [[1,0,0],[0,1,0],[1,1,1]]
Explanation: First reverse each row: [[0,1,1],[1,0,1],[0,0,0]].
Then, invert the image: [[1,0,0],[0,1,0],[1,1,1]]
Example 2:

Input: image = [[1,1,0,0],[1,0,0,1],[0,1,1,1],[1,0,1,0]]
Output: [[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]
Explanation: First reverse each row: [[0,0,1,1],[1,0,0,1],[1,1,1,0],[0,1,0,1]].
Then invert the image: [[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]
 

Constraints:

n == image.length
n == image[i].length
1 <= n <= 20
images[i][j] is either 0 or 1.
 */



// My Solution:
class Solution {
    public int[][] flipAndInvertImage(int[][] image) {
        // 模拟 + 双指针
        // Time: O(N), Space: O(1), N = n^2
        int n = image.length;
        for (int i=0; i<n; i++) {
            for (int j=0; j<n/2; j++) {
                int l = image[i][j];
                int r = image[i][n-1-j];
                l = l == 0 ? 1 : 0;
                r = r == 0 ? 1 : 0;
                image[i][j] = r;
                image[i][n-1-j] = l;
            }
            if (n%2 == 1) image[i][n/2] = image[i][n/2] == 0 ? 1 : 0;
        }
        
        return image;
    }
}
