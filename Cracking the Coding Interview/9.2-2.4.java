/* Question:
编写代码，以给定值 x 为基准将链表分割成两部分，所有小于 x 的结点排在大于或等于 x 的结点之前。
*/



public class Solution1 {
    /*
        传入链表的首结点，以及作为链表分割基准的值
    */
    public LinkedListNode partition(LinkedListNode node, int x) {
        LinkedListNode beforeStart = null;
        LinkedListNode beforeEnd = null;
        LinkedListNode afterStart = null;
        LinkedListNode afterEnd = null;

        /* 分割链表 */
        while (node != null) {
            LinkedListNode next = node.next;
            node.next = null;
            if (node.data < x) {
                /* 将结点插入 before 链表 */
                if (beforeStart == null) {
                    beforeStart = node;
                    beforeEnd = beforeStart;
                } else {
                    beforeEnd.next = node;
                    beforeEnd = node;
                }
            } else {
                /* 将结点插入 after 链表 */
                if (afterStart == null) {
                    afterStart = node;
                    afterEnd = afterStart;
                } else {
                    afterEnd.next = node;
                    afterEnd = node;
                }
            }
            node = next;
        }

        if (beforeStart == null) {
            return afterStart;
        }

        /* 合并 before 和 after 链表 */
        beforeEnd.next = afterStart;
        return beforeStart;
    }
}



public class Solution2 {
    public LinkedListNode partition(LinkedListNode node, int x) {
        LinkedListNode beforeStart = null;
        LinkedListNode afterStart = null;

        /* 分割链表 */
        while (node != null) {
            LinkedListNode next = node.next;
            if (node.data < x) {
                /* 将结点插入 before 链表的前端 */
                node.next = beforeStart;
                beforeStart = node;
            } else {
                /* 将结点插入 after 链表的前端 */
                node.next = afterStart;
                afterStart = node;
            }
            node = next;
        }

        /* 合并 before 和 after 链表 */
        if (beforeStart == null) {
            return afterStart;
        }

        /* 定位至 before 链表末尾，合并两个链表 */
        LinkedListNode head = beforeStart;
        while (beforeStart.next != null) {
            beforeStart = beforeStart.next;
        }
        beforeStart.next = afterStart;

        return head;
    }
}