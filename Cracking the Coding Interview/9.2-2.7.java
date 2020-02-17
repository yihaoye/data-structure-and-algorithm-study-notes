/* Question:
编写一个函数，检查链表是否为回文。 
*/



// 迭代法
public class Solution {
    boolean isPalindrome(LinkedListNode head) {
        LinkedListNode fast = head;
        LinkedListNode slow = head;

        Stack<Integer> stack = new Stack<Integer>();

        /*
        将链表前半部分元素入栈。当快速runner（移动速度为慢速runner的两倍）
        到达链表尾部时，则慢速runner已处在链表中间位置
        */
        while (fast != null && fast.next != null) {
            stack.push(slow.data);
            slow = slow.next;
            fast = fast.next.next;
        }

        /* 链表有奇数个元素，跳过中间元素 */
        if (fast != null) {
            slow = slow.next;
        }

        while (slow != null) {
            int top = stack.pop().intValue();

            /* 两者不相同，则该链表不是回文序列 */
            if (top != slow.data) {
                return false;
            }
            slow = slow.next;
        }
        return true;
    }
}



// 递归法
public class Solution {
    class Result {
        public LinkedListNode node;
        public boolean result;
    }

    Result isPalindromeRecurse(LinkedListNode head, int length) {
        if (head == null || length == 0) {
            return new Result(null, true);
        } else if (length == 1) {
            return new Result(head.next, true);
        } else if (length == 2) {
            return new Result(head.next.next, head.data == head.next.data);
        }
        Result res = isPalindromeRecurse(head.next, length - 2);
        if (!res.result || res.node == null) {
            return res;
        } else {
            res.result = head.data == res.node.data;
            res.node = res.node.next;
            return res;
        }
    }

    boolean isPalindrome(LinkedListNode head) {
        Result p = isPalindromeRecurse(head, listSize(head));
        return p.result;
    }
}