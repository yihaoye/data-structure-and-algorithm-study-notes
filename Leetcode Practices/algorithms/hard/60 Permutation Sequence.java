/*
The set [1, 2, 3, ..., n] contains a total of n! unique permutations.

By listing and labeling all of the permutations in order, we get the following sequence for n = 3:

"123"
"132"
"213"
"231"
"312"
"321"
Given n and k, return the kth permutation sequence.

 

Example 1:

Input: n = 3, k = 3
Output: "213"
Example 2:

Input: n = 4, k = 9
Output: "2314"
Example 3:

Input: n = 3, k = 1
Output: "123"
 

Constraints:

1 <= n <= 9
1 <= k <= n!
*/



// My Solution:
class Solution {
    int[] factorial; // factorial[i] = i!，假设 n 为 3，若想知道以 1 为首的有多少种，其实就是 2!，其实无论谁为首都一样，并且谁为剩下的也一样
    List<Integer> availables = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)); // max n is 9

    public String getPermutation(int n, int k) {
        // 记忆化 + 数学（排列组合 + 取余取模运算）
        // Time: O(N)
        init(n);
        List<Integer> indexs = new ArrayList<>(); // indexs 是当前剩下的数字 availables 中按序的第 index 个数字
        for (int i=n-1; i>=0; i--) {
            int index = k / factorial[i] - (k >= factorial[i] && k % factorial[i] == 0 ? 1 : 0);
            indexs.add(index);
            k -= index * factorial[i];
        }
        String res = "";
        for (int index : indexs) {
            res += availables.get(index);
            availables.remove(index);
        }
        return res;
    }

    public void init(int n) {
        factorial = new int[n + 1];
        factorial[0] = 1; factorial[1] = 1;
        for (int i=2; i<=n; i++) factorial[i] = factorial[i-1] * i;
    }
}
