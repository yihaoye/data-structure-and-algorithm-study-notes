/*
A linked list of length n is given such that each node contains an additional random pointer, which could point to any node in the list, or null.

Construct a deep copy of the list. The deep copy should consist of exactly n brand new nodes, where each new node has its value set to the value of its corresponding original node. Both the next and random pointer of the new nodes should point to new nodes in the copied list such that the pointers in the original list and copied list represent the same list state. None of the pointers in the new list should point to nodes in the original list.

For example, if there are two nodes X and Y in the original list, where X.random --> Y, then for the corresponding two nodes x and y in the copied list, x.random --> y.

Return the head of the copied linked list.

The linked list is represented in the input/output as a list of n nodes. Each node is represented as a pair of [val, random_index] where:

val: an integer representing Node.val
random_index: the index of the node (range from 0 to n-1) that the random pointer points to, or null if it does not point to any node.
Your code will only be given the head of the original linked list.

 

Example 1:
https://assets.leetcode.com/uploads/2019/12/18/e1.png

Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]

Example 2:
https://assets.leetcode.com/uploads/2019/12/18/e2.png

Input: head = [[1,1],[2,1]]
Output: [[1,1],[2,1]]

Example 3:
https://assets.leetcode.com/uploads/2019/12/18/e3.png

Input: head = [[3,null],[3,0],[3,null]]
Output: [[3,null],[3,0],[3,null]]

Example 4:

Input: head = []
Output: []
Explanation: The given linked list is empty (null pointer), so return null.
 

Constraints:

0 <= n <= 1000
-10000 <= Node.val <= 10000
Node.random is null or is pointing to some node in the linked list.
*/



// My Solution:
/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/
class Solution {
    public Node copyRandomList(Node head) {
        /*
            思路 - 遍历两遍，第一遍用哈希表并使用原节点对象 toString 为 Key，Val 为新建节点对象并赋值（但不设 next 和 random 引用），第二遍时负责设 next 和 random 引用，此时使用哈希表键定位新建的其他节点即可
            时间复杂度 O(N)，空间复杂度 O(N)
        */
        if (head == null) return null;
        HashMap<String, Node> map = new HashMap<>();
        Node tmp = head;
        while (tmp != null) {
            Node newNode = new Node(tmp.val);
            map.put(tmp.toString(), newNode);
            tmp = tmp.next;
        }
        
        tmp = head;
        while (tmp != null) {
            if (tmp.next != null) map.get(tmp.toString()).next = map.get(tmp.next.toString());
            if (tmp.random != null) map.get(tmp.toString()).random = map.get(tmp.random.toString());
            tmp = tmp.next;
        }
        
        return map.get(head.toString());
    }
}



// Other's Solution:
/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/
class Solution {
    public Node copyRandomList(Node head) {
        /*
            迭代 + 节点拆分 - https://leetcode-cn.com/problems/copy-list-with-random-pointer/solution/fu-zhi-dai-sui-ji-zhi-zhen-de-lian-biao-rblsf/
            首先将该链表中每一个节点拆分为两个相连的节点，例如对于链表 A→B→C，可以将其拆分为 A→A′→B→B′→C→C′。对于任意一个原节点 S，其拷贝节点 S′ 即为其后继节点。
            时间复杂度 O(N)，空间复杂度 O(1)
        */
        if (head == null) {
            return null;
        }
        for (Node node = head; node != null; node = node.next.next) {
            Node nodeNew = new Node(node.val);
            nodeNew.next = node.next;
            node.next = nodeNew;
        }
        for (Node node = head; node != null; node = node.next.next) {
            Node nodeNew = node.next;
            nodeNew.random = (node.random != null) ? node.random.next : null;
        }
        Node headNew = head.next;
        for (Node node = head; node != null; node = node.next) {
            Node nodeNew = node.next;
            node.next = node.next.next;
            nodeNew.next = (nodeNew.next != null) ? nodeNew.next.next : null;
        }
        return headNew;
    }
}
