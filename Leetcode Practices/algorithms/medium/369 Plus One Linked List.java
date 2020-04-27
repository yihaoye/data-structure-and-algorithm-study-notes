/*
Given a non-negative integer represented as non-empty a singly linked list of digits, plus one to the integer.

You may assume the integer do not contain any leading zero, except the number 0 itself.

The digits are stored such that the most significant digit is at the head of the list.

Example :

Input: [1,2,3]
Output: [1,2,4]
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
    public ListNode plusOne(ListNode head) {
        head.val += head.next == null ? 1 : recurse(head.next);
        if (head.val == 10) {
            head.val = 0;
            ListNode res = new ListNode(1);
            res.next = head;
            return res;
        }
        return head;
    }
    
    // return carry
    public int recurse(ListNode node) {
        if (node.next == null) {
            node.val++;
            if (node.val == 10) {
                node.val = 0;
                return 1;
            }
            return 0;
        }
        node.val += recurse(node.next);
        if (node.val == 10) {
            node.val = 0;
            return 1;
        }
        return 0;
    }
}