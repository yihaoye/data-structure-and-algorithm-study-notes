/**
Design and implement a data structure for a Least Frequently Used (LFU) cache.

Implement the LFUCache class:

LFUCache(int capacity) Initializes the object with the capacity of the data structure.
int get(int key) Gets the value of the key if the key exists in the cache. Otherwise, returns -1.
void put(int key, int value) Update the value of the key if present, or inserts the key if not already present. When the cache reaches its capacity, it should invalidate and remove the least frequently used key before inserting a new item. For this problem, when there is a tie (i.e., two or more keys with the same frequency), the least recently used key would be invalidated.
To determine the least frequently used key, a use counter is maintained for each key in the cache. The key with the smallest use counter is the least frequently used key.

When a key is first inserted into the cache, its use counter is set to 1 (due to the put operation). The use counter for a key in the cache is incremented either a get or put operation is called on it.

The functions get and put must each run in O(1) average time complexity.

 

Example 1:

Input
["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, 3, null, -1, 3, 4]

Explanation
// cnt(x) = the use counter for key x
// cache=[] will show the last used order for tiebreakers (leftmost element is  most recent)
LFUCache lfu = new LFUCache(2);
lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
lfu.get(1);      // return 1
                 // cache=[1,2], cnt(2)=1, cnt(1)=2
lfu.put(3, 3);   // 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2.
                 // cache=[3,1], cnt(3)=1, cnt(1)=2
lfu.get(2);      // return -1 (not found)
lfu.get(3);      // return 3
                 // cache=[3,1], cnt(3)=2, cnt(1)=2
lfu.put(4, 4);   // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1.
                 // cache=[4,3], cnt(4)=1, cnt(3)=2
lfu.get(1);      // return -1 (not found)
lfu.get(3);      // return 3
                 // cache=[3,4], cnt(4)=1, cnt(3)=3
lfu.get(4);      // return 4
                 // cache=[4,3], cnt(4)=2, cnt(3)=3
 

Constraints:

0 <= capacity <= 104
0 <= key <= 105
0 <= value <= 109
At most 2 * 105 calls will be made to get and put.

 */



// Other's Solution 2:
class LFUCache {
    Map<Integer, Node> cache;  // 存储缓存的内容
    Map<Integer, LinkedHashSet<Node>> freqMap; // 存储每个频次对应的双向链表
    int size;
    int capacity;
    int min; // 存储当前最小频次

    public LFUCache(int capacity) {
        // HashMap + LinkedHashSet + Node - https://leetcode.cn/problems/lfu-cache/solutions/48636/java-13ms-shuang-100-shuang-xiang-lian-biao-duo-ji/
        // Time: O(1), Space: O(capacity)
        cache = new HashMap<> (capacity);
        freqMap = new HashMap<>();
        this.capacity = capacity;
    }
    
    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) return -1;
        freqInc(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (capacity == 0) return;
        
        Node node = cache.get(key);
        if (node != null) {
            node.value = value;
            freqInc(node);
        } else {
            if (size == capacity) {
                Node deadNode = removeNode();
                cache.remove(deadNode.key);
                size--;
            }
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addNode(newNode);
            size++;     
        }
    }

    void freqInc(Node node) {
        // 从原freq对应的链表里移除, 并更新min
        int freq = node.freq;
        LinkedHashSet<Node> set = freqMap.get(freq);
        set.remove(node);
        if (freq == min && set.size() == 0) min = freq + 1;

        // 加入新freq对应的链表
        node.freq++;
        LinkedHashSet<Node> newSet = freqMap.get(freq + 1);
        if (newSet == null) {
            newSet = new LinkedHashSet<>();
            freqMap.put(freq + 1, newSet);
        }
        newSet.add(node);
    }

    void addNode(Node node) {
        LinkedHashSet<Node> set = freqMap.get(1);
        if (set == null) {
            set = new LinkedHashSet<>();
            freqMap.put(1, set);
        } 
        set.add(node); 
        min = 1;
    }

    Node removeNode() {
        LinkedHashSet<Node> set = freqMap.get(min);
        Node deadNode = set.iterator().next();
        set.remove(deadNode);
        return deadNode;
    }
}

class Node {
    int key;
    int value;
    int freq = 1;

    public Node() {}
    
    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}
/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */



// My Solution:
class LFUCache {
    HashMap<Integer, Node> hMap;
    TreeSet<Node> tSet;
    int size;
    int time; // simple timestamp for checking least recently used

    public LFUCache(int capacity) {
        // HashMap + TreeSet + Object
        // Time: O(logN), Space: O(N)
        hMap = new HashMap<>();
        tSet = new TreeSet<>((n1, n2) -> n1.freq == n2.freq ? n2.time - n1.time : n2.freq - n1.freq);
        size = capacity;
        time = 0;
    }
    
    public int get(int key) {
        if (!hMap.containsKey(key)) return -1;
        Node node = hMap.get(key);
        tSet.remove(node); // remove 必须在对象成员更新前进行，原理原因具体参考结尾处的解析
        node.freq++; node.time = time++;
        tSet.add(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if (!hMap.containsKey(key) && tSet.size() == size) {
            hMap.remove(tSet.last().key);
            tSet.remove(tSet.last());
        }
        Node node = hMap.getOrDefault(key, new Node());
        tSet.remove(node); // remove 必须在对象成员更新前进行
        node.key = key; node.val = value; node.freq++; node.time = time++;
        tSet.add(node); hMap.put(key, node);
    }
}

class Node {
    int key, val, freq, time;

    Node() {
        this(-1, -1, 0, 0);
    }

    Node(int key, int val, int freq, int time) {
        this.key = key;
        this.val = val;
        this.freq = freq;
        this.time = time;
    }

    @Override
    public int hashCode() {
        return this.key;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        Node obj = (Node) o;
        return this.key == obj.key;
    }
}
/*
TreeSet是基于红黑树实现的，其元素的顺序是根据元素的自然顺序或者通过提供的比较器来确定的。在你的代码中，使用TreeSet来维护Node对象的顺序，其中比较器是通过节点的频率和时间。
关于为什么remove必须在对象成员更新前进行，原因涉及到红黑树的内部实现和比较器的作用。
在红黑树中，当你调用remove方法时，它会根据比较器找到匹配的节点并将其删除。在删除节点之前，比较器会根据删除前的节点值来进行比较。如果在更新成员之前删除了节点，那么比较器就会使用删除前的节点值进行比较，从而找到正确的节点并进行删除。
如果在更新成员之后再进行删除，节点的值已经发生变化，比较器会使用更新后的值进行比较，这可能导致在红黑树中找不到正确的节点，从而无法成功删除。
在你的代码中，Node对象是TreeSet的元素，而remove操作依赖于Node类中的比较器。因此，确保在更新成员之前调用remove是为了保证红黑树中的节点按照正确的顺序进行删除。
总的来说，这涉及到红黑树的内部实现和Java中TreeSet的比较器工作原理，确保在更新成员之前调用remove是为了保证正确的节点被删除。
*/



// Other's Solution:
class LFUCache {
    int minfreq, capacity;
    Map<Integer, Node> keyMap;
    Map<Integer, DoublyLinkedList> freqMap;

    public LFUCache(int capacity) {
        // 双哈希表（freqMap, keyMap）+ 两个自定义对象（Node, 双向链表 DoublyLinkedList） - https://leetcode.cn/problems/lfu-cache/solution/lfuhuan-cun-by-leetcode-solution/
        // Time: O(1), Space: O(min(capacity, k)) - k 为插入的不同键的数量
        this.minfreq = 0;
        this.capacity = capacity;
        keyMap = new HashMap<Integer, Node>();
        freqMap = new HashMap<Integer, DoublyLinkedList>();
    }
    
    public int get(int key) {
        if (capacity == 0) return -1;
        if (!keyMap.containsKey(key)) return -1;

        Node node = keyMap.get(key);
        int val = node.val, freq = node.freq;
        freqMap.get(freq).remove(node);
        // 如果当前链表为空，需要在哈希表 freqMap 中删除，且更新 minFreq
        if (freqMap.get(freq).size == 0) {
            freqMap.remove(freq);
            if (minfreq == freq) minfreq += 1;
        }
        // 插入到 freq + 1 中
        DoublyLinkedList list = freqMap.getOrDefault(freq + 1, new DoublyLinkedList());
        list.addFirst(new Node(key, val, freq + 1));
        freqMap.put(freq + 1, list);
        keyMap.put(key, freqMap.get(freq + 1).getHead());
        return val;
    }
    
    public void put(int key, int value) {
        if (capacity == 0) return;

        if (!keyMap.containsKey(key)) {
            // 缓存已满，需要进行删除操作
            if (keyMap.size() == capacity) {
                // 通过 minFreq 拿到 freqMap[minFreq] 链表的末尾节点
                Node node = freqMap.get(minfreq).getTail();
                keyMap.remove(node.key);
                freqMap.get(minfreq).remove(node);
                if (freqMap.get(minfreq).size == 0) freqMap.remove(minfreq);
            }
            DoublyLinkedList list = freqMap.getOrDefault(1, new DoublyLinkedList());
            list.addFirst(new Node(key, value, 1));
            freqMap.put(1, list);
            keyMap.put(key, freqMap.get(1).getHead());
            minfreq = 1;
        } else {
            // 与 get 操作基本一致，除了需要更新缓存的值
            Node node = keyMap.get(key);
            int freq = node.freq;
            freqMap.get(freq).remove(node);
            if (freqMap.get(freq).size == 0) {
                freqMap.remove(freq);
                if (minfreq == freq) minfreq += 1;
            }
            DoublyLinkedList list = freqMap.getOrDefault(freq + 1, new DoublyLinkedList());
            list.addFirst(new Node(key, value, freq + 1));
            freqMap.put(freq + 1, list);
            keyMap.put(key, freqMap.get(freq + 1).getHead());
        }
    }
}

class Node {
    int key, val, freq;
    Node prev, next;

    Node() {
        this(-1, -1, 0);
    }

    Node(int key, int val, int freq) {
        this.key = key;
        this.val = val;
        this.freq = freq;
    }
}

class DoublyLinkedList {
    Node dummyHead, dummyTail;
    int size;

    DoublyLinkedList() {
        dummyHead = new Node();
        dummyTail = new Node();
        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
        size = 0;
    }

    public void addFirst(Node node) {
        Node prevHead = dummyHead.next;
        node.prev = dummyHead;
        dummyHead.next = node;
        node.next = prevHead;
        prevHead.prev = node;
        size++;
    }

    public void remove(Node node) {
        Node prev = node.prev, next = node.next;
        prev.next = next;
        next.prev = prev;
        size--;
    }

    public Node getHead() {
        return dummyHead.next;
    }

    public Node getTail() {
        return dummyTail.prev;
    }
}
