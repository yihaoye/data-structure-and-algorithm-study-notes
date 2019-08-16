/* Question:
实现一个算法，找出单向链表中倒数第 k 个结点。
*/



// 若链表长度已知，则迭代至第 length - k 个结点即可，但如此简单不大可能是面试中出现的。

// 递归（每次递归函数返回一个值，比如末端返回 0，次末端返回 1，如此类推，当返回 k 时即我们想要的结点）
public class Solution1 {
    public static int nthToLast(LinkedListNode head, int k) {
        if (head == null) {
            return 0;
        }
        int i = nthToLast(head.next, k) + 1;
        if (i == k) {
            System.out.println(head.data);
        }
        return i;
    }
}


// C++
// 此 Solution2 请看 9.2-2.2.cpp，原理与方法1一样，只是使用了指针来记录i值，从而可以返回结点而不是i
// 其实 Java 也可以模仿该方法，即使用静态变量即可


// 创建包裹类（这里的使用逻辑和静态变量逻辑类似）
public class IntWrapper {
    public int value = 0;
}

public class Solution3 {
    LinkedListNode nthToLastR2(LinkedListNode head, int k, IntWrapper i) {
        if (head == null) {
            return null;
        }
        LinkedListNode node = nthToLastR2(head.next, k, i);
        i.value = i.value + 1;
        if (i == k) { // 找到倒数第 k 个元素
            return head;
        }
        return node;
    }
}
// 除上面方法外还可以通过创建一个类及其对象来存放结点和计数器来解题



// 以上都是递归调用，都需要占用 O(n) 的空间

// 迭代法 - 更高效但不太直观
// 具体通过使用两个指针来实现，指针中间间隔 k 个结点
public class Solution4 {
    LinkedListNode nthToLast(LinkedListNode head, int k) {
        if (k <= 0) return null;
        
        LinkedListNode p1 = head;
        LinkedListNode p2 = head;

        // p2向前移动k个结点
        for (int i = 0; i < k-1; i++) {
            if (p2 == null) return null; // 错误检查
            p2 = p2.next;
        }
        if (p2 == null) return null;

        // 现在以同样的速度移动p1和p2，当p2达到链表末端时，p1刚好指向倒数第k个结点
        while (p2.next != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1;
    }
}
// 此算法时间复杂度 O(n)，空间复杂度为 O(1)