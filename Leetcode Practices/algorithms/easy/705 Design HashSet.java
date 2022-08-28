/**
Design a HashSet without using any built-in hash table libraries.

Implement MyHashSet class:

void add(key) Inserts the value key into the HashSet.
bool contains(key) Returns whether the value key exists in the HashSet or not.
void remove(key) Removes the value key in the HashSet. If key does not exist in the HashSet, do nothing.
 

Example 1:

Input
["MyHashSet", "add", "add", "contains", "contains", "add", "contains", "remove", "contains"]
[[], [1], [2], [1], [3], [2], [2], [2], [2]]
Output
[null, null, null, true, false, null, true, null, false]

Explanation
MyHashSet myHashSet = new MyHashSet();
myHashSet.add(1);      // set = [1]
myHashSet.add(2);      // set = [1, 2]
myHashSet.contains(1); // return True
myHashSet.contains(3); // return False, (not found)
myHashSet.add(2);      // set = [1, 2]
myHashSet.contains(2); // return True
myHashSet.remove(2);   // set = [1]
myHashSet.contains(2); // return False, (already removed)
 

Constraints:

0 <= key <= 10^6
At most 10^4 calls will be made to add, remove, and contains.
 */



// Other's Solution:
class MyHashSet {
    
    ListNode[] nodes;

    public MyHashSet() {
        /*
            参考 Leetcode 706
            创建一个 ListNode 链表节点类包含键、值与下一个节点的指针，创建一个全局 ListNode 数组且长度为 10000（测试值），getIndex 函数负责计算键的哈希值，findElement 函数负责 ListNode 数组中定位链表并迭代取得健对应的的节点
            时间复杂度 O(1)，空间复杂度 O(N)
        */
        nodes = new ListNode[10000];
    }
    
    public void add(int key) {
        int hash = hash(key);
		ListNode prev = findListNode(hash, key);
		
		if (prev.next == null) prev.next = new ListNode(key);
    }
    
    public void remove(int key) {
        int hash = hash(key);
        ListNode prev = findListNode(hash, key);
			
        if (prev.next != null) prev.next = prev.next.next;
    }
    
    public boolean contains(int key) {
        int hash = hash(key);
		ListNode prev = findListNode(hash, key);
		return prev.next == null ? false : true;
    }
    
    /* key methods */
    private int hash(int key) {	
		return Integer.hashCode(key) % nodes.length;
	}
    
    private ListNode findListNode(int hash, int key) {
		if (nodes[hash] == null) return nodes[hash] = new ListNode(-1);
        
        ListNode prev = nodes[hash];
		
		while (prev.next != null && prev.next.key != key) {
			prev = prev.next;
		}
		return prev;
	}
    
    class ListNode {
		int key;
		ListNode next;

		ListNode(int key) {
			this.key = key;
		}
	}
}
/**
 * Your MyHashSet object will be instantiated and called as such:
 * MyHashSet obj = new MyHashSet();
 * obj.add(key);
 * obj.remove(key);
 * boolean param_3 = obj.contains(key);
 */
