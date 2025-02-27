/*
emplyee directory
multiple group, and nested group, every employee is within a group

find closest common group of employees (given target)
employee name to identify

-            Company

          /           \

        HR               Engg

      /   \          /          \ 

     Mona  Springs  BE          FE 

                    /  \        / \ 

                  Alice Bob  Lisa Marley

must have common group
*/

import java.util.*;

// 定义多叉树节点
class TreeNode {
    String val;
    List<TreeNode> children;
    
    TreeNode(String x) {
        val = x;
        children = new ArrayList<>();
    }
    
    void addChild(TreeNode child) {
        children.add(child);
    }
}

public class Solution {
    // 寻找多个节点的 LCA
    public TreeNode findLCA(TreeNode root, Set<String> targets) {
        if (root == null || targets.isEmpty()) {
            return null;
        }
        
        Result result = findLCAHelper(root, targets);
        return result.foundAll ? result.node : null;
    }
    
    // 辅助类，用于记录递归过程中的结果
    private static class Result {
        TreeNode node;      // 当前子树中找到的 LCA
        int count;          // 找到的目标节点数量
        boolean foundAll;   // 是否找到所有目标节点
        
        Result(TreeNode node, int count) {
            this.node = node;
            this.count = count;
            this.foundAll = false;
        }
    }
    
    private Result findLCAHelper(TreeNode node, Set<String> targets) {
        if (node == null) return new Result(null, 0);
        
        // 检查当前节点是否是目标之一
        int totalFound = targets.contains(node.val) ? 1 : 0;
        
        // 递归遍历所有子节点
        for (TreeNode child : node.children) {
            Result childResult = findLCAHelper(child, targets);
            // 如果子树中找到了所有目标节点，则直接返回该子树的结果
            if (childResult.foundAll) return childResult;
            // 否则继续计算当前结果
            totalFound += childResult.count;         
        }
        
        // 创建当前节点的结果
        Result result = new Result(node, totalFound);
        // 如果当前子树找到了所有目标节点
        if (totalFound == targets.size()) result.foundAll = true;
        return result;
    }
    
    // 测试代码
    public static void main(String[] args) {
        // 创建示例多叉树
        //           "root"
        //         /   |   \
        //    "node1" "node2" "node3"
        //    /  |      |      |   \
        // "a" "b"     "c"    "d"  "e"
        
        TreeNode root = new TreeNode("root");
        
        TreeNode node1 = new TreeNode("node1");
        TreeNode node2 = new TreeNode("node2");
        TreeNode node3 = new TreeNode("node3");
        
        root.addChild(node1);
        root.addChild(node2);
        root.addChild(node3);
        
        TreeNode a = new TreeNode("a");
        TreeNode b = new TreeNode("b");
        node1.addChild(a);
        node1.addChild(b);
        
        TreeNode c = new TreeNode("c");
        node2.addChild(c);
        
        TreeNode d = new TreeNode("d");
        TreeNode e = new TreeNode("e");
        node3.addChild(d);
        node3.addChild(e);
        
        Solution solution = new Solution();
        
        // 测试案例
        Set<String> targets1 = new HashSet<>(Arrays.asList("a", "b"));
        System.out.println("LCA of a and b: " + solution.findLCA(root, targets1).val); // 应该输出 "node1"
        
        Set<String> targets2 = new HashSet<>(Arrays.asList("a", "c"));
        System.out.println("LCA of a and c: " + solution.findLCA(root, targets2).val); // 应该输出 "root"
        
        Set<String> targets3 = new HashSet<>(Arrays.asList("a", "b", "c"));
        System.out.println("LCA of a, b and c: " + solution.findLCA(root, targets3).val); // 应该输出 "root"
        
        Set<String> targets4 = new HashSet<>(Arrays.asList("d", "e"));
        System.out.println("LCA of d and e: " + solution.findLCA(root, targets4).val); // 应该输出 "node3"
    }
}
