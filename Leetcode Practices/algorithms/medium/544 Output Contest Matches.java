/*
During the NBA playoffs, we always arrange the rather strong team to play with the rather weak team, like make the rank 1 team play with the rank nth team, which is a good strategy to make the contest more interesting. Now, you're given n teams, you need to output their final contest matches in the form of a string.

The n teams are given in the form of positive integers from 1 to n, which represents their initial rank. (Rank 1 is the strongest team and Rank n is the weakest team.) We'll use parentheses('(', ')') and commas(',') to represent the contest team pairing - parentheses('(' , ')') for pairing and commas(',') for partition. During the pairing process in each round, you always need to follow the strategy of making the rather strong one pair with the rather weak one.

Example 1:
Input: 2
Output: (1,2)
Explanation: 
Initially, we have the team 1 and the team 2, placed like: 1,2.
Then we pair the team (1,2) together with '(', ')' and ',', which is the final answer.
Example 2:
Input: 4
Output: ((1,4),(2,3))
Explanation: 
In the first round, we pair the team 1 and 4, the team 2 and 3 together, as we need to make the strong team and weak team together.
And we got (1,4),(2,3).
In the second round, the winners of (1,4) and (2,3) need to play again to generate the final winner, so you need to add the paratheses outside them.
And we got the final answer ((1,4),(2,3)).
Example 3:
Input: 8
Output: (((1,8),(4,5)),((2,7),(3,6)))
Explanation: 
First round: (1,8),(2,7),(3,6),(4,5)
Second round: ((1,8),(4,5)),((2,7),(3,6))
Third round: (((1,8),(4,5)),((2,7),(3,6)))
Since the third round will generate the final winner, you need to output the answer (((1,8),(4,5)),((2,7),(3,6))).
Note:
The n is in range [2, 212].
We ensure that the input n can be converted into the form 2k, where k is a positive integer.
*/



// My Solution:
class Solution {
    public String findContestMatch(int n) {
        /* (i,n+1-i)
           (1,n,n/2,n/4,...,2)
            1,2                                         (i,2+1-i)
            1,4 2,3                                     (i,4+1-i)
            1,8 4,5 2,7 3,6                             (i,8+1-i)
            1,16 8,9 4,13 5,12  2,15 7,10 3,14 6,11     (i,16+1-i)
            意思是基于n-1时序列的每个数字中间插入一个空位并填入（n+1减去前一个位的值的结果）（即前一轮数组除了0位的每个元素挪至该位index*2-1的index位置）
            So, DP?
        */
        StringBuilder res = new StringBuilder();
        int[] seq = new int[n];
        seq[0] = 1;
        seq[1] = 2;
        for (int i=2; i<=n/2; i*=2) {
            for (int j=i-1; j>0; j--) {
                seq[j*2] = seq[j];
            }
            for (int j=1; j<=i*2-1; j+=2) {
                seq[j] = i*2+1-seq[j-1];
            }
        }
        for (int i=0; i<n; i++) {
            if (i%2 == 0) {
                if (i == 0) {
                    for (int j=2; j<=n; j*=2) res.append('(');
                } else {
                    for (int j=2; j<=i; j*=2) if (i%j == 0) res.append('(');
                }
            }
            res.append(seq[i]);
            if (i%2 == 1) {
                for (int j=2; j<=i+1; j*=2) if ((i+1)%j == 0) res.append(')');
            }
            if (i != n-1) res.append(',');
        }
        
        return res.toString();
    }
}
