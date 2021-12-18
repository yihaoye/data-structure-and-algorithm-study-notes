/*
There are buckets buckets of liquid, where exactly one of the buckets is poisonous. To figure out which one is poisonous, you feed some number of (poor) pigs the liquid to see whether they will die or not. Unfortunately, you only have minutesToTest minutes to determine which bucket is poisonous.

You can feed the pigs according to these steps:

Choose some live pigs to feed.
For each pig, choose which buckets to feed it. The pig will consume all the chosen buckets simultaneously and will take no time.
Wait for minutesToDie minutes. You may not feed any other pigs during this time.
After minutesToDie minutes have passed, any pigs that have been fed the poisonous bucket will die, and all others will survive.
Repeat this process until you run out of time.
Given buckets, minutesToDie, and minutesToTest, return the minimum number of pigs needed to figure out which bucket is poisonous within the allotted time.

 

Example 1:

Input: buckets = 1000, minutesToDie = 15, minutesToTest = 60
Output: 5
Example 2:

Input: buckets = 4, minutesToDie = 15, minutesToTest = 15
Output: 2
Example 3:

Input: buckets = 4, minutesToDie = 15, minutesToTest = 30
Output: 2
 

Constraints:

1 <= buckets <= 1000
1 <= minutesToDie <= minutesToTest <= 100
*/





// Other's Solution:
class Solution {
    public int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
        /*
            没思路看题解（https://leetcode-cn.com/problems/poor-pigs/solution/hua-jie-suan-fa-458-ke-lian-de-xiao-zhu-by-guanpen/），是 N 进制数学问题（或者 M 维度数学）以达到单个猪负载可能的最多信息 N（M 为猪的使用数量，以 M 构建 M 维空间，M 个维相交即可确定一个焦点 - 有毒的桶；N 则为单个维度/轴的最大值）
            时间复杂度 O(1)，空间复杂度 O(1)
        */
        int times = minutesToTest / minutesToDie;
        int base = times + 1; // 这里 base 即 N 进制里的 N
        // base ^ ans >= buckets 
        // ans >= log(buckets) / log(base)
        double temp = Math.log(buckets) / Math.log(base);
        int ans = (int)Math.ceil(temp);
        return ans;
    }
}
