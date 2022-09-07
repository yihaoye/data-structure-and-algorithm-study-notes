/*
Given the head of a singly linked list, reverse the list, and return the reversed list.

 

Example 1:
https://assets.leetcode.com/uploads/2021/02/19/rev1ex1.jpg

Input: head = [1,2,3,4,5]
Output: [5,4,3,2,1]

Example 2:
https://assets.leetcode.com/uploads/2021/02/19/rev1ex2.jpg

Input: head = [1,2]
Output: [2,1]

Example 3:

Input: head = []
Output: []
 

Constraints:

The number of nodes in the list is the range [0, 5000].
-5000 <= Node.val <= 5000
 

Follow up: A linked list can be reversed either iteratively or recursively. Could you implement both?


*/





// Other's Solution:
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
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }
}



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
    public ListNode reverseList(ListNode head) {
        // 栈
        // Time: O(N), Space: O(1)
        Deque<ListNode> stack = new LinkedList<>();
        if (head != null) stack.offer(head);
        ListNode tmpLast = null, tmpNext = null;
        while (!stack.isEmpty()) {
            ListNode last = stack.pop();
            tmpNext = last.next;
            last.next = tmpLast;
            if (tmpNext != null) stack.offer(tmpNext);
            tmpLast = last;
        }
        
        return tmpLast;
    }
}
// 上面的解法可简化为如下
class Solution {
    public ListNode reverseList(ListNode head) {
        // 栈思想 Time: O(N), Space: O(1)
        ListNode tmpLast = null, tmpNext = head;
        while (tmpNext != null) {
            ListNode last = tmpNext;
            tmpNext = last.next;
            last.next = tmpLast;
            tmpLast = last;
        }
        
        return tmpLast;
    }
}
