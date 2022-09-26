/**
Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane, return the maximum number of points that lie on the same straight line.

 

Example 1:
https://assets.leetcode.com/uploads/2021/02/25/plane1.jpg

Input: points = [[1,1],[2,2],[3,3]]
Output: 3

Example 2:
https://assets.leetcode.com/uploads/2021/02/25/plane2.jpg

Input: points = [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
Output: 4
 

Constraints:

1 <= points.length <= 300
points[i].length == 2
-10^4 <= xi, yi <= 10^4
All the points are unique.
 */



// My Solution:
class Solution {
    public int maxPoints(int[][] points) {
        // DFS + Math (两点确定直线公式 A*x + B*y + C = 0 以及最大公约数 - 用于处理分数情况) + Object & hash + HashMap <ABC_hash, count>
        // Time: O(N^2) - 1 + 2 + ... + 300, Space: 最多 O(N^2)
        if (points.length == 1) return 1; // edge case
        Map<Slope, Integer> map = new HashMap<>();
        List<int[]> processed = new ArrayList<>(); // processed point, used for dfs
        int res = 0;
        for (int[] point : points) {
            Set<Slope> preCal = new HashSet<>();
            for (int[] prePoint : processed) {
                Slope slope = calSlope(point, prePoint);
                if (preCal.contains(slope)) continue; // 避免同一个点重复计入同一条线中
                preCal.add(slope);
                
                int cnt = map.getOrDefault(slope, 1) + 1; // 每条线的 count 都没有算第一个对比点，需要 default 里补上 1
                map.put(slope, cnt);
                res = Math.max(res, cnt);
            }
            processed.add(point);
        }
        
        return res;
    }
    
    public Slope calSlope(int[] point, int[] prePoint) {
        // 斜率 k = yDelta / xDelta = kNumerator / kDenominator
        // y = k*x + b && A*x + B*y + C = 0 -> y = - (A/B)*x - C/B
        // yDelta / xDelta = - (A / B) -> A = -yDelta = y1 - y2, B = xDelta = x2 - x1
        // 计算 b -> (y2 - b) / (x2 - 0) = (y1 - b) / (x1 - 0)
        // y2*x1 - b*x1 = y1*x2 - b*x2 -> (y2*x1 - y1*x2) / (x1 - x2) = b
        // C = - b*B = y2*x1 - y1*x2
        int x1 = point[0], y1 = point[1], x2 = prePoint[0], y2 = prePoint[1];
        int preA = y1 - y2, preB = x2 - x1, preC = y2 * x1 - y1 * x2; // preA = -yDelta，preB = xDelta
        int gcd = gcd(preA, preB);
        gcd = gcd(gcd, preC);
        if (gcd == 0) gcd = 1; // 避免除以 0，但不能在前面两行处理，因为如果参数其中一个为 0 则公约数为另一个数，如果前面 0 改为 1 则公约数会变成 1
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
