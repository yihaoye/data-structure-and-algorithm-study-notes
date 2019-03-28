//Question:
/*
Invert a binary tree.

Example:

from

     4
   /   \
  2     7
 / \   / \
1   3 6   9

to

     4
   /   \
  7     2
 / \   / \
9   6 3   1

*/



//Solution：
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
    TreeNode* invertTree(TreeNode* root) {
        TreeNode* temp;
        if(root!=NULL && (root->left||root->right))
        {
            temp = root->left;
            root->left = root->right;
            root->right = temp;
            invertTree(root->left);
            invertTree(root->right);
        }
        
        return root;
    }
};

//Attention!
//This is a classic question, should be practiced more.


//Another Solution: (others' solution and it is non-recursion!!!!!)
//C++, no-recursion, clean BFS solution
TreeNode* invertTree(TreeNode* root) {

    if(nullptr == root) return root;

    queue<TreeNode*> myQueue;   // our queue to do BFS
    myQueue.push(root);         // push very first item - root

    while(!myQueue.empty()){    // run until there are nodes in the queue 

        TreeNode *node = myQueue.front();  // get element from queue
        myQueue.pop();                     // remove element from queue

        if(node->left != nullptr){         // add left kid to the queue if it exists
            myQueue.push(node->left);
        }

        if(node->right != nullptr){        // add right kid 
            myQueue.push(node->right);
        }

        // invert left and right pointers      
        TreeNode* tmp = node->left;
        node->left = node->right;
        node->right = tmp;

    }

    return root;
} 