/*
Design a max stack data structure that supports the stack operations and supports finding the stack's maximum element.

Implement the MaxStack class:

MaxStack() Initializes the stack object.
void push(int x) Pushes element x onto the stack.
int pop() Removes the element on top of the stack and returns it.
int top() Gets the element on the top of the stack without removing it.
int peekMax() Retrieves the maximum element in the stack without removing it.
int popMax() Retrieves the maximum element in the stack and removes it. If there is more than one maximum element, only remove the top-most one.
 

Example 1:

Input
["MaxStack", "push", "push", "push", "top", "popMax", "top", "peekMax", "pop", "top"]
[[], [5], [1], [5], [], [], [], [], [], []]
Output
[null, null, null, null, 5, 5, 1, 5, 1, 5]

Explanation
MaxStack stk = new MaxStack();
stk.push(5);   // [5] the top of the stack and the maximum number is 5.
stk.push(1);   // [5, 1] the top of the stack is 1, but the maximum is 5.
stk.push(5);   // [5, 1, 5] the top of the stack is 5, which is also the maximum, because it is the top most one.
stk.top();     // return 5, [5, 1, 5] the stack did not change.
stk.popMax();  // return 5, [5, 1] the stack is changed now, and the top is different from the max.
stk.top();     // return 1, [5, 1] the stack did not change.
stk.peekMax(); // return 5, [5, 1] the stack did not change.
stk.pop();     // return 1, [5] the top of the stack and the max element is now 5.
stk.top();     // return 5, [5] the stack did not change.
 

Constraints:

-107 <= x <= 107
At most 104 calls will be made to push, pop, top, peekMax, and popMax.
There will be at least one element in the stack when pop, top, peekMax, or popMax is called.
 

Follow up: Could you come up with a solution that supports O(1) for each top call and O(logn) for each other call? 
*/



// Other's Solution:
class MaxStack {
    /*
        双向链表+平衡树 - https://leetcode-cn.com/problems/max-stack/solution/max-stack-by-leetcode/
        时间复杂度：top 操作为 O(1)，其余操作为 O(logN)，空间复杂度：O(N)
    */
    TreeMap<Integer, List<Node>> btMap; // 平衡树
    DoubleLinkedList dLinkedList;

    public MaxStack() {
        btMap = new TreeMap();
        dLinkedList = new DoubleLinkedList();
    }

    public void push(int x) {
        Node node = dLinkedList.add(x);
        if(!btMap.containsKey(x)) btMap.put(x, new ArrayList<Node>());
        btMap.get(x).add(node);
    }

    public int pop() {
        int val = dLinkedList.pop();
        List<Node> L = btMap.get(val);
        L.remove(L.size() - 1);
        if (L.isEmpty()) btMap.remove(val);
        return val;
    }

    public int top() {
        return dLinkedList.peek();
    }

    public int peekMax() {
        return btMap.lastKey();
    }

    public int popMax() {
        int max = peekMax();
        List<Node> L = btMap.get(max);
        Node node = L.remove(L.size() - 1);
        dLinkedList.unlink(node);
        if (L.isEmpty()) btMap.remove(max);
        return max;
    }
}

class DoubleLinkedList {
    Node head, tail;

    public DoubleLinkedList() {
        head = new Node(0);
        tail = new Node(0);
        head.next = tail;
        tail.prev = head;
    }

    public Node add(int val) {
        Node x = new Node(val);
        x.next = tail;
        x.prev = tail.prev;
        tail.prev.next = x;
        tail.prev = x;
        return x;
    }

    public int pop() {
        return unlink(tail.prev).val;
    }

    public int peek() {
        return tail.prev.val;
    }

    public Node unlink(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        return node;
    }
}

class Node {
    int val;
    Node prev, next;
    public Node(int v) {val = v;}
}

/**
 * Your MaxStack object will be instantiated and called as such:
 * MaxStack obj = new MaxStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.peekMax();
 * int param_5 = obj.popMax();
 */
