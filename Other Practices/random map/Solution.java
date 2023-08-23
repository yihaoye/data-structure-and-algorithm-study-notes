import java.util.*;

class RandomHashMap { // by Copilot (not tested)
    private ListNode[] arr; // List<ListNode> list
    private int count;

    public MyHashMap() {
        this.arr = new ListNode[10000]; // fix size, for extendable size, use List<ListNode> list
        this.count = 0;
    }

    public void insert(int k, int v) {
        int index = k % arr.length;
        ListNode head = arr[index];
        if (head == null) {
            arr[index] = new ListNode(k, v);
            count++;
        } else {
            ListNode curr = head;
            while (curr != null) {
                if (curr.key == k) {
                    curr.val = v;
                    return;
                }
                curr = curr.next;
            }
            ListNode newNode = new ListNode(k, v);
            newNode.next = head;
            head.prev = newNode;
            arr[index] = newNode;
            count++;
        }
    }

    public boolean remove(int k) {
        int index = k % arr.length;
        ListNode head = arr[index];
        if (head == null) return false;
        ListNode curr = head;
        while (curr != null) {
            if (curr.key == k) {
                if (curr.prev == null) {
                    arr[index] = curr.next;
                    if (curr.next != null) curr.next.prev = null;
                } else {
                    curr.prev.next = curr.next;
                    if (curr.next != null) curr.next.prev = curr.prev;
                }
                count--;
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    public int getRandom() {
        if (count == 0) throw new RuntimeException("Empty map");
        int random = (int) (Math.random() * arr.length);
        int index = 0;
        while (true) {
            if (arr[index] == null) {
                index++;
                continue;
            }
            ListNode curr = arr[index];
            while (curr != null) {
                if (random == 0) return curr.val;
                random--;
                curr = curr.next;
            }
        }
        return curr.val;
    }

    class ListNode {
        public int key;
        public int val;
        public ListNode prev, next;

        public ListNode(int key, int val) {
            this.key = key;
            this.val = val;
            this.prev = null;
            this.next = null;
        }
    }
}
