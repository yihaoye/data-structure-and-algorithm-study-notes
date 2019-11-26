/* Question:
给定两个用链表表示的整数，每个结点包含一个数位。这些数位是反向存放的，也就是个位排在链表首部。编写函数对这两个整数求和，并用链表形式返回结果。
*/



public class Solution {
    LinkedListNode addLists(LinkedListNode l1, LinkedListNode l2, int carry) {
        /* 两个链表全部为空且进位为0，则函数返回 */
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }

        LinkedListNode result = new LinkedListNode();

        /* 将 value 以及 l1 和 l2 的 data 相加 */
        int value = carry;
        if (l1 != null) {
            value += l1.data;
        }
        if (l2 != null) {
            value += l2.data;
        }

        result.data = value % 10; /* 求和结果的个位 */

        /* 递归 */
        LinkedListNode more = addLists(l1 == null ? null : l1.next, 
                                    l2 == null ? null : l2.next, 
                                    value >= 10 ? 1 : 0);
        result.setNext(more);
        return result;
    }
}



/* Question:
进阶：假设这些数位是正向存放的，请在做一遍。
*/



public class Solution {
    public class PartialSum {
        public LinkedListNode sum = null;
        public int carry = 0;
    }

    LinkedListNode addLists(LinkedListNode l1, LinkedListNode l2) {
        int len1 = length(l1);
        int len2 = length(l2);

        /* 用零填充较短的链表，参看注释（1） */
        if (len1 < len2) {
            l1 = padList(l1, len2 - len1);
        } else {
            l2 = padList(l2, len1 - len2);
        }

        /* 对两个链表求和 */
        PartialSum sum = addListsHelper(l1, l2);

        /* 如有进位，则插入链表首部，否则，直接返回整个链表 */
        if (sum.carry == 0) {
            return sum.sum;
        } else {
            LinkedListNode result = insertBefore(sum.sum, sum.carry);
            return result;
        }
    }

    PartialSum addListsHelper(LinkedListNode l1, LinkedListNode l2) {
        if (l1 == null && l2 == null) {
            PartialSum sum = new PartialSum();
            return sum;
        }
        /* 对较小数字递归求和 */
        PartialSum sum = addListsHelper(l1.next, l2.next);

        /* 将进位和当前数据相加 */
        int val = sum.carry + l1.data + l2.data;

        /* 插入当前数字的求和结果 */
        LinkedListNode full_result = insertBefore(sum.sum, val % 10);

        /* 返回求和结果和进位数 */
        sum.sum = full_result;
        sum.carry = val / 10;
        return sum;
    }

    /* 用零填充链表 */
    LinkedListNode padList(LinkedListNode l, int padding) {
        LinkedListNode head = l;
        for (int i = 0; i < padding; i++) {
            LinkedListNode n = new LinkedListNode(0, null, null);
            head.prev = n;
            n.next = head;
            head = n;
        }
        return head;
    }

    /* 辅助函数，将结点插入链表首部 */
    LinkedListNode insertBefore(LinkedListNode list, int data) {
        LinkedListNode node = new LinkedListNode(data, null, null);
        if (list != null) {
            list.prev = node;
            node.next = list;
        }
        return node;
    }
}