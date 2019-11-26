/* Question:
实现一个算法，删除单向链表中间的某个结点，假定你只能访问该结点。
*/



// 解法即将后续结点的数据复制到当前结点即可，而那个原来的后继结点可删可不删
public class Solution {
    public static boolean deleteNode(LinkedListNode n) {
        if (n == null || n.next == null) {
            return false; // 失败（因为链表是单向的，且待删结点为链表尾结点，则此题无解，可能的处理情况为标记该结点为假）
        }
        LinkedListNode next = n.next;
        n.data = next.data;
        n.next = next.next;
        return true;
    }
}