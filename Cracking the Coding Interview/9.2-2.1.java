import java.util.Hashtable;

/* Question:
编写代码，移除未排序链表中的重复结点。
*/



// 迭代访问整个链表，将每个结点加入散列表。若发现重复元素，则将该结点从链表中移除，然后继续迭代。
public class Solution1 {
    public static void deleteDups(LinkedListNode n) {
        Hashtable table = new Hashtable();
        LinkedListNode previous = null;
        while (n != null) {
            if (table.containsKey(n.data)) {
                previous.next = n.next;
            } else {
                table.put(n.data, true);
                previous = n;
            }
            n = n.next;
        }
    }
}
// 时间复杂度为 O(N)，N 为链表结点数目。



// 不使用缓冲区
// 即可以使用两个指针来迭代：current 迭代访问整个链表，runner 用于检查后续的结点是否重复。
public class Solution2 {
    public static void deleteDups(LinkedListNode head) {
        if (head == null) return;

        LinkedListNode current = head;
        while (current != null) {
            /* 移除后续值相同的所有结点 */
            LinkedListNode runner = current; // 这是 Java 引用，所以 runner 和 current 指的是同一个物理变量
            while (runner.next != null) { // 移除链表中所有值与 current 结点的值相同的结点
                if (runner.next.data == current.data) {
                    runner.next = runner.next.next;
                } else {
                    runner = runner.next;
                }
            }
            current = current.next;
        }
    }
}
// 该方法空间复杂度为 O(1)，但时间复杂度为 O(N^2)。