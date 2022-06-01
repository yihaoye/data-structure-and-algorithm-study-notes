/*
Given an integer array nums, handle multiple queries of the following types:

Update the value of an element in nums.
Calculate the sum of the elements of nums between indices left and right inclusive where left <= right.
Implement the NumArray class:

NumArray(int[] nums) Initializes the object with the integer array nums.
void update(int index, int val) Updates the value of nums[index] to be val.
int sumRange(int left, int right) Returns the sum of the elements of nums between indices left and right inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right]).
 

Example 1:

Input
["NumArray", "sumRange", "update", "sumRange"]
[[[1, 3, 5]], [0, 2], [1, 2], [0, 2]]
Output
[null, 9, null, 8]

Explanation
NumArray numArray = new NumArray([1, 3, 5]);
numArray.sumRange(0, 2); // return 1 + 3 + 5 = 9
numArray.update(1, 2);   // nums = [1, 2, 5]
numArray.sumRange(0, 2); // return 1 + 2 + 5 = 8
 

Constraints:

1 <= nums.length <= 3 * 104
-100 <= nums[i] <= 100
0 <= index < nums.length
-100 <= val <= 100
0 <= left <= right < nums.length
At most 3 * 104 calls will be made to update and sumRange.
*/



// Other's Solution:
class NumArray {
    /*
        经典树状数组场景，实现一个树状数组
        时间复杂度 O(logN)，空间复杂度 O(N)
    */
    private int[] tree;
    private int[] nums;
    
    public NumArray(int[] nums) {
        this.nums = nums;
        int n = this.nums.length;
        tree = new int[n + 1];
        for (int i = 0; i < n; i++) add(i + 1, this.nums[i]);
    }
    
    public void update(int index, int val) {
        add(index + 1, val - nums[index]);
        nums[index] = val;
    }
    
    public int sumRange(int left, int right) {
        return query(right + 1) - query(left);
    }
    
    private int lowbit(int x) {
        return x & -x;
    }
    
    private int query(int x) {
        int res = 0;
        for (int i = x; i > 0; i -= lowbit(i)) res += tree[i];
        return res;
    }
    
    private void add(int x, int u) {
        int n = this.nums.length;
        for (int i = x; i <= n; i += lowbit(i)) tree[i] += u;
    }
}
/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(index,val);
 * int param_2 = obj.sumRange(left,right);
 */



// Other's Solution 2:
class NumArray {
    /*
        经典线段树场景，实现一个线段树
        时间复杂度 O(logN)，空间复杂度 O(N)
    */
    private int[] nums;
    private SegmentTreeNode root;
    
    public NumArray(int[] nums) {
        this.nums = nums;
        if (this.nums != null) this.root = buildTree(0, nums.length-1);
    }
    
    public void update(int index, int val) {
        updateTree(this.root, index, val);
    }
    
    public int sumRange(int left, int right) {
        return sumRange(this.root, left, right);
    }
    
    private SegmentTreeNode buildTree(int start, int end) {
        if (start == end) return new SegmentTreeNode(start, end, this.nums[start], null, null);
        int mid = start + (end - start) / 2;
        SegmentTreeNode left = buildTree(start, mid);
        SegmentTreeNode right = buildTree(mid + 1, end);
        return new SegmentTreeNode(start, end, left.val + right.val, left, right);
    }
    
    private void updateTree(SegmentTreeNode root, int index, int val) {
        if (root.start == index && root.end == index) {
            root.val = val;
            return;
        }

        if (index <= root.mid) updateTree(root.left, index, val);
        else updateTree(root.right, index, val);

        root.val = root.left.val + root.right.val;
    }
  
    private int sumRange(SegmentTreeNode root, int left, int right) {
        if (root.start == left && root.end == right) return root.val;
          
        if (right <= root.mid) return sumRange(root.left, left, right);
        else if (left > root.mid) return sumRange(root.right, left, right);
        else return sumRange(root.left, left, root.mid) + sumRange(root.right, root.mid + 1, right);
    }
    
    class SegmentTreeNode {
        int start;
        int end;
        int mid;
        int val;
        SegmentTreeNode left;
        SegmentTreeNode right;

        public SegmentTreeNode(int start, int end, int val, SegmentTreeNode left, SegmentTreeNode right) {
            this.start = start;
            this.end = end;
            this.mid = start + (end - start) / 2;
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(index,val);
 * int param_2 = obj.sumRange(left,right);
 */
