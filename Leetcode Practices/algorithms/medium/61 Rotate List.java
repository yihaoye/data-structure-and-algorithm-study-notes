/**
Given the head of a linked list, rotate the list to the right by k places.

 

Example 1:
https://assets.leetcode.com/uploads/2020/11/13/rotate1.jpg

Input: head = [1,2,3,4,5], k = 2
Output: [4,5,1,2,3]

Example 2:
https://assets.leetcode.com/uploads/2020/11/13/roate2.jpg

Input: head = [0,1,2], k = 4
Output: [2,0,1]
 

Constraints:

The number of nodes in the list is in the range [0, 500].
-100 <= Node.val <= 100
0 <= k <= 2 * 10^9
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
    public ListNode rotateRight(ListNode head, int k) {
        // 线性表+取模
        // Time: O(N), Space: O(N)
        List<ListNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        if (list.size() == 0) return null;
        k = k % list.size();
        if (k == 0) return list.get(0);
        
        ListNode newHead = list.get(list.size() - k);
        list.get(list.size() - k - 1).next = null;
        list.get(list.size() - 1).next = list.get(0);
        return newHead;
    }
}
