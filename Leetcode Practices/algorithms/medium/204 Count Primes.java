/*
Count the number of prime numbers less than a non-negative number, n.

Example:

Input: 10
Output: 4
Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.
*/



// Other's Solution:
class Solution {
    public int countPrimes(int n) {
        /*
            看题解 - 类动态规划+排除（埃氏筛）https://leetcode-cn.com/problems/count-primes/solution/ji-shu-zhi-shu-by-leetcode-solution/，遍历时同时更新非质数的相关 dp 数组
            时间复杂度 O(NloglogN) - 复杂的计算不要求掌握，空间复杂度 O(N)
        */
        boolean[] notPrime = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (notPrime[i] == false) {
                count++;
                for (int j = 2; i*j < n; j++) {
                    notPrime[i*j] = true;
                }
            }
        }
        
        return count;
    }
}
// Explanation: https://assets.leetcode.com/static_assets/public/images/solutions/Sieve_of_Eratosthenes_animation.gif



// Other's Solution 2:
class Solution {
    public int countPrimes(int n) {
        // 线性筛法 - by ChatGPT，保证每个合数都只被其最小质因数筛去（因为合数都可以表示为一组纯质数的乘积），而不被其他质数重复筛去，从而达成性能优化
        // 整个过程中，通过不断地更新最小质因数数组 minPrime，能够在时间复杂度为 O(n) 的情况下找到小于等于 n 的所有质数。
        // 相比于朴素的埃氏筛法，减少了许多不必要的标记操作，从而提高了效率。其核心思想是避免多次标记同一个数，使得整个算法的时间复杂度相对较低。
        // Time: O(N)
        int[] minPrime = new int[n];
        List<Integer> primes = new ArrayList<>();

        for (int i = 2; i < n; i++) {
            if (minPrime[i] == 0) { // 意味着 i 在前面的迭代没被设置过，所以是质数
                minPrime[i] = i; // i 是当前正在处理的数，minPrime[i] 表示 i 的最小质因数
                primes.add(i);
            }

            for (int j = 0; j < primes.size() && primes.get(j) <= minPrime[i] && i * primes.get(j) < n; j++) { // primes.get(j) <= minPrime[i]：确保 primes.get(j) 小于等于当前合数 i 的最小质因数
                minPrime[i * primes.get(j)] = primes.get(j); // 将 minPrime[i * primes.get(j)] 设置为 primes.get(j)，表示合数 i * primes.get(j) 的最小质因数是 primes.get(j)
            }
        }

        return primes.size();
    }
}



// My Solution (Time Limit Exceeded):
class MySolution {
    public int countPrimes(int n) {
        // 每个合数都可以写成几个质数（也可称为素数）相乘的形式，这几个质数就都叫做这个合数的质因数。
        int[] primes = new int[n];
        int res = 0;
        for (int i=0; i<n; i++) cachePrime(i, primes);
        for (int j=0; j<primes.length && primes[j] != 0; j++) res++;
        
        return res;
    }
    
    public void cachePrime(int num, int[] primes) {
        int i = 0;
        if (num < 2) return;
        for (; i < primes.length && primes[i] != 0; i++) {
            if (num%primes[i] == 0) return;
        }
        if (i < primes.length) primes[i] = num;
    }
}