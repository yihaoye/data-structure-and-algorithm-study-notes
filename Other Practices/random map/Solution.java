import java.util.*;

class RandomHashMap { // by Copilot (not tested)
    private ListNode[] arr; // List<ListNode> list
    private int count;

    public RandomHashMap() {
        this.arr = new ListNode[10000]; // fix size, for extendable size, use List<ListNode> list
        this.count = 0;
    }

    public void insert(int k, int v) { // O(1)
        // 参考 LC Q706
        this.count++; // 如果 insert 了重复的 key，count 不变
    }

    public boolean remove(int k) { // O(1)
        // 参考 LC Q706
        this.count--; // 如果 remove 了不存在的 key，count 不变
    }

    public int getRandom() { // O(N) N 为不同的 key 的个数 - 一种进一步提升该函数性能的方法是使用红黑树代替 ListNode[] arr，并且每个 Node 要多增加两个值，一个是该 Node 的左右子树的节点个数，一个是 lazyUpdate，如此 RandomHashMap 的三个函数时间复杂度均为 O(logN)
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
