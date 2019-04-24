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



// My Solution 1: (not passed, maybe wrong)
import java.math.BigInteger;

class Solution {
    public int numWays(int n, int k) {
        int min = (n%2 == 1) ? n/2+1 : n/2;
        int max = n;
        int sum = 0;
        for (int i=min; i<=max; i++) {
            if (i > 1 && k > 1) {
                int mul = ((n == i) ? 1 : combinatoric(i, n-i));
                sum += k*(i-1)*(k-1)*mul;
            }
            if (i == 1 && k > 0) sum += k;
        }
        
        return sum;
    }
    
    // combinatoric choose n from m
    public int combinatoric(int m, int n) {
        BigInteger res = BigInteger.ONE;
        for (int i = 0; i < n; i++) {
            res = res.multiply(BigInteger.valueOf(m-i)).divide(BigInteger.valueOf(i+1));
        }
        return res.intValue();
    }
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
01010 （5选3，选8-5即3个双位） (即m选n的可能性, m>n, mCn = m!/((m-n)!*n!) = (n+1)*..*m/1*..*(m-n))
010101 （6选2）
0101010 （7选1）
01010101 （8选0）
for loop above cases do cal with k*(num-1)*(k-1) and sum up is the result

如果 n 是奇数 (e.g. 5, then num min must 3 since 2 will have 3相邻)
n/2+1 < num < n
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
/*
    If you are painting the ith post, you have two options:

    make it different color as i-1th post
    make it same color as i-1 th post (if you are allowed!)
    simply add these for your answer:
    num_ways(i) = num_ways_diff(i) + num_ways_same(i)

    Now just think of how to calculate each of those functions.

    The first one is easy. If you are painting the ith post, how many ways can you paint it to make it different from the i-1 th post? k-1

    num_ways_diff(i) = num_ways(i-1) * (k-1)

    The second one is hard, but not so hard when you think about it.

    If you are painting the ith post, how many ways can you paint it to make it the same as the i-1th post? At first, we should think the answer is 1 -- it must be whatever color the last one was.

    num_ways_same(i) = num_ways(i-1) * 1

    But no! This will fail in the cases where painting the last post the same results in three adjacent posts of the same color! We need to consider ONLY the cases where the last two colors were different. But we can do that!

    num_ways_diff(i-1) <- all the cases where the i-1th and i-2th are different.

    THESE are the cases where can just plop the same color to the end, and no longer worry about causing three in a row to be the same.

    num_ways_same(i) = num_ways_diff(i-1) * 1

    We sum these for our answer, like I said before:

    num_ways(i) = num_ways_diff(i) + num_ways_same(i)
    = num_ways(i-1) * (k-1) + num_ways_diff(i-1)

    We know how to compute num_ways_diff, so we can substitute:
    num_ways(i) = num_ways(i-1) * (k-1) + num_ways(i-2) * (k-1)

    We can even simplify a little more:

    num_ways(i) = (num_ways(i-1) + num_ways(i-2)) * (k-1)

    As a note, trying to intuitively understand that last line is impossible. If you think you understand it intuitively, you are fooling yourself. Only the original equation makes intuitive sense.

    Once you have this, the code is trivial (but overall, this problem is certainly not an easy problem, despite the leetcode tag!):
*/
class Solution {
    public int numWays(int n, int k) {
        // if there are no posts, there are no ways to paint them
        if (n == 0) return 0;
        
        // if there is only one post, there are k ways to paint it
        if (n == 1) return k;
        
        // if there are only two posts, you can't make a triplet, so you 
        // are free to paint however you want.
        // first post, k options. second post, k options
        if (n == 2) return k*k;
        
        int table[] = new int[n+1];
        table[0] = 0;
        table[1] = k;
        table[2] = k*k;
        for (int i = 3; i <= n; i++) {
            // the recursive formula that we derived
            table[i] = (table[i-1] + table[i-2]) * (k-1);
        }
        return table[n];
    }
}
