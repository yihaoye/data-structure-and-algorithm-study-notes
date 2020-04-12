/*
Given a linked list, determine if it has a cycle in it.

To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.

 

Example 1:

Input: head = [3,2,0,-4], pos = 1
Output: true
Explanation: There is a cycle in the linked list, where tail connects to the second node.
![](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist.png)

Example 2:

Input: head = [1,2], pos = 0
Output: true
Explanation: There is a cycle in the linked list, where tail connects to the first node.
![](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist_test2.png)

Example 3:

Input: head = [1], pos = -1
Output: false
Explanation: There is no cycle in the linked list.
![](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist_test3.png)

 

Follow up:

Can you solve it using O(1) (i.e. constant) memory?
*/



// My Solution:
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        /* 找出碰撞处，将处于链表中 LOOP_SIZE - k 步的位置 */
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) { // 碰撞
                return true;
            }
        }

        /* 没有碰撞处，也即没有环路 */
        // fast == null || fast.next == null
        return false;
    }
}