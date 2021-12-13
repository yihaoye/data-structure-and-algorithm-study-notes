/*
Design a HashMap without using any built-in hash table libraries.

To be specific, your design should include these functions:

put(key, value) : Insert a (key, value) pair into the HashMap. If the value already exists in the HashMap, update the value.
get(key): Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key.
remove(key) : Remove the mapping for the value key if this map contains the mapping for the key.

Example:

MyHashMap hashMap = new MyHashMap();
hashMap.put(1, 1);          
hashMap.put(2, 2);         
hashMap.get(1);            // returns 1
hashMap.get(3);            // returns -1 (not found)
hashMap.put(2, 1);          // update the existing value
hashMap.get(2);            // returns 1 
hashMap.remove(2);          // remove the mapping for 2
hashMap.get(2);            // returns -1 (not found) 

Note:

All keys and values will be in the range of [0, 1000000].
The number of operations will be in the range of [1, 10000].
Please do not use the built-in HashMap library.

*/



// My Solution after inspired:
class MyHashMap {
    int[] map;
    
    /** Initialize your data structure here. */
    public MyHashMap() {
        map = new int[1000000];
        Arrays.fill(map, -1);
    }
    
    /** value will always be non-negative. */
    public void put(int key, int value) {
        map[key] = value;
    }
    
    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {
        return map[key];
    }
    
    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        map[key] = -1;
    }
}
/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */
// Above can be optimized in following:
class MyHashMap {
    int[] map;
    
    /** Initialize your data structure here. */
    public MyHashMap() {
        map = new int[1000000];
    }
    
    /** value will always be non-negative. */
    public void put(int key, int value) {
        map[key] = value + 1;
    }
    
    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {
        return map[key] - 1;
    }
    
    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        map[key] = 0;
    }
}



// Other's Solution:
/*
Traditional Hash-Table Implementation - Using Array of LinkedList - Accepted in 75 ms

The general implementation of HashMap uses bucket which is basically a chain of linked lists and each node containing <key, value> pair.
So if we have duplicate nodes, that doesn't matter - it will still replicate each key with it's value in linked list node.
When we insert the pair (10, 20) and then (10, 30), there is technically no collision involved. We are just replacing the old value with the new value for a given key 10, since in both cases, 10 is equal to 10 and also the hash code for 10 is always 10.
Collision happens when multiple keys hash to the same bucket. In that case, we need to make sure that we can distinguish between those keys. Chaining collision resolution is one of those techniques which is used for this.
Just for the information. In JDK 8, HashMap has been tweaked so that if keys can be compared for ordering, then any densely-populated bucket is implemented as a tree, so that even if there are lots of entries with the same hash-code, the complexity isO(log n).
Time complexity: O(1) average and O(n) worst case - for all get(),put() and remove() methods.
Space complexity: O(n) - where n is the number of entries in HashMap
*/
class MyHashMap {
	ListNode[] nodes = new ListNode[10000];

	public int get(int key) {
		int index = getIndex(key);
		ListNode prev = findElement(index, key);
		return prev.next == null ? -1 : prev.next.val;
	}
	
	public void put(int key, int value) {
		int index = getIndex(key);
		ListNode prev = findElement(index, key);
		
		if (prev.next == null) prev.next = new ListNode(key, value);
		else prev.next.val = value;
	}

	public void remove(int key) {
		int index = getIndex(key);
        ListNode prev = findElement(index, key);
			
        if (prev.next != null) prev.next = prev.next.next;
	}

	private int getIndex(int key) {	
		return Integer.hashCode(key) % nodes.length;
	}

	private ListNode findElement(int index, int key) {
		if(nodes[index] == null) return nodes[index] = new ListNode(-1, -1);
        
        ListNode prev = nodes[index];
		
		while(prev.next != null && prev.next.key != key) {
			prev = prev.next;
		}
		return prev;
	}

	private static class ListNode {
		int key, val;
		ListNode next;

		ListNode(int key, int val) {
			this.key = key;
			this.val = val;
		}
	}
}



// Other's Solution:
class MyHashMap {
    private static final int BASE = 769;
    private LinkedList[] data;

    public MyHashMap() {
        data = new LinkedList[BASE];
        for (int i = 0; i < BASE; ++i) {
            data[i] = new LinkedList<Pair>();
        }
    }
    
    public void put(int key, int value) {
        int h = hash(key);
        Iterator<Pair> iterator = data[h].iterator();
        while (iterator.hasNext()) {
            Pair pair = iterator.next();
            if (pair.getKey() == key) {
                pair.setValue(value);
                return;
            }
        }
        data[h].offerLast(new Pair(key, value));
    }
    
    public int get(int key) {
        int h = hash(key);
        Iterator<Pair> iterator = data[h].iterator();
        while (iterator.hasNext()) {
            Pair pair = iterator.next();
            if (pair.getKey() == key) {
                return pair.value;
            }
        }
        return -1;
    }
    
    public void remove(int key) {
        int h = hash(key);
        Iterator<Pair> iterator = data[h].iterator();
        while (iterator.hasNext()) {
            Pair pair = iterator.next();
            if (pair.key == key) {
                data[h].remove(pair);
                return;
            }
        }
    }

    private static int hash(int key) {
        return key % BASE;
    }
    
    private class Pair {
        private int key;
        private int value;

        public Pair(int key, int value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
