class Solution {
    public int lengthOfLIS(int[] nums) {
        /*
            a: 思路 - DP，维持一个哈希表，以 nums2 为开始，nums2 即为 nums 的首 2 位子数组的哈希表，哈希表包含了 nums2 每个元素作为结尾的 LIS 值（Key 为元素，Val 为其 LIS），然后计算 nums3 时遍历 nums2，若 nums 3 的新元素大于 nums2 的被遍历元素时则对该元素的 LIS 值加一为 nums3 可得的 max 临时值，结尾时 put(nums3新元素, max)。
            b: 空间复杂度为 O(N)
            c: Start Time 9:13pm, 9:38pm
        */
        HashMap<Integer, Integer> map = new HashMap<>();
        int res = 1; // final max
        for (int num : nums) {
            int tempMax = 1;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getKey() < num && entry.getValue() >= tempMax) {
                    tempMax = entry.getValue() + 1;
                }
            }
            map.put(num, tempMax);
            res = Math.max(res, tempMax);
        }
        
        return res;
    }
}