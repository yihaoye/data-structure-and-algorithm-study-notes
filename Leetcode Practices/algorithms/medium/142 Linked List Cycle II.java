/*
Given a linked list, return the node where the cycle begins. If there is no cycle, return null.

To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.

Note: Do not modify the linked list.

 

Example 1:

Input: head = [3,2,0,-4], pos = 1
Output: tail connects to node index 1
Explanation: There is a cycle in the linked list, where tail connects to the second node.
![](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist.png)

Example 2:

Input: head = [1,2], pos = 0
Output: tail connects to node index 0
Explanation: There is a cycle in the linked list, where tail connects to the first node.
![](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist_test2.png)

Example 3:

Input: head = [1], pos = -1
Output: no cycle
Explanation: There is no cycle in the linked list.
![](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist_test3.png)

 

Follow-up:
Can you solve it without using extra space?
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
    public ListNode detectCycle(ListNode head) {
        // <Cracking the Coding Interview> 9.2-2.6
        ListNode slow = head, fast = head;

        /* 找出碰撞处，将处于链表中 LOOP_SIZE - k 步的位置 */
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) { // 碰撞
                break;
            }
        }

        /* 错误检查，没有碰撞处，也即没有环路 */
        if (fast == null || fast.next == null) {
            return null;
        }

        /* 将 slow 指向首部，fast 指向碰撞处，两者
        * 距离环路起始处 k 步，若两者以相同速度移动，
        * 则必会在环路起始处碰在一起 */
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return fast;
    }
}