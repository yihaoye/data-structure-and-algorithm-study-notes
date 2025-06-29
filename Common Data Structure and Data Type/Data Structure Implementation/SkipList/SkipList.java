package skiplist;

import java.util.*;

/**
 * 源代码来源：https://gist.github.com/SylvanasSun/f2a3e30e3657d8727006887751c1d1de
 * 
 * 应用到 Leetcode Q1206 Design-Skiplist
 * 
 * ToDo: 彻底实现查询指定 ranking 范围的元素、查询元素的 ranking 等等（相关函数逻辑已完成只差实现在 add/remove 方法中维护 span）...
 *
 * @author SylvanasSun
 */
public class SkipList<K extends Comparable<K>, V> implements Iterable<K> {
    protected static final Random randomGenerator = new Random();
    protected static final double DEFAULT_PROBABILITY = 0.5;
    private Node<K, V> head;
    private double probability;
    private int size;

    public SkipList() {
        this(DEFAULT_PROBABILITY);
    }

    public SkipList(double probability) {
        this.head = new Node<K, V>(null, null, 0);
        this.probability = probability;
        this.size = 0;
    }

    public V get(K key) {
        checkKeyValidity(key);
        Node<K, V> node = findNode(key);
        if (node.getKey().compareTo(key) == 0) return node.getValue();
        else return null;
    }

    // ToDo：在 add 方法中维护 span 的思路，注意下面循环逻辑是由下至上的，先下到最底层才开始第 1 步：
    // 1. 插入时，同时设置插入节点 span（newNode.span），如果是整层的第一个节点则为 0 否则为下层计算的 m（如果是最底层则 m 为 1）
    // 2. 插入后：
    //    - 更新右边节点的 span，leftNode.span = leftNode.span - newNode.span + 1
    //    - 如果随机数决定了要为新节点构建上一层级的节点：按原逻辑会左走回到前一个可以往上走的同一层级的节点，此时求和经过的节点的 span 为 m 供第 1 步使用然后继续循环逻辑（由下至上）
    public void add(K key, V value) { // 这里的 K 是不可重复的，重复 K 会覆盖原值。如果想实现可重复的，可以将 Node 的 value 改为整数统计或 List<V>，且 get、add、remove 方法均需要修改
        checkKeyValidity(key);
        Node<K, V> node = findNode(key);
        if (node.getKey() != null && node.getKey().compareTo(key) == 0) {
            node.setValue(value);
            return;
        }
        Node<K, V> newNode = new Node<K, V>(key, value, node.getLevel());
        horizontalInsert(node, newNode);

        int currentLevel = node.getLevel();
        int headLevel = head.getLevel();
        while (isBuildLevel()) { // 通过随机数来决定是否需要构建新的层级
            if (currentLevel >= headLevel) { // 如果当前层级已经到达或超越顶层（注意越上层数字越大，顶层数字最大，底层为 0），那么需要构建一个新的顶层
                Node<K, V> newHead = new Node<K, V>(null, null, headLevel + 1);
                verticalLink(newHead, head);
                head = newHead;
                headLevel = head.getLevel();
            }
            // 找到 node 对应的上一层节点
            while (node.getUp() == null) node = node.getPrevious();
            node = node.getUp();

            // 将 newNode 复制到上一层（因为上面随机函数判定了让这个 newNode 也成为上一层的一个节点）
            Node<K, V> newNodeUp = new Node<K, V>(key, value, node.getLevel());
            horizontalInsert(node, newNodeUp);
            verticalLink(newNodeUp, newNode);
            newNode = newNodeUp;
            currentLevel++;
        }
        size++;
    }

    // ToDo：在 remove 方法中维护 span 的思路：
    // 与 add 相反即可
    public void remove(K key) {
        checkKeyValidity(key);
        Node<K, V> node = findNode(key);
        if (node == null || node.getKey().compareTo(key) != 0) throw new NoSuchElementException("The key is not exist!");

        // 移动到最底层
        while (node.getDown() != null) node = node.getDown();
        // 自底向上地进行删除
        Node<K, V> prev = null;
        Node<K, V> next = null;
        for (; node != null; node = node.getUp()) {
            prev = node.getPrevious();
            next = node.getNext();
            if (prev != null) prev.setNext(next);
            if (next != null) next.setPrevious(prev);
        }

        // 对顶层链表进行调整，去除无效的顶层链表
        while (head.getNext() == null && head.getDown() != null) {
            head = head.getDown();
            head.setUp(null);
        }
        size--;
    }

    public boolean contains(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public boolean empty() {
        return size == 0;
    }

    protected Node<K, V> findNode(K key) {
        Node<K, V> node = head;
        Node<K, V> next = null;
        Node<K, V> down = null;
        K nodeKey = null;

        while (true) {
            // 找到最接近 key 的 Node（小于或等于）
            next = node.getNext();
            while (next != null && lessThanOrEqual(next.getKey(), key)) {
                node = next;
                next = node.getNext();
            }
            nodeKey = node.getKey();
            if (nodeKey != null && nodeKey.compareTo(key) == 0) break;
            // 往下一层搜索看看是否有比当前 node 更接近 key 的 Node
            down = node.getDown();
            if (down != null) node = down;
            else break;
        }

        return node;
    }

    protected void checkKeyValidity(K key) {
        if (key == null) throw new IllegalArgumentException("Key must be not null!");
    }

    protected boolean lessThanOrEqual(K a, K b) {
        return a.compareTo(b) <= 0;
    }

    protected boolean isBuildLevel() {
        return randomGenerator.nextDouble() < probability;
    }

    protected void horizontalInsert(Node<K, V> x, Node<K, V> y) {
        y.setPrevious(x);
        y.setNext(x.getNext());
        if (x.getNext() != null) x.getNext().setPrevious(y);
        x.setNext(y);
    }

    protected void verticalLink(Node<K, V> x, Node<K, V> y) { // 注意这里的 x 和 y 应是相同的节点（使用场景要求，使用者也要注意保证），x 在上 y 在下，无需设置 x 的上层和 y 的下层
        x.setDown(y);
        y.setUp(x);
    }

    public int getRank(K key) { // O(logN)
        Node<K, V> node = head; int rank = 0;
        for (; node != null; node = node.getDown()) {
            for (; node.getNext() != null; node = node.getNext()) {
                int cmp = node.getNext().getKey().compareTo(key);
                if (cmp > 0) break; // 右边节点比 key 大，往下走。注意这里如果 key 不存在且后面不再会有比 key 更小的 node 时也会尝试往下走，因为这样代码更精简且性能也没什么影响
                rank += node.getNext().span;
                if (cmp == 0) return rank; // key 存在且定位，直接返回。否则右边节点比 key 小，for loop 往右走
            }
        }
        return rank; // key 不存在，返回小于 key 的节点数
    }

    public Node<K, V> getElementByRank(int rank) { // O(logN)
        if (rank < 0 || rank >= size) throw new IllegalArgumentException("Rank out of bounds");
        Node<K, V> node = head; int curRank = 0;
        for (; node != null; node = node.getDown()) {
            for (; node.getNext() != null; node = node.getNext()) {
                int nxtRank = curRank + node.getNext().span;
                if (nxtRank == rank) return node.getNext(); // 定位，直接返回
                if (nxtRank > rank) break; // 右边节点排名超出，往下走
                curRank = nxtRank; // 右边节点排名未超出，for loop 往右走
            }
        }
        return null; // 非预期情况，返回 null 以保证编译
    }


    protected static class Node<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private int level;
        private int span; // 表示在当前层级，该节点与前一个节点之间跨越了多少个底层节点，用于高效更新和计算节点的排名（ranking），在 add/remove 时更新，span 为 0 则应该是整个 SkipList 的头一个，如果紧跟着前一个节点则 span 应为 1
        private Node<K, V> up, down, next, previous;

        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            this.level = level;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public Node<K, V> getUp() {
            return up;
        }

        public void setUp(Node<K, V> up) {
            this.up = up;
        }

        public Node<K, V> getDown() {
            return down;
        }

        public void setDown(Node<K, V> down) {
            this.down = down;
        }

        public Node<K, V> getNext() {
            return next;
        }

        public void setNext(Node<K, V> next) {
            this.next = next;
        }

        public Node<K, V> getPrevious() {
            return previous;
        }

        public void setPrevious(Node<K, V> previous) {
            this.previous = previous;
        }
    }


    // SkipListIterator 和 iterator 不是必需的，可酌情省略（若省略记得把前面 SkipList 类的 implements Iterable<K> 也移除）
    @Override
    public Iterator<K> iterator() {
        return new SkipListIterator<K, V>(head);
    }

    protected static class SkipListIterator<K extends Comparable<K>, V> implements Iterator<K> {
        private Node<K, V> node;

        public SkipListIterator(Node<K, V> node) {
            while (node.getDown() != null) node = node.getDown();
            while (node.getPrevious() != null) node = node.getPrevious();
            if (node.getNext() != null) node = node.getNext();

            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return this.node != null;
        }

        @Override
        public K next() {
            K result = node.getKey();
            node = node.getNext();
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
