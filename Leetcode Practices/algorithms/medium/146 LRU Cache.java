/*
Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.

Implement the LRUCache class:

* LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
* int get(int key) Return the value of the key if the key exists, otherwise return -1.
* void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.

Follow up:
Could you do get and put in O(1) time complexity?

 
Example 1:
    Input
    ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
    [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
    Output
    [null, null, null, 1, null, -1, null, -1, 3, 4]

    Explanation
    LRUCache lRUCache = new LRUCache(2);
    lRUCache.put(1, 1); // cache is {1=1}
    lRUCache.put(2, 2); // cache is {1=1, 2=2}
    lRUCache.get(1);    // return 1
    lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
    lRUCache.get(2);    // returns -1 (not found)
    lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
    lRUCache.get(1);    // return -1 (not found)
    lRUCache.get(3);    // return 3
    lRUCache.get(4);    // return 4
 

Constraints:
    * 1 <= capacity <= 3000
    * 0 <= key <= 3000
    * 0 <= value <= 104
    * At most 3 * 104 calls will be made to get and put.
*/





// My Solution (Customized LinkedList + HashMap): 
class LRUCache {
    private Map<Integer, Node> map;
    private Node listHead;
    private Node listTail;
    private int capacity;

    public LRUCache(int capacity) {
        this.map = new HashMap<>();
        this.listHead = null;
        this.listTail = null;
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if (map.containsKey(key)) {
            Node updateNode = map.get(key);
            if (updateNode.prev != null) {
                updateNode.prev.next = updateNode.next;
                if (updateNode.next != null) updateNode.next.prev = updateNode.prev;
                else listTail = updateNode.prev; // updateNode is listTail
                listHead.prev = updateNode;
                updateNode.prev = null;
                updateNode.next = listHead;
                listHead = updateNode;
            }
            return updateNode.val;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node updateNode = map.get(key);
            updateNode.val = value;
            if (updateNode.prev != null) {
                updateNode.prev.next = updateNode.next;
                if (updateNode.next != null) updateNode.next.prev = updateNode.prev;
                else listTail = updateNode.prev; // updateNode is listTail
                listHead.prev = updateNode;
                updateNode.prev = null;
                updateNode.next = listHead;
                listHead = updateNode;
            }
            return;
        }
        Node newNode = new Node(null, listHead, key, value);
        map.put(key, newNode);
        if (listTail == null) listTail = newNode;
        listHead = newNode;
        if (listHead.next != null) listHead.next.prev = listHead;
        if (map.size() > capacity) {
            map.remove(listTail.key);
            listTail = listTail.prev;
            listTail.next = null;
        }
    }
    
    class Node {
        public Node prev;
        public Node next;
        public int key;
        public int val;
        
        public Node(Node prev, Node next, int key, int val) {
            this.prev = prev;
            this.next = next;
            this.key = key;
            this.val = val;
        }
    }
}
/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
