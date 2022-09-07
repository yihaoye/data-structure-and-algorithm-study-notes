//Question:
/*
You are given two linked lists representing two non-negative numbers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 0 -> 8
*/





// Others' Answer:
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode c1 = l1;
        ListNode c2 = l2;
        ListNode sentinel = new ListNode(0);
        ListNode d = sentinel;
        int sum = 0;
        while (c1 != null || c2 != null) {
            sum /= 10;
            if (c1 != null) {
                sum += c1.val;
                c1 = c1.next;
            }
            if (c2 != null) {
                sum += c2.val;
                c2 = c2.next;
            }
            d.next = new ListNode(sum % 10);
            d = d.next;
        }
        if (sum / 10 == 1)
            d.next = new ListNode(1);
        return sentinel.next;
    }
}



// Official Answer:
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }
}



// My Solution:
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        /*
            遍历两个链表，同时使用一个进位符，若其中一个节点为空则视为 0，若两个节点都为空则停止遍历，最后根据进位符是否为 0 决定是否多创建一个节点
            时间复杂度 O(N)，空间复杂度 O(N)
        */
        int carry = 0;
        ListNode res = new ListNode(carry);
        ListNode tmp = res;
        
        while (true) {
            int val1 = l1 != null ? l1.val : 0;
            int val2 = l2 != null ? l2.val : 0;
            tmp.val = tmp.val + val1 + val2;
            if (tmp.val >= 10) {
                carry = tmp.val / 10;
                tmp.val = tmp.val % 10;
            }
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
            if (l1 == null && l2 == null && carry == 0) break;
            tmp.next = new ListNode(carry);
            carry = 0;
            tmp = tmp.next;
        }
        
        return res;
    }
}



// My Solution 2:
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res = new ListNode(0);
        ListNode tmp = res;
        int flag = 0;
        
        while (l1 != null || l2 != null || flag != 0) {
            if (l1 != null) {
                flag += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                flag += l2.val;
                l2 = l2.next;
            }
            tmp.val = flag % 10;
            flag = flag / 10;
            if (l1 != null || l2 != null || flag != 0) {
                tmp.next = new ListNode(0);
                tmp = tmp.next;
            }
        }
        
        return res;
    }
}
