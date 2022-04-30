/**
You are given a 2D array of integers envelopes where envelopes[i] = [wi, hi] represents the width and the height of an envelope.

One envelope can fit into another if and only if both the width and height of one envelope are greater than the other envelope's width and height.

Return the maximum number of envelopes you can Russian doll (i.e., put one inside the other).

Note: You cannot rotate an envelope.

 

Example 1:

Input: envelopes = [[5,4],[6,4],[6,7],[2,3]]
Output: 3
Explanation: The maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
Example 2:

Input: envelopes = [[1,1],[1,1],[1,1]]
Output: 1
 

Constraints:

1 <= envelopes.length <= 105
envelopes[i].length == 2
1 <= wi, hi <= 105

 */



// Other's Solution:
class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        // 二分搜索 + LIS（DP）- https://leetcode-cn.com/problems/russian-doll-envelopes/solution/zui-chang-shang-sheng-zi-xu-lie-bian-xin-6s8d/
        // Time: O(N*logN), Space: O(N)
        int n = envelopes.length;
        if (n == 0) return n;
        
        Arrays.sort(envelopes, (a, b) -> a[0] - b[0]); // 由于使用了 g 记录高度，因此这里只需将 w 从小到达排序即可
        int[] f = new int[n], g = new int[n]; // f(i) 为考虑前 i 个物品，并以第 i 个物品为结尾的最大值；g(i) 记录的是长度为 i 的最长上升子序列的最小「信封高度」
        
        Arrays.fill(g, Integer.MAX_VALUE); // 因为要取 min，用一个足够大（不可能）的高度初始化
        g[0] = 0;
        int res = 1;
        for (int i = 0, j = 0, len = 1; i < n; i++) {
            if (envelopes[i][0] != envelopes[j][0]) { // 对于 w 相同的数据，不更新 g 数组，且 envelopes[i][0] != envelopes[j][0] 即 envelopes[i][0] > envelopes[j][0]
                while (j < i) { // 限制 j 不能越过 i，确保 g 数组中只会出现第 i 个信封前的「历史信封」
                    int prev = f[j], cur = envelopes[j][1];
                    if (prev == len) { // 与 len 长度（之前未达到过）一致了，说明上升序列多增加一位
                        g[len] = cur;
                        len++; // 设定下一次期望达到的长度
                    } else {
                        g[prev] = Math.min(g[prev], cur); // 始终保留最小的「信封高度」，这样可以确保有更多的信封可以与其行程上升序列。举例：同样是上升长度为 5 的序列，保留最小高度为 5 记录（而不是保留任意的，比如 10），这样之后高度为 7 8 9 的信封都能形成序列；
                    }
                    j++;
                }
            }

            // 二分过程；g[i] 代表的是上升子序列长度为 i 的「最小信封高度」
            int l = 0, r = len;
            while (l < r) {
                int mid = l + r >> 1;
                // 令 check 条件为 envelopes[i][1] <= g[mid]（代表 w 和 h 都严格小于当前信封）
                // 这样找到的就是满足条件，最靠近数组中心点的数据（也就是满足 check 条件的最大下标）
                // 对应回 g[] 数组的含义，其实就是找到 w 和 h 都满足条件的最大上升长度
                if (envelopes[i][1] <= g[mid]) r = mid;
                else l = mid + 1;
            }
            // 更新 f[i] 与答案
            f[i] = r;
            res = Math.max(res, f[i]);
        }
        return res;
    }
}
