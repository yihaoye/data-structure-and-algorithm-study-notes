Reference: https://www.geeksforgeeks.org/dynamic-programming/  
Dynamic Programming is mainly an optimization over plain recursion. Wherever we see a recursive solution that has repeated calls for same inputs, we can optimize it using Dynamic Programming. The idea is to simply store the results of subproblems, so that we do not have to re-comupute them when needed later. This simple optimization reduces time complexities from exponential to polynomial. For example, if we write simple recursive solution for Fibonacci Numbers, we get exponential time complexity and if we optimize it by storing solutions of subproblems, time complexity reduces to linear.  
动态规划背后的基本思想非常简单。大致上，若要解一个给定问题，我们需要解其不同部分（即子问题），再根据子问题的解以得出原问题的解。  
通常许多子问题非常相似，为此动态规划法试图仅仅解决每个子问题一次，从而减少计算量：一旦某个给定子问题的解已经算出，则将其记忆化存储，以便下次需要同一个子问题解之时直接查表。这种做法在重复子问题的数目关于输入的规模呈指數增長时特别有用。  
  
Recursion:
```java
int fib(int n) {
    if (n <= 1) return n;
    return fib(n − 1) + fib(n − 2);
}
```
  
Dynamic Programming:
```java
f[0] = 0;
f[1] = 1;

for (int i=2; i <= n; i++) {
    f[i] = f[i-1] + f[i-2];
}

return f[n];
```