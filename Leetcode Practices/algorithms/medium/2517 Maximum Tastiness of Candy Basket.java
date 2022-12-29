/**
You are given an array of positive integers price where price[i] denotes the price of the ith candy and a positive integer k.

The store sells baskets of k distinct candies. The tastiness of a candy basket is the smallest absolute difference of the prices of any two candies in the basket.

Return the maximum tastiness of a candy basket.

 

Example 1:

Input: price = [13,5,1,8,21,2], k = 3
Output: 8
Explanation: Choose the candies with the prices [13,5,21].
The tastiness of the candy basket is: min(|13 - 5|, |13 - 21|, |5 - 21|) = min(8, 8, 16) = 8.
It can be proven that 8 is the maximum tastiness that can be achieved.
Example 2:

Input: price = [1,3,1], k = 2
Output: 2
Explanation: Choose the candies with the prices [1,3].
The tastiness of the candy basket is: min(|1 - 3|) = min(2) = 2.
It can be proven that 2 is the maximum tastiness that can be achieved.
Example 3:

Input: price = [7,7,7,7], k = 2
Output: 0
Explanation: Choosing any two distinct candies from the candies we have will result in a tastiness of 0.
 

Constraints:

2 <= k <= price.length <= 10^5
1 <= price[i] <= 10^9
 */



// Other's Solution:
class Solution {
    public int maximumTastiness(int[] price, int k) {
        // https://leetcode.com/problems/maximum-tastiness-of-candy-basket/solutions/2947983/c-java-python-binary-search-and-sorting/?orderBy=hot
        // use binary search to search the minimum difference. Since range is 0 - 10^9, Time complexity will be n * log(10 ^ 9) = 10 ^ 5 * 30. This should be within constraints.
        
        // Sort prices
        // Run a binary search in the range 0 to 10 ^ 9
        // The check function iterates over the array prices and checks if the given value x can be the minimum difference for any subsequence of the array prices
        // Time complexity: O(nlog(10^9))
        Arrays.sort(price);
        int lo = 0, hi = 1000_000_000;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (check(mid, price, k)) lo = mid + 1;
            else hi = mid;
        }
        return lo - 1;
    }
    
    boolean check(int x, int[] price, int k) {
        int last = price[0], count = 1, i = 1;
        while (count < k && i < price.length) {
            if (price[i] - last >= x) {
                last = price[i];
                count++;
            }
            i++;
        }
        return count == k;
    }
}
