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



// GCD 最大公约数
public int gcd(int a, int b) { //（欧几里得算法 - https://zh.wikipedia.org/zh-hans/%E8%BC%BE%E8%BD%89%E7%9B%B8%E9%99%A4%E6%B3%95）
    if (a == 0) return b; // 若 a 或 b 的任意一个为 0，则最大公约数就是另一个数
    while (b != 0) {
        int temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}
// 证明过程：https://www.youtube.com/watch?v=WGO4Tqx5owg（包括贝祖定理）
// 若是求 3 个或更多个数的最大公约数，则方法是先对头两个数求出其最大公约数，然后再以该最大公约数与第 3 个数作为 a、b 求第二轮的最大公约数，如此类推。例题如 Leetcode Q149。



// LCM 最小公倍数 - https://stackoverflow.com/a/4202114/6481829
public long lcm(long a, long b) {
    return a * b / gcd(a, b); // https://zh.wikipedia.org/wiki/%E6%9C%80%E5%B0%8F%E5%85%AC%E5%80%8D%E6%95%B8#JAVA
}

public long lcm(long[] input) {
    long result = input[0];
    for (int i = 1; i < input.length; i++) result = lcm(result, input[i]);
    return result;
}



// 高效 x^n - k （并且对 10^9+7 取模）参考 https://leetcode.com/problems/count-collisions-of-monkeys-on-a-polygon/solutions/3111664/java-c-python-should-be-pow-2-n-4/?orderBy=most_votes
// Time O(logn), Space O(1)
public int fast_power(int x, int n, int k) {
    long res = 1, base = x, mod = (long) 1e9 + 7;
    while (n > 0) {
        if (n % 2 == 1) res = res * base % mod;
        base = base * base % mod;
        n /= 2; // or n >>= 1;
    }
    return (int) ((res - k + mod) % mod);
}
