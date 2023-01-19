/**
You are given a 2D integer array stockPrices where stockPrices[i] = [dayi, pricei] indicates the price of the stock on day dayi is pricei. A line chart is created from the array by plotting the points on an XY plane with the X-axis representing the day and the Y-axis representing the price and connecting adjacent points. One such example is shown below:


Return the minimum number of lines needed to represent the line chart.

 

Example 1:


Input: stockPrices = [[1,7],[2,6],[3,5],[4,4],[5,4],[6,3],[7,2],[8,1]]
Output: 3
Explanation:
The diagram above represents the input, with the X-axis representing the day and Y-axis representing the price.
The following 3 lines can be drawn to represent the line chart:
- Line 1 (in red) from (1,7) to (4,4) passing through (1,7), (2,6), (3,5), and (4,4).
- Line 2 (in blue) from (4,4) to (5,4).
- Line 3 (in green) from (5,4) to (8,1) passing through (5,4), (6,3), (7,2), and (8,1).
It can be shown that it is not possible to represent the line chart using less than 3 lines.
Example 2:


Input: stockPrices = [[3,4],[1,2],[7,8],[2,3]]
Output: 1
Explanation:
As shown in the diagram above, the line chart can be represented with a single line.
 

Constraints:

1 <= stockPrices.length <= 10^5
stockPrices[i].length == 2
1 <= dayi, pricei <= 10^9
All dayi are distinct.
 */



// My Solution:
class Solution {
    public int minimumLines(int[][] stockPrices) {
        // 参考 LC Q149 数学(坐标系) + 对象 + HashSet
        Arrays.sort(stockPrices, (a, b) -> Integer.compare(a[0], b[0])); // stockPrices 并不保证 x 横坐标有序
        Set<Slope> set = new HashSet<>();
        for (int i=1; i<stockPrices.length; i++) {
            Slope slope = calSlope(stockPrices[i], stockPrices[i-1]);
            set.add(slope);
        }
        return set.size();
    }

    public Slope calSlope(int[] point, int[] prePoint) {
        int x1 = point[0], y1 = point[1], x2 = prePoint[0], y2 = prePoint[1];
        int preA = y1 - y2, preB = x2 - x1, preC = y2 * x1 - y1 * x2;
        int gcd = gcd(preA, preB);
        gcd = gcd(gcd, preC);
        if (gcd == 0) gcd = 1;
        int A = preA / gcd, B = preB / gcd, C = preC / gcd; // 必须保证除以最大公约数，否则后面的 Object hash 会不同
        if ((A < 0 && B >= 0) || (A < 0 && B <= 0)) { // format sign
            A *= -1; B *= -1; C *= -1;
        }
        
        return new Slope(A, B, C);
    }
    
    class Slope {
        int A = 0;
        int B = 0;
        int C = 0;
        
        public Slope(int A, int B, int C) {
            this.A = A;
            this.B = B;
            this.C = C;
        }
        
        @Override
        public int hashCode() {
            int hash = 1;
            hash = 31 * hash + A;
            hash = 31 * hash + B;
            hash = 31 * hash + C;
            return hash;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            Slope obj = (Slope) o;
            return this.A == obj.A && this.B == obj.B && this.C == obj.C;
        }
    }
    
    public int gcd(int a, int b) { //（欧几里得算法 - https://zh.wikipedia.org/zh-hans/%E8%BC%BE%E8%BD%89%E7%9B%B8%E9%99%A4%E6%B3%95）
        if (a == 0) return b;
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}



// Other's Solution:
class Solution {
    public int minimumLines(int[][] A) {
        // https://leetcode.com/problems/minimum-lines-to-represent-a-line-chart/solutions/2062141/java-c-python-compare-slopes-cross-product/?orderBy=most_votes
        int n = A.length, res = n - 1;
        Arrays.sort(A, (a, b) -> Integer.compare(a[0], b[0]));
        for (int i = 1; i < n - 1; ++i)
            if (1L * (A[i][0] - A[i - 1][0]) * (A[i + 1][1] - A[i][1]) == 1L * (A[i + 1][0] - A[i][0]) * (A[i][1] - A[i - 1][1]))
                res--;
        return res;
    }
}
