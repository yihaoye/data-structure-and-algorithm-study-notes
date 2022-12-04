/**
You are given the root of a binary tree with unique values.

In one operation, you can choose any two nodes at the same level and swap their values.

Return the minimum number of operations needed to make the values at each level sorted in a strictly increasing order.

The level of a node is the number of edges along the path between it and the root node.

 

Example 1:
https://assets.leetcode.com/uploads/2022/09/18/image-20220918174006-2.png

Input: root = [1,4,3,7,6,8,5,null,null,null,null,9,null,10]
Output: 3
Explanation:
- Swap 4 and 3. The 2nd level becomes [3,4].
- Swap 7 and 5. The 3rd level becomes [5,6,8,7].
- Swap 8 and 7. The 3rd level becomes [5,6,7,8].
We used 3 operations so return 3.
It can be proven that 3 is the minimum number of operations needed.

Example 2:
https://assets.leetcode.com/uploads/2022/09/18/image-20220918174026-3.png

Input: root = [1,3,2,7,6,5,4]
Output: 3
Explanation:
- Swap 3 and 2. The 2nd level becomes [2,3].
- Swap 7 and 4. The 3rd level becomes [4,6,5,7].
- Swap 6 and 5. The 3rd level becomes [4,5,6,7].
We used 3 operations so return 3.
It can be proven that 3 is the minimum number of operations needed.

Example 3:
https://assets.leetcode.com/uploads/2022/09/18/image-20220918174052-4.png

Input: root = [1,2,3,4,5,6]
Output: 0
Explanation: Each level is already sorted in increasing order so return 0.
 

Constraints:

The number of nodes in the tree is in the range [1, 10^5].
1 <= Node.val <= 10^5
All the values of the tree are unique.
 */



// My Solution:
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
    public int minimumOperations(TreeNode root) {
        // 注意：只交换值，不是交换整个节点
        // BFS + 贪心（排序）
        int res = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> list = new LinkedList<>();
        PriorityQueue<Integer> sortList = new PriorityQueue<>();
        if (root != null) queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode pre = queue.poll();
                list.offer(pre.val);
                sortList.offer(pre.val);
                if (pre.left != null) queue.offer(pre.left);
                if (pre.right != null) queue.offer(pre.right);
            }
            
            res += cal(list, sortList);
        }
        
        return res;
    }
    
    public int cal(Queue<Integer> list, PriorityQueue<Integer> sortList) {
        int res = 0, len = list.size(), index = 0;
        int[] unsorted = new int[len];
        Map<Integer, Integer> sorted = new HashMap<>(); // <num, sortedIndex>
        while (index < len) {
            unsorted[index] = list.poll();
            sorted.put(sortList.poll(), index);
            index++;
        }
        for (int i=0; i<len;) {
            int tmp = unsorted[i];
            if (i != sorted.get(tmp)) {
                int sortedIndex = sorted.get(tmp);
                unsorted[i] = unsorted[sortedIndex];
                unsorted[sortedIndex] = tmp;
                res++;
            } else {
                i++;
            }
        }
        
        return res == 0 ? 0 : res;
    }
}
