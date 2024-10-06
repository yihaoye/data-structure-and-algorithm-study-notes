/*
Given a linked list, remove the n-th node from the end of list and return its head.

Example:

Given linked list: 1->2->3->4->5, and n = 2.

After removing the second node from the end, the linked list becomes 1->2->3->5.
Note:

Given n will always be valid.

Follow up:

Could you do this in one pass?
*/



// Other's Solution (two pointers - sliding window):
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // O(N) Time, O(1) Space
        ListNode start = new ListNode(0);
        ListNode l = start, r = start;
        l.next = head;

        // Move r in front so that the gap between l and r becomes n
        for (int i=1; i<=n+1; i++) r = r.next;
        // Move r to the end, maintaining the n length gap (window)
        while (r != null) {
            l = l.next;
            r = r.next;
        }
        // Skip the desired node
        l.next = l.next.next;
        return start.next;
    }
}
