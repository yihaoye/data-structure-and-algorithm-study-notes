/*
Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.

Return the linked list sorted as well.

Example 1:

Input: 1->2->3->3->4->4->5
Output: 1->2->5
Example 2:

Input: 1->1->1->2->3
Output: 2->3
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
    public ListNode deleteDuplicates(ListNode head) {
        Map<Integer, Integer> map = new HashMap<>();
        ListNode res = new ListNode(0);
        res.next = head;
        ListNode cur = res, temp = head;
        while (temp != null) {
            if (map.containsKey(temp.val)) map.put(temp.val, 1);
            else map.put(temp.val, 0);
            temp = temp.next;
        }
        temp = head;
        while (true) {
            while (temp != null && map.get(temp.val) != 0) temp = temp.next;
            cur.next = temp;
            cur = cur.next;
            if (temp == null) break;
            temp = temp.next;
        }
        
        return res.next;
    }
}