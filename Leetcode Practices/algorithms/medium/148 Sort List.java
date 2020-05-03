/*
Sort a linked list in O(n log n) time using constant space complexity.

Example 1:

Input: 4->2->1->3
Output: 1->2->3->4
Example 2:

Input: -1->5->3->4->0
Output: -1->0->3->4->5
*/



// Other's Solution (Merge Sort, Author: Huahua):
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode sortList(ListNode head) {
      if (head == null || head.next == null) return head;
      ListNode slow = head;
      ListNode fast = head.next;
      while (fast != null && fast.next != null) {
        fast = fast.next.next;
        slow = slow.next;
      }
      ListNode mid = slow.next;
      slow.next = null;
      return merge(sortList(head), sortList(mid));
    }
    
    private ListNode merge(ListNode l1, ListNode l2) {
      ListNode dummy = new ListNode(0);
      ListNode tail = dummy;
      while (l1 != null && l2 != null) {
        if (l1.val > l2.val) {
          ListNode tmp = l1;
          l1 = l2;
          l2 = tmp;
        }
        tail.next = l1;
        l1 = l1.next;
        tail = tail.next;
      }
      tail.next = (l1 != null) ? l1 : l2;
      return dummy.next;
    }
}
// 参考：http://zxi.mytechroad.com/blog/list/leetcode-148-sort-list/
