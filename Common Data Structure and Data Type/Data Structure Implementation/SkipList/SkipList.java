package skiplist;

import java.util.*;

/**
 * 源代码来源：https://gist.github.com/SylvanasSun/f2a3e30e3657d8727006887751c1d1de
 * 
 * 应用到 Leetcode Q1206 Design-Skiplist
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


    protected static class Node<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private int level;
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
