/*
Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.

Clarification: The input/output format is the same as how LeetCode serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.

 

Example 1:
https://assets.leetcode.com/uploads/2020/09/15/serdeser.jpg

Input: root = [1,2,3,null,null,4,5]
Output: [1,2,3,null,null,4,5]
Example 2:

Input: root = []
Output: []
Example 3:

Input: root = [1]
Output: [1]
Example 4:

Input: root = [1,2]
Output: [1,2]
 

Constraints:

The number of nodes in the tree is in the range [0, 104].
-1000 <= Node.val <= 1000
*/



// My Solution:
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {
    /*
        看 topic 提示 DFS。序列化：前序遍历树并把节点值存入一个字符串当中，以逗号分割，若节点为空则只添加一个逗号。反序列化：将字符串以逗号为标识分割成字符串数组，递归地前序遍历数组，每个递归里新建一个树节点顺序表、先添加当前节点然后紧接着添加左节点的顺序表（参数索引为当前索引 +1）、添加右节点的顺序表（参数索引为当前索引 +1+左节点顺序表的长度）、然后返回顺序表到上一层
        时间复杂度 O(N)，空间复杂度 O(N)
    */

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder strB = new StringBuilder();
        dfs(root, strB);
        strB.deleteCharAt(strB.length()-1); // remove last comma
        return strB.toString();
    }

    // pre order dfs
    public void dfs(TreeNode node, StringBuilder strB) {
        if (node != null) {
            strB.append(node.val);
            strB.append(",");
            dfs(node.left, strB);
            dfs(node.right, strB);
        } else {
            strB.append(",");
        }
        return;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        List<String> nodeValList = Arrays.asList(data.split(","));
        List<TreeNode> nodeList = new ArrayList<>();
        nodeList.addAll(dfs(nodeValList, 0));
        return nodeList.get(0);
    }
    
    public List<TreeNode> dfs(List<String> nodeValList, int index) {
        List<TreeNode> nodeList = new ArrayList<>();
        if (index >= nodeValList.size()) return nodeList;
        if (nodeValList.get(index).length() == 0) { // string is "", which means the node is null
            nodeList.add(null);
            return nodeList;
        }
        
        TreeNode node = new TreeNode(Integer.parseInt(nodeValList.get(index)));
        nodeList.add(node);
        List<TreeNode> leftNodeList = dfs(nodeValList, index+1);
        List<TreeNode> rightNodeList = dfs(nodeValList, index+1+leftNodeList.size());
        if (leftNodeList.size() > 0) node.left = leftNodeList.get(0);
        if (rightNodeList.size() > 0) node.right = rightNodeList.get(0);
        nodeList.addAll(leftNodeList);
        nodeList.addAll(rightNodeList);
        return nodeList;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
