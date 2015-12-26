//Question:
/*
Given a binary tree, find its maximum depth.

The maximum depth is the number of nodes along the longest path from 
the root node down to the farthest leaf node.

*/


//Solution: (8ms Recursive/BFS C++ Solutions)
/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 * };
 */
class Solution {
public:
    int maxDepth(TreeNode* root) {
        int maxDepth(TreeNode* root) {
        return root ? 1 + max(maxDepth(root -> left), maxDepth(root -> right)) : 0;
    }
    }
};