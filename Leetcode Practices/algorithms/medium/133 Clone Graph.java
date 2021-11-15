/*
Given a reference of a node in a connected undirected graph.

Return a deep copy (clone) of the graph.

Each node in the graph contains a val (int) and a list (List[Node]) of its neighbors.

class Node {
    public int val;
    public List<Node> neighbors;
}
 

Test case format:

For simplicity sake, each node's value is the same as the node's index (1-indexed). For example, the first node with val = 1, the second node with val = 2, and so on. The graph is represented in the test case using an adjacency list.

Adjacency list is a collection of unordered lists used to represent a finite graph. Each list describes the set of neighbors of a node in the graph.

The given node will always be the first node with val = 1. You must return the copy of the given node as a reference to the cloned graph.

 

Example 1:
![](https://assets.leetcode.com/uploads/2019/11/04/133_clone_graph_question.png)

Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
Output: [[2,4],[1,3],[2,4],[1,3]]
Explanation: There are 4 nodes in the graph.
1st node (val = 1)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
2nd node (val = 2)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
3rd node (val = 3)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
4th node (val = 4)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
Example 2:
![](https://assets.leetcode.com/uploads/2020/01/07/graph.png)

Input: adjList = [[]]
Output: [[]]
Explanation: Note that the input contains one empty list. The graph consists of only one node with val = 1 and it does not have any neighbors.
Example 3:

Input: adjList = []
Output: []
Explanation: This an empty graph, it does not have any nodes.
Example 4:
![](https://assets.leetcode.com/uploads/2020/01/07/graph-1.png)

Input: adjList = [[2],[1]]
Output: [[2],[1]]
 

Constraints:

1 <= Node.val <= 100
Node.val is unique for each node.
Number of Nodes will not exceed 100.
There is no repeated edges and no self-loops in the graph.
The Graph is connected and all nodes can be visited starting from the given node.
*/



// Other's Solution:
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/

class Solution {
    public HashMap<Integer, Node> map = new HashMap<>();
    
    public Node cloneGraph(Node node) {
        return clone(node);
    }
    
    public Node clone(Node node) {
        if (node == null) return null;
        
        if (map.containsKey(node.val)) 
            return map.get(node.val);
        
        Node newNode = new Node(node.val, new ArrayList<Node>());
        map.put(newNode.val, newNode);
        for (Node neighbor : node.neighbors) 
            newNode.neighbors.add(clone(neighbor));
        return newNode;
    }
}



// My Solution:
class Solution {
    /*
        思路 - BFS + HashMap（DFS 在特殊情况下可能爆栈），问题的关键在于遍历所有节点但是又要注意循环路径，因为 val 被设定为唯一，可以用一个全局 HashMap 存储节点并以 val 为 Key 标记是否遍历过了（只在第一次遍历中 push 到 queue 中）。另外克隆操作是 new 一个 Node 并给 val 和 neighbors 赋值（new ArrayList<>(...)；或使用哈希表中的对象引用）
        时间复杂度 O(N)，空间复杂度 O(N)
    */
    HashMap<Integer, Node> map = new HashMap<>();
    Queue<Node> queue = new LinkedList<Node>();

    public Node cloneGraph(Node node) {
        if (node == null) return node;
        
        Node res = new Node(node.val, new ArrayList<>(node.neighbors));
        map.put(res.val, res);
        queue.add(res);
        while (!queue.isEmpty()) {
            Node preNewNode = queue.poll();
            List<Node> preOldNeighbors = preNewNode.neighbors;
            preNewNode.neighbors = new ArrayList<>();
            for (Node oldNode : preOldNeighbors) {
                Node nextNewNode = null;
                if (!map.containsKey(oldNode.val)) {
                    nextNewNode = new Node(oldNode.val, new ArrayList<>(oldNode.neighbors));
                    map.put(nextNewNode.val, nextNewNode);
                    queue.add(nextNewNode);
                } else {
                    nextNewNode = map.get(oldNode.val);
                }
                preNewNode.neighbors.add(nextNewNode);
            }
        }
        return res;
    }
}
