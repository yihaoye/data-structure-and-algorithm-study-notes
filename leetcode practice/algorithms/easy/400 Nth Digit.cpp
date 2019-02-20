
/*
The original question is:

    Find the nth digit of the infinite integer sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, ...

1. Take a try

    From 1 to 9, we have 9 single-digit numbers (1×9 digits).
    From 10 to 99, we have 90 double-digit numbers (2×90 digits).
    From 100 to 999, we have 900 triple-digit numbers (3×900 digits).
    ............................................................................................................

2. Divide the problem

If we suppose that the nth digit is in integer X, we can divide the problem into 3 steps:

    Find out the TOTAL AMOUNT OF DIGITS that X has in all.
    (1? 2? 3?..., which means X is between 1 and 9? 10 and 99? 100 and 999? ...)
    Find out WHICH NUMBER X is.
    Find out the nth digit is WHICH DIGIT in X, and output.

3. Explanation
Step 1:

n can be presented as:

    n = 9 + 2 × 90 + 3 × 900 + ... + 9(k - 1) × 10^(k - 2) + M, M < 9k × 10^(k-1)

Here I need to find out M in order to locate X. I use an recursive solution.

The recursive function is named as find(int n, int p)

    Set the initial total amount of digits as p = 1;
    Calculate the amount of all p-digits numbers' digits: num = 9p × 10^(p - 1)
    Judge if n > num
    If TRUE, return find(n - num, p + 1).
    If FALSE, go to Step 2.

Step 2:

Here I get M, and I know X MUST has p digits.

How to find WHICH NUMBER X is? Let's see some examples:

    If M is 1 to p, X is 10000....0 (p - 1 zeros , one 1).
    If M is p + 1 to 2p, X is 10000...1 (p - 2 zeros, two 1's).
    ..............................................................................

We come to the conclusion:

    X = (M - 1) / p + 10000....0 (p - 1 zeros , one 1) = (M - 1) / p + 10^(p - 1).

As X has been found, now the nth dight is the kth digit in X, where

    k = (M - 1) mod p, 0 ≤ k ≤ p - 1.

Then we go to Step 3.
Step 3:

Suppose X = A0A1A2...A(p - 1), which has p digits.

As

    X / 10^(p - 1) = A0. A0 mod 10 = A0;
    X / 10^(p - 2) = A0A1. A0A1 mod 10 = A1;
    .................................

The kth digit in X is

    X / 10^(p - k - 1) mod 10
*/

//4. Code

class Solution {
public:
    int find(int n, int p)
    {
        if(n > 9 * p * pow(10, p - 1))
        {
            // Step 1
            return find(n - 9 * p * pow(10, p - 1), p + 1);
        }else{
            // Step 2
            int x = (n - 1) / p + pow(10, p - 1);
            int k = (n - 1) % p;
            // Step 3
            return (x / (int) pow(10, p - k - 1)) % 10;
        }
    }
    
    int findNthDigit(int n) {
        return find (n, 1);
    }
};

