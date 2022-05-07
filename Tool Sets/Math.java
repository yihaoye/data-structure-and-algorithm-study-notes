int a = 3;
int b = 4;
Math.min(a, b);
Math.min(12.123, 12.456);

int c = -1;
Math.abs(c);



// 手动相乘两个数 (每个数均以 int[] 形式表示)
// Leetcode 43
// https://leetcode.com/problems/multiply-strings/discuss/17605/Easiest-JAVA-Solution-with-Graph-Explanation
public int[] multiply(int[] num1, int[] num2) { // e.g.   multiply({4, 5}, {1, 2, 3})
    int m = num1.length, n = num2.length;
    int[] res = new int[m + n];

    for (int i = m - 1; i >= 0; i--) {
        for (int j = n - 1; j >= 0; j--) {
            int mul = num1[i] * num2[j];
            int p1 = i + j, p2 = i + j + 1;
            int sum = mul + res[p2];

            res[p1] += sum / 10; // Q: what if pos[p1] == 9 and sum > 10 ?  A: https://leetcode.com/problems/multiply-strings/discuss/17605/Easiest-JAVA-Solution-with-Graph-Explanation/17380
            res[p2] = (sum) % 10;
        }
    }

    return res;
}



// 矩阵相乘（https://zh.wikipedia.org/wiki/%E7%9F%A9%E9%99%A3%E4%B9%98%E6%B3%95）
// Leetcode Q311（C = AB）
public int[][] multiply(int[][] A, int[][] B) {
    int aH = A.length, aW = A[0].length, bW = B[0].length;
    int[][] C = new int[aH][bW];
    
    for (int ai=0; ai<aH; ai++) {
        for (int aj=0; aj<aW; aj++) {
            if (A[ai][aj] != 0) { // if 优化
                for (int bj=0; bj<bW; bj++) {
                    if (B[aj][bj] != 0) C[ai][bj] += A[ai][aj]*B[aj][bj]; // if 优化
                }
            }
        }
    }
    
    return C;
}



// 阶乘
public long factorial(int n) {
    long fact = 1;
    for (int i = 2; i <= n; i++) {
        fact = fact * i;
    }
    return fact;
}
