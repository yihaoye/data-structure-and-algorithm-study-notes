// 下方有些高阶数学函数已有 Java 内置实现，具体参考 ./BigNumber.java



int a = 3;
int b = 4;
Math.min(a, b);
Math.min(12.123, 12.456);

int c = -1;
Math.abs(c);



// 向量、几何
// 以下在 LC Q587 得到应用
/**
 * 将两个点转换为一个向量，例如点 a(x1, y1), b(x2, y2) 则转化为向量 a ——> b = (x2-x1, y2-y1)
 *
 * @param b 向量终点
 * @param a 向量起始点
 * @return 返回向量 a ——> b
 */
public double[] convertToVector(double[] a, double[] b) { // 亦即向量相减
    return new double[]{a[0] - b[0], a[1] - b[1]};
}

/**
 * 求向量叉积，输入向量 a，向量 b，其叉积几何定义等于 axb = |a||b|sinθ，数学定义为 axb = (x1*y2 - y1*x2)
 *
 * @param a 输入向量 a
 * @param b 输入向量 b
 * @return 返回向量 axb 的叉积（axb = -bxa）
 */
public double cross(double[] a, double[] b) { // 叉乘
    return a[0] * b[1] - a[1] * b[0];
}

/**
 * 输入三个点，a、b、c，其中 a 作为起始点，b、c 分别为两个向量的终点，将 abc 三点转化为两个向量 ab、ac，
 * 然后返回 ab 旋转到 ac 这条向量上的面积 s，对于面积 s
 * 1、若 s < 0，表明 ab 到 ac 需顺时针旋转；
 * 2、若 s > 0，表明 ab 到 ac 需逆时针旋转；
 * 3、若 s = 0，表明 ab 和 ac 同处于一个水平线上。
 * 可以使用右手定则自个判断一下（大拇指朝上为正，朝下为负，以大拇指为轴，四个手指垂直于起始向量 ab 然后往 ac 旋转）
 *
 * @param a 点 a
 * @param b 点 b
 * @param c 点 c
 * @return 返回向量 ab 和向量 ac 的叉积
 */
public double getArea(double[] a, double[] b, double[] c) { // 向量 ab 转为 向量 ac 过程中扫过的面积
    return cross(convertToVector(b, a), convertToVector(c, a));
}



// 斜率公式
// Leetcode Q149



// 手动相乘两个数 (每个数均以 int[] 形式表示)
// Leetcode Q43
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



// 组合计算
long MOD = (long) 1e9 + 7;
long[][] dp = new long[maxN][maxN];
public long combination(int n, int k) { // Binomial Coefficient - C(n, k)
    if (dp[n][k] != 0) return dp[n][k]; // 记忆化搜索优化性能
    // base cases
    if (k > n) return 0;
    if (k == 0 || k == n) return 1;
    // recursive
    long res = (combination(n - 1, k - 1) + combination(n - 1, k)) % MOD;
    dp[n][k] = res;
    return res;
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



// 模拟分数及其计算
// 需要记录分子分母以及动态计算（两个分母直接相乘，如果会溢出的话使用最小公倍数）、合并（需最大公约数）
// 一个分数可以直接用一个数组（仅两个元素：分子与分母）表示
// Leetcode 592



// 快速幂法 迭代写法 Java 高效 x^n - k （并且对 10^9+7 取模），Python 等语言有内置函数无需自实现，参考自 https://leetcode.com/problems/count-collisions-of-monkeys-on-a-polygon/solutions/3111664/java-c-python-should-be-pow-2-n-4/?orderBy=most_votes
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

// 快速幂法 递归写法
public long fast_power(long x, long n) { // x is base, n is exponent
    if (n == 0) return 1;

    long res = fast_power(x, n / 2);
    if (n % 2 == 0) return res * res;
    else return res * res * x;
}

// 31 位内包括 31 高效求 2^n
public int power2(n) {
    return 1 << n;
}
// 63 位内包括 63 高效求 2^n
public long power2(n) {
    return 1L << n;
}



// 计算 loga(b)
double x = Math.log(b) / Math.log(a);



// 质因数分解
public Set<Integer> prime_factorization(int num) {
    int n = (int) Math.sqrt(num);
    Set<Integer> primes = new HashSet<>();
    for (int i=2; i<=n; i+=1+(i%2)) { // num 分解质因素
        if (num < i) break;
        if (num % i == 0) {
            primes.add(i);
            while (num % i == 0) num /= i;
        }
    }
    if (num > 1) primes.add(num);
}



// 计算 n 内的质数
// Leetcode Q204
