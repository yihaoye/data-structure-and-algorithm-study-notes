/**
Given the head of a singly linked list, sort the list using insertion sort, and return the sorted list's head.

The steps of the insertion sort algorithm:

Insertion sort iterates, consuming one input element each repetition and growing a sorted output list.
At each iteration, insertion sort removes one element from the input data, finds the location it belongs within the sorted list and inserts it there.
It repeats until no input elements remain.
The following is a graphical example of the insertion sort algorithm. The partially sorted list (black) initially contains only the first element in the list. One element (red) is removed from the input data and inserted in-place into the sorted list with each iteration.

https://upload.wikimedia.org/wikipedia/commons/0/0f/Insertion-sort-example-300px.gif
 

Example 1:
https://assets.leetcode.com/uploads/2021/03/04/sort1linked-list.jpg

Input: head = [4,2,1,3]
Output: [1,2,3,4]

Example 2:
https://assets.leetcode.com/uploads/2021/03/04/sort2linked-list.jpg

Input: head = [-1,5,3,4,0]
Output: [-1,0,3,4,5]
 

Constraints:

The number of nodes in the list is in the range [1, 5000].
-5000 <= Node.val <= 5000
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
    public ListNode insertionSortList(ListNode head) {
        ListNode dummy = new ListNode(Integer.MIN_VALUE, head);
        while (true) {
            ListNode tmp = dummy.next;
            while (tmp.next != null && tmp.next.val >= tmp.val) tmp = tmp.next;
            if (tmp.next == null) break;
            ListNode removedNode = removeNextNode(tmp);
            tmp = dummy;
            while (tmp.next.val < removedNode.val) tmp = tmp.next;
            insertNextNode(tmp, removedNode);
        }
        
        return dummy.next;
    }
    
    public ListNode removeNextNode(ListNode curNode) {
        ListNode removedNode = curNode.next;
        ListNode removedNodeNext = removedNode.next;
        removedNode.next = null;
        curNode.next = removedNodeNext;
        return removedNode;
    }
    
    public void insertNextNode(ListNode curNode, ListNode newNode) {
        ListNode originalNext = curNode.next;
        curNode.next = newNode;
        newNode.next = originalNext;
    }
}
