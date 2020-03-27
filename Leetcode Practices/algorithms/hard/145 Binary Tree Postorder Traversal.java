//Question:
/*
Given a binary tree, return the postorder traversal of its nodes' values.

Example:

Input: [1,null,2,3]
   1
    \
     2
    /
   3

Output: [3,2,1]
Follow up: Recursive solution is trivial, could you do it iteratively?
*/



// My Solution 2 (Iterative):
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
   public List<Integer> postorderTraversal(TreeNode root) {
       List<Integer> res = new ArrayList<Integer>();
       if (root == null) return res;
       
       List<TreeNode> linkedList = new LinkedList<TreeNode>();
       linkedList.add(root);
       
       ListIterator<TreeNode> lIterator = linkedList.listIterator();
       while (true) {
           TreeNode node = lIterator.next();
           lIterator.previous();
           if (node.left != null) lIterator.add(node.left);
           if (node.right != null) lIterator.add(node.right);
           
           if (lIterator.hasPrevious()) lIterator.previous();
           else break;
       }
       while (lIterator.hasNext()) res.add(lIterator.next().val);
       
       return res;
   }
}



// My Solution 1 (Recursive):
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
   public List<Integer> postorderTraversal(TreeNode root) {
       List<Integer> res = new ArrayList<Integer>();
       if (root == null) return res;
       res.addAll(postorderTraversal(root.left));
       res.addAll(postorderTraversal(root.right));
       res.add(root.val);
       return res;
   }
}
