/*
You are given two integer arrays nums1 and nums2 of length n.

The XOR sum of the two integer arrays is (nums1[0] XOR nums2[0]) + (nums1[1] XOR nums2[1]) + ... + (nums1[n - 1] XOR nums2[n - 1]) (0-indexed).

For example, the XOR sum of [1,2,3] and [3,2,1] is equal to (1 XOR 3) + (2 XOR 2) + (3 XOR 1) = 2 + 0 + 2 = 4.
Rearrange the elements of nums2 such that the resulting XOR sum is minimized.

Return the XOR sum after the rearrangement.

 

Example 1:

Input: nums1 = [1,2], nums2 = [2,3]
Output: 2
Explanation: Rearrange nums2 so that it becomes [3,2].
The XOR sum is (1 XOR 3) + (2 XOR 2) = 2 + 0 = 2.
Example 2:

Input: nums1 = [1,0,3], nums2 = [5,3,4]
Output: 8
Explanation: Rearrange nums2 so that it becomes [5,4,3]. 
The XOR sum is (1 XOR 5) + (0 XOR 4) + (3 XOR 3) = 4 + 4 + 0 = 8.
 

Constraints:

n == nums1.length
n == nums2.length
1 <= n <= 14
0 <= nums1[i], nums2[i] <= 10^7
*/



// Other's Solution:
class Solution {
    int N = 400; // 表示模拟退火算法的迭代次数。在每次迭代中，都会进行一次温度退火，然后尝试交换元素，从而逐步优化答案。N 的值较大时，算法会更充分地搜索空间，但也会增加计算时间。
    double hi = 1e5, lo = 1e-5, fa = 0.90; // hi 表示初始温度，lo 表示最终温度。模拟退火算法的核心思想是在初始时接受一些变差的解，然后逐渐减小温度，降低接受变差解的概率。hi 的值较大表示初始时可以接受较大的变化，lo 的值较小表示退火后只接受较小的变化。fa 是退火的降温系数，表示每次退火后温度降低的比例。较大的 fa 会导致退火过程较快，较小的 fa 则会使退火过程较慢。
    Random random = new Random();
    int[] n1, n2;
    int n;
    int ans = Integer.MAX_VALUE;
    
    public int minimumXORSum(int[] nums1, int[] nums2) {
        // https://mp.weixin.qq.com/s/kInkA9ZGtR1eCVzAqhvemg
        // 复杂度：启发式搜索不讨论时空复杂度
        n1 = nums1; n2 = nums2;
        n = n1.length;
        while (N-- > 0) sa();
        return ans;
    }

    // 「模拟退火」的单次迭代基本流程：
    // 随机选择两个下标，计算「交换下标元素前对应序列的得分」&「交换下标元素后对应序列的得分」，
    // 如果温度下降（交换后的序列更优），进入下一次迭代，
    // 如果温度上升（交换前的序列更优），以「一定的概率」恢复现场（再交换回来）。
    // 对于一个能够运用模拟退火求解的问题，最核心的是如何实现 calc 方法（即如何定义一个具体方案的得分），其余均为模板内容。
    void sa() {
        shuffle(n2);
        for (double t = hi; t > lo; t *= fa) {
            int a = random.nextInt(n), b = random.nextInt(n);
            int prev = calc();
            swap(n2, a, b);
            int cur = calc(); 
            int diff = cur - prev;

            // 在模拟退火算法中使用随机搜索来决定是否接受一个更差的解时，需要一个方法来决定是否接受这个更差的解。
            // 这是通过 Metropolis 准则来实现的，通常使用指数函数来模拟。
            // 在这个具体的代码中，Math.log(diff / t) 计算了当前解与之前解的差异 diff 与当前温度 t 的比值的自然对数。
            // 然后，与 random.nextDouble()（产生 0 到 1 之间的随机数）进行比较。
            // Metropolis 准则的思想是，在初始阶段，随机接受一些差的解，以便能够跳出局部最优解，然后随着温度逐渐降低，逐渐减少接受差解的概率，从而在全局范围内趋向于找到更好的解。
            // 下面这个表达式的含义是，如果 diff 比 t 的数量级大（即解差距比温度大），那么 Math.log(diff / t) 会是正数，因此与随机生成的随机数比较通常会接受差解。
            // 随着温度降低，t 的数量级比 diff 大，Math.log(diff / t) 会趋近于负数，从而越来越不太可能接受差解。
            // 这个过程模拟了在模拟退火中随着温度降低，接受差解的概率逐渐减小的现象，使算法能够从可能的解空间中找到更好的解。
            if (Math.log(diff / t) >= random.nextDouble()) swap(n2, a, b);
        }
    }

    void swap(int[] n, int a, int b) {
        int c = n[a];
        n[a] = n[b];
        n[b] = c;
    }

    int calc() {
        int res = 0;
        for (int i = 0; i < n; i++) res += n1[i] ^ n2[i];
        ans = Math.min(ans, res);
        return res;
    }

    void shuffle(int[] nums) {
        for (int i = n; i > 0; i--) swap(nums, random.nextInt(i), i - 1);
    }
}
