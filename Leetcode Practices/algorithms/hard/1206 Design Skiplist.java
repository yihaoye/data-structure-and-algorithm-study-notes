/*
Design a Skiplist without using any built-in libraries.

A skiplist is a data structure that takes O(log(n)) time to add, erase and search. Comparing with treap and red-black tree which has the same function and performance, the code length of Skiplist can be comparatively short and the idea behind Skiplists is just simple linked lists.

For example, we have a Skiplist containing [30,40,50,60,70,90] and we want to add 80 and 45 into it. The Skiplist works this way:


Artyom Kalinin [CC BY-SA 3.0], via Wikimedia Commons

You can see there are many layers in the Skiplist. Each layer is a sorted linked list. With the help of the top layers, add, erase and search can be faster than O(n). It can be proven that the average time complexity for each operation is O(log(n)) and space complexity is O(n).

See more about Skiplist: https://en.wikipedia.org/wiki/Skip_list

Implement the Skiplist class:

Skiplist() Initializes the object of the skiplist.
bool search(int target) Returns true if the integer target exists in the Skiplist or false otherwise.
void add(int num) Inserts the value num into the SkipList.
bool erase(int num) Removes the value num from the Skiplist and returns true. If num does not exist in the Skiplist, do nothing and return false. If there exist multiple num values, removing any one of them is fine.
Note that duplicates may exist in the Skiplist, your code needs to handle this situation.

 

Example 1:

Input
["Skiplist", "add", "add", "add", "search", "add", "search", "erase", "erase", "search"]
[[], [1], [2], [3], [0], [4], [1], [0], [1], [1]]
Output
[null, null, null, null, false, null, true, false, true, false]

Explanation
Skiplist skiplist = new Skiplist();
skiplist.add(1);
skiplist.add(2);
skiplist.add(3);
skiplist.search(0); // return False
skiplist.add(4);
skiplist.search(1); // return True
skiplist.erase(0);  // return False, 0 is not in skiplist.
skiplist.erase(1);  // return True
skiplist.search(1); // return False, 1 has already been erased.
 

Constraints:

0 <= num, target <= 2 * 10^4
At most 5 * 10^4 calls will be made to search, add, and erase.
*/



// My Solution:
class Skiplist {
    protected static final Random randomGenerator = new Random();
    protected static final double DEFAULT_PROBABILITY = 0.5;
    private Node head;
    private double probability;
    private int size;

    public Skiplist() {
        this(DEFAULT_PROBABILITY);
    }

    public Skiplist(double probability) {
        this.head = new Node(null, null, 0);
        this.probability = probability;
        this.size = 0;
    }
    
    public boolean search(int target) {
        Integer k = target;
        Integer v = get(k);
        return v != null && v != 0;
    }
    
    public void add(int num) {
        Integer k = num;
        Integer v = get(k);
        if (v == null) v = 0;
        put(k, v + 1);
    }
    
    public boolean erase(int num) {
        Integer k = num;
        Integer v = get(k);
        if (v == null || v == 0) return false;
        put(k, v - 1);
        return true;
    }

    public Integer get(Integer key) {
        checkKeyValidity(key);
        Node node = findNode(key);
        if (node != null && node.getKey() != null && node.getKey().compareTo(key) == 0) return node.getValue();
        else return null;
    }

    public void put(Integer key, Integer value) {
        checkKeyValidity(key);
        Node node = findNode(key);
        if (node.getKey() != null && node.getKey().compareTo(key) == 0) {
            node.setValue(value);
            return;
        }
        Node newNode = new Node(key, value, node.getLevel());
        horizontalInsert(node, newNode);

        int currentLevel = node.getLevel();
        int headLevel = head.getLevel();
        while (isBuildLevel()) { // 通过随机数来决定是否需要构建新的层级
            if (currentLevel >= headLevel) { // 如果当前层级已经到达或超越顶层（注意越上层数字越大，顶层数字最大，底层为 0），那么需要构建一个新的顶层
                Node newHead = new Node(null, null, headLevel + 1);
                verticalLink(newHead, head);
                head = newHead;
                headLevel = head.getLevel();
            }
            // 找到 node 对应的上一层节点
            while (node.getUp() == null) node = node.getPrevious();
            node = node.getUp();

            // 将 newNode 复制到上一层（因为上面随机函数判定了让这个 newNode 也成为上一层的一个节点）
            Node newNodeUp = new Node(key, value, node.getLevel());
            horizontalInsert(node, newNodeUp);
            verticalLink(newNodeUp, newNode);
            newNode = newNodeUp;
            currentLevel++;
        }
        size++;
    }

    public boolean remove(Integer key) {
        checkKeyValidity(key);
        Node node = findNode(key);
        if (node == null || node.getKey().compareTo(key) != 0) return false;

        // 移动到最底层
        while (node.getDown() != null) node = node.getDown();
        // 自底向上地进行删除
        Node prev = null;
        Node next = null;
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
        return true;
    }

    public boolean contains(Integer key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public boolean empty() {
        return size == 0;
    }

    protected Node findNode(Integer key) {
        Node node = head;
        Node next = null;
        Node down = null;
        Integer nodeKey = null;

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

    protected void checkKeyValidity(Integer key) {
        if (key == null) throw new IllegalArgumentException("Key must be not null!");
    }

    protected boolean lessThanOrEqual(Integer a, Integer b) {
        return a.compareTo(b) <= 0;
    }

    protected boolean isBuildLevel() {
        return randomGenerator.nextDouble() < probability;
    }

    protected void horizontalInsert(Node x, Node y) {
        y.setPrevious(x);
        y.setNext(x.getNext());
        if (x.getNext() != null) x.getNext().setPrevious(y);
        x.setNext(y);
    }

    protected void verticalLink(Node x, Node y) { // 注意这里的 x 和 y 应是相同的节点（使用场景要求，使用者也要注意保证），x 在上 y 在下，无需设置 x 的上层和 y 的下层
        x.setDown(y);
        y.setUp(x);
    }

    protected static class Node {
        private Integer key;
        private Integer value;
        private int level;
        private Node up, down, next, previous;

        public Node(Integer key, Integer value, int level) {
            this.key = key;
            this.value = value;
            this.level = level;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public Node getUp() {
            return up;
        }

        public void setUp(Node up) {
            this.up = up;
        }

        public Node getDown() {
            return down;
        }

        public void setDown(Node down) {
            this.down = down;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrevious() {
            return previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }
    }
}
/**
 * Your Skiplist object will be instantiated and called as such:
 * Skiplist obj = new Skiplist();
 * boolean param_1 = obj.search(target);
 * obj.add(num);
 * boolean param_3 = obj.erase(num);
 */
