/**
Given two sparse vectors, compute their dot product.

Implement class SparseVector:

SparseVector(nums) Initializes the object with the vector nums
dotProduct(vec) Compute the dot product between the instance of SparseVector and vec
A sparse vector is a vector that has mostly zero values, you should store the sparse vector efficiently and compute the dot product between two SparseVector.

Follow up: What if only one of the vectors is sparse?

 

Example 1:

Input: nums1 = [1,0,0,2,3], nums2 = [0,3,0,4,0]
Output: 8
Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
v1.dotProduct(v2) = 1*0 + 0*3 + 0*0 + 2*4 + 3*0 = 8
Example 2:

Input: nums1 = [0,1,0,0,0], nums2 = [0,0,0,0,2]
Output: 0
Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
v1.dotProduct(v2) = 0*0 + 1*0 + 0*0 + 0*0 + 0*2 = 0
Example 3:

Input: nums1 = [0,1,0,0,2,0,0], nums2 = [1,0,0,0,3,0,4]
Output: 6
 

Constraints:

n == nums1.length == nums2.length
1 <= n <= 10^5
0 <= nums1[i], nums2[i] <= 100
 */



// Other's Solution:
class SparseVector {
    Map<Integer, Integer> indexMap = new HashMap<>(); // 用哈希表而不是数组，是因为为 0 时不存储在极端情况下能减少大量时空复杂度
    
    SparseVector(int[] nums) {
        // 哈希表 - https://leetcode.com/problems/dot-product-of-two-sparse-vectors/discuss/826713/Java-100-O(min(dm-dn))-solution
        for (int i = 0; i < nums.length; i++)
            if (nums[i] != 0) indexMap.put(i, nums[i]);
    }

    // Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVector vec) {
        if (indexMap.size() == 0 || vec.indexMap.size() == 0) return 0;
        if (indexMap.size() > vec.indexMap.size()) return vec.dotProduct(this);
        int productSum = 0;
        for (Map.Entry<Integer, Integer> entry : indexMap.entrySet()) {
            int index = entry.getKey();
            Integer vecValue = vec.indexMap.get(index);
            if (vecValue == null) continue; 
            productSum += (entry.getValue() * vecValue);
        }
        return productSum;
    }
}
// Your SparseVector object will be instantiated and called as such:
// SparseVector v1 = new SparseVector(nums1);
// SparseVector v2 = new SparseVector(nums2);
// int ans = v1.dotProduct(v2);
