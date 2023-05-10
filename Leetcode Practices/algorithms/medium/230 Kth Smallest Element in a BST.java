/*
Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.

 

Example 1:

Input: root = [3,1,4,null,2], k = 1
   3
  / \
 1   4
  \
   2
Output: 1
Example 2:

Input: root = [5,3,6,2,4,null,null,1], k = 3
       5
      / \
     3   6
    / \
   2   4
  /
 1
Output: 3
Follow up:
What if the BST is modified (insert/delete operations) often and you need to find the kth smallest frequently? How would you optimize the kthSmallest routine?

 

Constraints:

The number of elements of the BST is between 1 to 10^4.
You may assume k is always valid, 1 ≤ k ≤ BST's total elements.
*/



// My Solution (中序遍历):
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int kthSmallest(TreeNode root, int k) {
        ArrayList<TreeNode> list = new ArrayList<TreeNode>();
        inOrder(root, list);
        return list.get(k-1).val;
    }
    
    public void inOrder(TreeNode root, List<TreeNode> list) {
        if (root == null) return;
        inOrder(root.left, list);
        list.add(root);
        inOrder(root.right, list);
    }
}



// My Solution (for follow up):
class Solution {
    int maxVal = 10000; // 0 <= Node.val <= 10^4
    int[] prefixSum = new int[maxVal + 1]; // prefixSum[i] means how many node val <= i, could be replaced by ArrayList for flexible extends but code will be a bit complex
    int lastValidIndex = -1; // last valid index of prefixSum, i > lastValidIndex is not lazy updated with prefixSum yet
    TreeMap<Integer, Integer> ops = new TreeMap<>(); // insert and delete operations with key, sort by key, [A, 3] means add num A 3 times, [B, -2] means remove num B twice, for lazy update, use TreeMap instead of PriorityQueue since there can be lot of add A then remove A useless operations etc

    public int kthSmallest(TreeNode root, int k) {
        // DFS(inorder) + prefix sum + binary search + lazy update
        // Query time complexity: best O(logN), worst O(N*logN), first time O(N)
        // Modify time complexity: best O(1), worst O(logN)
        // Space complexity: O(N)
        if (prefixSum[maxVal] == 0) inorder(root); // prefixSum[10000] == 0 means not init yet, since tree size n must >= 1

        while (prefixSum[lastValidIndex] < k) { // when prefixSum[lastValidIndex] < k then it must be --> !ops.isEmpty() && ops.firstKey() == lastValidIndex + 1
            lazyUpdateOnce();
            if (prefixSum[lastValidIndex] >= k) return lastValidIndex; // purge
        }

        return binarySearch(k, lastValidIndex);
    }

    public void inorder(TreeNode root) {
        if (root.left != null) inorder(root.left);
        while (lastValidIndex < root.val) {
            prefixSum[lastValidIndex + 1] = lastValidIndex == -1 ? 0 : prefixSum[lastValidIndex];
            lastValidIndex++;
        }
        prefixSum[root.val]++;
        if (root.right != null) inorder(root.right);
    }

    public void lazyUpdateOnce() { // lazy update prefixSum once (one index forward only)
        int idxToUpdate = ops.firstKey();
        int valToAdd = ops.get(idxToUpdate);
        prefixSum[idxToUpdate] += valToAdd;
        ops.remove(idxToUpdate);
        if (idxToUpdate + 1 <= maxVal) ops.put(idxToUpdate + 1, ops.getOrDefault(idxToUpdate + 1, 0) + valToAdd);
        lastValidIndex = idxToUpdate;
    }

    public int binarySearch(int target, int end) { // lower bound binary search
		int start = 0;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (prefixSum[mid] >= target) end = mid;
            else start = mid + 1;
        }
		if (start == prefixSum.length) return -1;
        return start;
	}



    /* The following is not really used in the test but important for follow up cases */

    // called during tree add new node with val k
    public void add(int k) {
        ops.put(k, ops.getOrDefault(k, 0) + 1);
        if (ops.get(k) == 0) ops.remove(k);
        lastValidIndex = Math.min(lastValidIndex, k - 1);
    }

    // called during tree remove node with val k
    public void remove(int k) {
        ops.put(k, ops.getOrDefault(k, 0) - 1);
        if (ops.get(k) == 0) ops.remove(k);
        lastValidIndex = Math.min(lastValidIndex, k - 1);
    }
}



// My Solution 2 (for follow up):
// DFS(inorder) + Segment Tree + Prefix Sum + Binary Search
// Query time complexity: average O(logN*logN), first time O(N)
// Modify time complexity: average O(logN)
// Space complexity: O(N)
class Solution {
    SegmentTree st;

    public int kthSmallest(TreeNode root, int k) {
        // 未完全验证，且二分与 query 部分或者可以再优化？
        if (st == null) {
            st = new SegmentTree();
            inorder(root);
        }

        return binarySearch(k);
    }

    public void inorder(TreeNode root) {
        if (root.left != null) inorder(root.left);
        st.update(st.root, root.val, (int) 1e4, 1);
        if (root.right != null) inorder(root.right);
    }

    public int binarySearch(int target) { // lower bound binary search
		int start = 0, end = (int) 1e4;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (st.query(st.root, mid, mid) >= target) end = mid;
            else start = mid + 1;
        }
		if (start == (int) 1e4) return -1;
        return start;
	}



    /* The following is not really used in the test but important for follow up cases */

    // called during tree add new node with val k
    public void add(int k) {
        st.update(st.root, k, (int) 1e4, 1);
    }

    // called during tree remove node with val k
    public void remove(int k) {
        st.update(st.root, k, (int) 1e4, -1);
    }
}

class SegmentTree {
    public Node root;

    public SegmentTree() {
        root = new Node(0, (int) (1e4));
    }

    class Node {
        private int leftX;
        private int rightX;
        private int maxValue;
        private int lazy;
        Node leftChild, rightChild;

        public Node(int leftX, int rightX) {
            this.leftX = leftX;
            this.rightX = rightX;
        }
    }

    public int query(Node root, int left, int right) { // query max value in the range
        if (root.rightX < left || right < root.leftX) {
            return 0;
        }
        if (left <= root.leftX && root.rightX <= right) {
            return root.maxValue;
        } else {
            lazyCreate(root);
            pushDown(root);
            int l = query(root.leftChild, left, right);
            int r = query(root.rightChild, left, right);
            return Math.max(l, r);
        }
    }

    public void update(Node root, int left, int right, int value) {
        if (root.rightX < left || right < root.leftX) {
            return;
        }
        if (left <= root.leftX && root.rightX <= right) {
            root.maxValue += value;
            root.lazy += value;
        } else {
            lazyCreate(root);
            pushDown(root);
            update(root.leftChild, left, right, value);
            update(root.rightChild, left, right, value);
            pushUp(root);
        }
    }

    public void lazyCreate(Node root) {
        if (root.leftChild == null) {
            root.leftChild = new Node(root.leftX, root.leftX + (root.rightX - root.leftX) / 2);
        }
        if (root.rightChild == null) {
            root.rightChild = new Node(root.leftX + (root.rightX - root.leftX) / 2 + 1, root.rightX);
        }
    }

    public void pushDown(Node root) {
        if (root.lazy > 0) {
            root.leftChild.maxValue += root.lazy;
            root.leftChild.lazy += root.lazy;
            root.rightChild.maxValue += root.lazy;
            root.rightChild.lazy += root.lazy;
            root.lazy = 0;
        }
    }

    public void pushUp(Node root) {
        root.maxValue = Math.max(root.leftChild.maxValue, root.rightChild.maxValue);
    }
}
