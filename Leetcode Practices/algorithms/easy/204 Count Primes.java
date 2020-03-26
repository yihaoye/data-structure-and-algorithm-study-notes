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