/* Question
给定一个有环链表，实现一个算法返回环路的开头结点。
*/

public class Solution {
    LinkedListNode FindBeginning(LinkedListNode head) {
        LinkedListNode slow = head;
        LinkedListNode fast = head;

        /* 找出碰撞处，将处于链表中 LOOP_SIZE - k 步的位置 */
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) { // 碰撞
                break;
            }
        }

        /* 错误检查，没有碰撞处，也即没有环路 */
        if (fast == null || fast.next == null) {
            return null;
        }

        /* 将 slow 指向首部，fast 指向碰撞处，两者
        * 距离环路起始处 k 步，若两者以相同速度移动，
        * 则必会在环路起始处碰在一起 */
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return fast;
    }
}