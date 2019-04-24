/*
There is a fence with n posts, each post can be painted with one of the k colors.

You have to paint all the posts such that no more than two adjacent fence posts have the same color.

Return the total number of ways you can paint the fence.

Note:
n and k are non-negative integers.

Example:

Input: n = 3, k = 2
Output: 6
Explanation: Take c1 as color 1, c2 as color 2. All possible ways are:

            post1  post2  post3      
 -----      -----  -----  -----       
   1         c1     c1     c2 
   2         c1     c2     c1 
   3         c1     c2     c2 
   4         c2     c1     c1  
   5         c2     c1     c2
   6         c2     c2     c1
*/



// Other's Solution 1: (Dynamic Programming)
class Solution {
    public int numWays(int n, int k) {
        if(n == 0) return 0;
        else if(n == 1) return k;
        int diffColorCounts = k*(k-1);
        int sameColorCounts = k;
        for(int i=2; i<n; i++) {
            int temp = diffColorCounts;
            diffColorCounts = (diffColorCounts + sameColorCounts) * (k-1);
            sameColorCounts = temp;
        }
        return diffColorCounts + sameColorCounts;
    }
}



// Other's Solution 2: (Dynamic Programming)
class Solution {
    public int numWays(int n, int k) {
        if (n == 0 || k == 0) return 0;
        if (n == 1) return k;
        // same[i] means the ith post has the same color with the (i-1)th post.
        int[] same = new int[n];
        // diff[i] means the ith post has a different color with the (i-1)th post.
        int[] diff = new int[n];
        same[0] = same[1] = k;
        diff[0] = k;
        diff[1] = k * (k - 1);
        for (int i = 2; i < n; ++i) {
            same[i] = diff[i-1];
            diff[i] = (k - 1) * same[i-1] + (k - 1) * diff[i-1];
        }
        return same[n-1] + diff[n-1];
    }
}



// My Solution 1: (not passed, maybe wrong)
class Solution {
    public int numWays(int n, int k) {
        int min = (n%2 == 1) ? n/2+1 : n/2;
        int max = n;
        int sum = 0;
        for (int i=min; i<=max; i++) {
            if (i > 1 && k > 1) {
                int mul = (n-i != 0) ? combinatoric(n, n-i) : 1;
                sum += k*(i-1)*(k-1)*mul;
            }
            if (i == 1 && k > 0) sum += k;
        }
        
        return sum;
    }
    
    // combinatoric choose n from m
    public int combinatoric(int m, int n) {
        return factorial(m)/(factorial(m-n)*factorial(n));
    }
    
    public int factorial(int n) {
        int fact = 1;
        for (int i = 2; i <= n; i++) fact = fact * i;
        return fact;
    }

    /* can not use factorial method here, so large that will exceed even long type, need clever way to implement combinatoric */
    // public BigInteger combinatoric(int m, int n) {
    //     BigInteger res = BigInteger.ONE;
    //     for (int i = 0; i < n; i++) {
    //         res = res.multiply(BigInteger.valueOf(m-i)).divide(BigInteger.valueOf(i+1));
    //     }
    //     return res;
    // }
}

/* Notes:
010101001 = 01010101 (num=8)
011001 = 0101 (num=4)
且0101 = 1010
k=5 0101 5*4*4*4 k*(num-1)*(k-1)
num is part calc

sum(num) <= 2^n (n位二进制有多少种可能 - 相邻位相等不超过二)
(相邻的0或1只代表该区域内为同色，只为提醒这两块不同色了只要不同色任何色都可以填，不代表这块0和下一块0为同色，块1同理)

n/2 < num < n （n为偶数时）
it means for example if n = 8
num min = 4, max = 8
then num have cases:
0101 （4选4）
01010 （5选3，选8-5即3个双位） (即m选n的可能性 m>n m!/((m-n)!*n!))
010101 （6选2）
0101010 （7选1）
01010101 （8选0）
for loop above cases do cal with k*(num-1)*(k-1) and sum up is the result

如果 n 是奇数 (e.g. 5, then num min must 3 since 2 will have 3相邻)
n/2+1 < num < n
*/