/*
Given a singly linked list, determine if it is a palindrome.

Example 1:

Input: 1->2
Output: false
Example 2:

Input: 1->2->2->1
Output: true
Follow up:
Could you do it in O(n) time and O(1) space?
*/



// My Solution (Inspired by told reverse first half):
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;
        ListNode slow = head, fast = head, ln2 = null;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            if (fast == null) {
                ln2 = slow.next;
                break;
            }
            if (fast.next == null) {
                ln2 = slow.next.next;
                break;
            }
            slow = slow.next;
        }
        
        // reverse first half, if odd not include middle one
        slow.next = null; // firstly split into two lists
        ListNode prev = null, curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        
        // prev is head of list 1 now
        ListNode ln1 = prev;
        while (ln1 != null && ln2 != null) {
            if (ln1.val != ln2.val) return false;
            ln1 = ln1.next;
            ln2 = ln2.next;
        }
        if (ln1 != null || ln2 != null) return false;
        return true;
    }
}