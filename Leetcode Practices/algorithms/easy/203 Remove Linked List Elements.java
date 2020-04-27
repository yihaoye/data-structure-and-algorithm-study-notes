/*
Remove all elements from a linked list of integers that have value val.

Example:

Input:  1->2->6->3->4->5->6, val = 6
Output: 1->2->3->4->5
*/



// My Solution:
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode removeElements(ListNode head, int val) {
        ListNode cur = new ListNode(0);
        cur.next = head;
        head = cur;
        while (cur.next != null) {
            while (cur.next != null && cur.next.val == val) cur.next = cur.next.next;
            cur = cur.next;
            if (cur == null) break;
        }
        
        return head.next;
    }
}