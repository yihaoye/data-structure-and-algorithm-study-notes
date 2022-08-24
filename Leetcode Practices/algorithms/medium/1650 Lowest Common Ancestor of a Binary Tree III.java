/**
Given two nodes of a binary tree p and q, return their lowest common ancestor (LCA).

Each node will have a reference to its parent node. The definition for Node is below:

class Node {
    public int val;
    public Node left;
    public Node right;
    public Node parent;
}
According to the definition of LCA on Wikipedia: "The lowest common ancestor of two nodes p and q in a tree T is the lowest node that has both p and q as descendants (where we allow a node to be a descendant of itself)."

 

Example 1:
https://assets.leetcode.com/uploads/2018/12/14/binarytree.png

Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.

Example 2:
https://assets.leetcode.com/uploads/2018/12/14/binarytree.png

Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5 since a node can be a descendant of itself according to the LCA definition.

Example 3:

Input: root = [1,2], p = 1, q = 2
Output: 1
 

Constraints:

The number of nodes in the tree is in the range [2, 10^5].
-10^9 <= Node.val <= 10^9
All Node.val are unique.
p != q
p and q exist in the tree.
 */



// My Solution:
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node parent;
};
*/
class Solution {
    public Node lowestCommonAncestor(Node p, Node q) {
        // 一直向上回溯到根节点，记录添加每个父节点到一个 List 中 <node, p1, p2, ... root>
        // 然后再从右向左找到最后一个一样的节点，即 LCA
        Node lca = null;
        
        Deque<Node> pStack = new LinkedList<>();
        pStack.push(p);
        while (pStack.peek().parent != null) pStack.push(pStack.peek().parent);
        
        Deque<Node> qStack = new LinkedList<>();
        qStack.push(q);
        while (qStack.peek().parent != null) qStack.push(qStack.peek().parent);
        
        while (!pStack.isEmpty() && !qStack.isEmpty()) {
            Node pLca = pStack.pop();
            Node qLca = qStack.pop();
            if (pLca == qLca) lca = pLca;
            else return lca;
        }
        
        return lca;
    }
}
