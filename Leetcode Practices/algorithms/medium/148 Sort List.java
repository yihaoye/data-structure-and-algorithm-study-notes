/*
Sort a linked list in O(n log n) time using constant space complexity.

Example 1:

Input: 4->2->1->3
Output: 1->2->3->4
Example 2:

Input: -1->5->3->4->0
Output: -1->0->3->4->5
*/



// Other's Solution (Merge Sort - Bottom Down, Author: Huahua):
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
  public ListNode sortList(ListNode head) {
    // 0 or 1 element, we are done.
    if (head == null || head.next == null) return head;

    int len = 1;
    ListNode cur = head;
    while (cur != null) {
        cur = cur.next;
        ++len;
    }

    ListNode dummy = new ListNode(0, head);
    ListNode l, r, tail;
    for (int n = 1; n < len; n <<= 1) { // n <<= 1 意思是翻倍，等同于 n *= 2   
      cur = dummy.next; // partial sorted head
      tail = dummy;
      while (cur != null) {
        l = cur;
        r = split(l, n);
        cur = split(r, n);
        List<ListNode> merged = merge(l, r);
        tail.next = merged.get(0);
        tail = merged.get(1);
      }
    }      
    return dummy.next;
  }

  // Splits the list into two parts, first n element and the rest.
  // Returns the head of the rest.
  private ListNode split(ListNode head, int n) {    
    while (--n > 0 && head != null) head = head.next;
    ListNode rest = head != null ? head.next : null;
    if (head != null) head.next = null;
    return rest;
  }
  
  // Merges two lists, returns the head and tail of the merged list.
  private List<ListNode> merge(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;
    while (l1 != null && l2 != null) {
      if (l1.val > l2.val) {
          ListNode tmp = l1;
          l1 = l2;
          l2 = tmp;
      }
      tail.next = l1;
      l1 = l1.next;
      tail = tail.next;
    }
    tail.next = (l1 != null) ? l1 : l2;
    while (tail.next != null) tail = tail.next;
    return Arrays.asList(dummy.next, tail);
  }
}
/*
https://zxi.mytechroad.com/blog/wp-content/uploads/2018/07/148-ep211-2.png
与下面的 Top Down 的递归手法相反（但核心思路一样），此法是从一开始把 List 分成 length 个 sub list，每个 sub list 至多 1 个 Node 然后每两个 sub list 之间的 Node 排序合并，
全部完成后此时 List 被分成 length/2 个 sub list 每个 sub list 至多 2 个 Node，准备下一轮排序合并，
即在第 i 轮时，List 被分成 length/(2^i) 个 sub list，每个 sub list 至多 2^i 个 Node 然后每两个 sub list 之间的 Node（共 2*(2^i) 个，此处 ^ 是指数运算的意思）排序合并（其实同上操作），
由此类推直至最后排序合并为一个 list 则最终完成。
时间复杂度：O(N*logN)
空间复杂度：O(1)
*/
// 参考：http://zxi.mytechroad.com/blog/list/leetcode-148-sort-list/



// Other's Solution (Merge Sort - Top Down, Author: Huahua):
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
    public ListNode sortList(ListNode head) {
      if (head == null || head.next == null) return head;
      ListNode slow = head;
      ListNode fast = head.next;
      while (fast != null && fast.next != null) {
        fast = fast.next.next;
        slow = slow.next;
      }
      ListNode mid = slow.next;
      slow.next = null;
      return merge(sortList(head), sortList(mid));
    }
    
    private ListNode merge(ListNode l1, ListNode l2) {
      ListNode dummy = new ListNode(0);
      ListNode tail = dummy;
      while (l1 != null && l2 != null) {
        if (l1.val > l2.val) {
          ListNode tmp = l1;
          l1 = l2;
          l2 = tmp;
        }
        tail.next = l1;
        l1 = l1.next;
        tail = tail.next;
      }
      tail.next = (l1 != null) ? l1 : l2;
      return dummy.next;
    }
}
/*
通过快慢指针将 List 分成两部分
l1, l2 = split(l)
l1', l2' = sortList(l1), sortList(l2)
merge(l1', l2')
时间复杂度：O(N*logN)
空间复杂度：O(logN) 因为分治递归
*/
// 参考：http://zxi.mytechroad.com/blog/list/leetcode-148-sort-list/
// https://github.com/yihaoye/data-structure-and-algorithm-study-notes/blob/master/Common%20Algorithm%20and%20Theory/Common%20Sorts/%E6%8E%92%E5%BA%8F%E7%AE%97%E6%B3%95%E6%80%A7%E8%83%BD%E5%AF%B9%E6%AF%94.png



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
    public ListNode sortList(ListNode head) {
        /*
            自底向上归并排序 - https://leetcode-cn.com/problems/sort-list/solution/pai-xu-lian-biao-by-leetcode-solution/
            时间复杂度 O(N*logN)，空间复杂度 O(1)
        */
        if (head == null) {
            return head;
        }
        int length = 0;
        ListNode node = head;
        while (node != null) {
            length++;
            node = node.next;
        }
        ListNode dummyHead = new ListNode(0, head);
        for (int subLength = 1; subLength < length; subLength <<= 1) {
            ListNode prev = dummyHead, curr = dummyHead.next;
            while (curr != null) {
                ListNode head1 = curr;
                for (int i = 1; i < subLength && curr.next != null; i++) {
                    curr = curr.next;
                }
                ListNode head2 = curr.next;
                curr.next = null;
                curr = head2;
                for (int i = 1; i < subLength && curr != null && curr.next != null; i++) {
                    curr = curr.next;
                }
                ListNode next = null;
                if (curr != null) {
                    next = curr.next;
                    curr.next = null;
                }
                ListNode merged = merge(head1, head2);
                prev.next = merged;
                while (prev.next != null) {
                    prev = prev.next;
                }
                curr = next;
            }
        }
        return dummyHead.next;
    }

    public ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummyHead = new ListNode(0);
        ListNode temp = dummyHead, temp1 = head1, temp2 = head2;
        while (temp1 != null && temp2 != null) {
            if (temp1.val <= temp2.val) {
                temp.next = temp1;
                temp1 = temp1.next;
            } else {
                temp.next = temp2;
                temp2 = temp2.next;
            }
            temp = temp.next;
        }
        if (temp1 != null) {
            temp.next = temp1;
        } else if (temp2 != null) {
            temp.next = temp2;
        }
        return dummyHead.next;
    }
}
