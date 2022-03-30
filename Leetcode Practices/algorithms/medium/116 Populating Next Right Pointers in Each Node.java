/*
You are given a perfect binary tree where all leaves are on the same level, and every parent has two children. The binary tree has the following definition:

struct Node {
  int val;
  Node *left;
  Node *right;
  Node *next;
}
Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.

Initially, all next pointers are set to NULL.

 

Example 1:
https://assets.leetcode.com/uploads/2019/02/14/116_sample.png

Input: root = [1,2,3,4,5,6,7]
Output: [1,#,2,3,#,4,5,6,7,#]
Explanation: Given the above perfect binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B. The serialized output is in level order as connected by the next pointers, with '#' signifying the end of each level.
Example 2:

Input: root = []
Output: []
 

Constraints:

The number of nodes in the tree is in the range [0, 212 - 1].
-1000 <= Node.val <= 1000
 

Follow-up:

You may only use constant extra space.
The recursive approach is fine. You may assume implicit stack space does not count as extra space for this problem.
*/



// Other's Solution:
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/
class Solution {
    public Node connect(Node root) {
        /*
            基于父节点层的 next 链表构建子节点层的 next 链表，如是类推（根节点直接可得除外） - https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node/solution/tian-chong-mei-ge-jie-dian-de-xia-yi-ge-you-ce-2-4/
            时间复杂度 O(N)，空间复杂度 O(1)
        */
        if (root == null) return root;
        
        Node leftmost = root; // 从根节点开始
        while (leftmost.left != null) {
            Node head = leftmost; // 遍历这一层节点组织成的链表，为下一层的节点更新 next 指针
            
            while (head != null) {
                head.left.next = head.right; // CONNECTION 1
                
                if (head.next != null) { // CONNECTION 2
                    head.right.next = head.next.left;
                }
                head = head.next; // 指针向后移动
            }
            leftmost = leftmost.left; // 去下一层的最左的节点
        }
        
        return root;
    }
}
