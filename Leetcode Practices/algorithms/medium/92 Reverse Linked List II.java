/*
Reverse a linked list from position m to n. Do it in one-pass.

Note: 1 ≤ m ≤ n ≤ length of list.

Example:

Input: 1->2->3->4->5->NULL, m = 2, n = 4
Output: 1->4->3->2->5->NULL
*/



// My Solution:
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0, head);
        ListNode tmp = dummy;
        int len = right - left + 1;
        while (--left > 0) tmp = tmp.next;
        tmp.next = reverseList(tmp.next, len);
        
        return dummy.next;
    }

    public ListNode reverseList(ListNode head, int len) {
        // 栈思想 Time: O(N), Space: O(1)
        ListNode last = null, cur = head;
        int cnt = len;
        while (cur != null && cnt-- > 0) {
            ListNode tmp = cur; cur = cur.next;
            tmp.next = last;
            last = tmp;
        }
        // if (cur != null) cur = reverseK(cur, len); // follow-up：如果想迭代、递归地反转链表的后续，可以添加这一行（假设输入 1->2->3->4->5->NULL，每 2 个进行反转，得出 2->1->4->3->5->NULL）
        if (head != null) head.next = cur; // len 遍历完后，cur 此时为反转后的链表的第 len+1 个节点，原来的 head 现在为第 len 个节点，将两者连接以完成剩下的拼接
        return last;
    }
}



// My Solution (ArrayList):
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
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode tmp = head, preNodeOfLeft = head;
        List<ListNode> subList = new ArrayList<>();
        for (int i=1; i<=right; i++) {
            if (i >= left) subList.add(tmp);
            if (i == left-1) preNodeOfLeft = tmp;
            tmp = tmp.next;
        }
        if (left > 1) preNodeOfLeft.next = subList.get(subList.size()-1);
        reverse(subList);
        
        return left == 1 ? subList.get(0) : head;
    }

    public void reverse(List<ListNode> subList) {
        Collections.reverse(subList);
        subList.get(subList.size()-1).next = subList.get(0).next; // preHead.next = preTail.next
        for (int i=0; i<subList.size()-1; i++) {
            subList.get(i).next = subList.get(i+1);
        }
        return;
    }
}



// My Solution (Recursion):
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode start = new ListNode(0);
        ListNode res = start;
        start.next = head;
        int loopTimes = n - m;
        while (--m > 0) start = start.next;
        recurse(start, loopTimes);
        return res.next;
    }
    
    // recurse to do reverse node "start.next" to node "start.next^(loopTimes+1)" and return "start.next^(loopTimes+1)", PS: "start.next^3" means start.next.next.next
    public ListNode recurse(ListNode start, int loopTimes) {
        if (loopTimes == 0) return start.next;
        ListNode temp1, temp2, end;
        if (loopTimes == 1) {
            end = start.next;
        } else {
            end = recurse(start.next, loopTimes-2);
        }
        
        end = swap(start, end);
        return end;
    }
    
    // revert two nodes - p1.next and p2.next, by exchange their parent and child
    private ListNode swap(ListNode p1, ListNode p2) {
        ListNode node1 = p1.next, node2 = p2.next, c1 = p1.next.next, c2 = p2.next.next;
        if (p1.next == p2) p2 = node2;
        
        node1.next = null;
        node2.next = null;
        
        p1.next = node2;
        p2.next = node1;
        
        if (p2 != node2) node2.next = c1;
        node1.next = c2;
        
        return node1;
    }
}