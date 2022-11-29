/**
You are given the head of a linked list.

Remove every node which has a node with a strictly greater value anywhere to the right side of it.

Return the head of the modified linked list.

 

Example 1:


Input: head = [5,2,13,3,8]
Output: [13,8]
Explanation: The nodes that should be removed are 5, 2 and 3.
- Node 13 is to the right of node 5.
- Node 13 is to the right of node 2.
- Node 8 is to the right of node 3.
Example 2:

Input: head = [1,1,1,1]
Output: [1,1,1,1]
Explanation: Every node has value 1, so no nodes are removed.
 

Constraints:

The number of the nodes in the given list is in the range [1, 10^5].
1 <= Node.val <= 10^5
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
    public ListNode removeNodes(ListNode head) {
        // 单调栈
        Deque<ListNode> stack = new LinkedList<>(); // 后续操作使其成为 “单调递减的栈”
        while (head != null) {
            while (!stack.isEmpty() && head.val > stack.peek().val) {
                stack.pop();
            }
            stack.push(head);
            head = head.next;
        }
        ListNode res = new ListNode();
        ListNode tmp = res;
        while (!stack.isEmpty()) {
            tmp.next = stack.pollLast();
            tmp = tmp.next;
        }
        tmp.next = null;
        return res.next;
    }
}
