/**
Given the head of a linked list, reverse the nodes of the list k at a time, and return the modified list.

k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.

You may not alter the values in the list's nodes, only nodes themselves may be changed.

 

Example 1:
https://assets.leetcode.com/uploads/2020/10/03/reverse_ex1.jpg

Input: head = [1,2,3,4,5], k = 2
Output: [2,1,4,3,5]

Example 2:
https://assets.leetcode.com/uploads/2020/10/03/reverse_ex2.jpg

Input: head = [1,2,3,4,5], k = 3
Output: [3,2,1,4,5]
 

Constraints:

The number of nodes in the list is n.
1 <= k <= n <= 5000
0 <= Node.val <= 1000
 

Follow-up: Can you solve the problem in O(1) extra memory space?
 */



// My Solution:
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
    public ListNode reverseKGroup(ListNode head, int k) {
        // 顺序表 + 排序 + 双指针
        List<ListNode> list = new ArrayList<>();
        ListNode dummy = new ListNode(0, head);
        ListNode left = dummy, right = dummy.next;
        while (right != null) {
            if (list.size() == k) {
                left.next = list.get(k-1);
                list.get(0).next = right;
                reverse(list);
                left = list.get(k-1);
                list.clear();
            }
            list.add(right);
            right = right.next;
        }
        if (list.size() == k) {
            left.next = list.get(k-1);
            list.get(0).next = right;
            reverse(list);
            left = list.get(k-1);
            list.clear();
        }
        
        return dummy.next;
    }
    
    public void reverse(List<ListNode> list) {
        Collections.reverse(list);
        for (int i=0; i<list.size()-1; i++) {
            list.get(i).next = list.get(i+1);
        }
    }
}
