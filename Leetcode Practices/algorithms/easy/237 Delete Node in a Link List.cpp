//Question:
/*
 Write a function to delete a node (except the tail) in a singly linked list, 
 given only access to that node.

 Supposed the linked list is 1 -> 2 -> 3 -> 4 and you are given the third node with value 3, 
 the linked list should become 1 -> 2 -> 4 after calling your function. 
*/


//Solution:
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    void deleteNode(ListNode* node) {
        node->val = node->next->val;
        ListNode* temp;
        temp = node->next;
        node->next = node->next->next;
        
        free(temp);
    }
};

//Others' Discuss:
/*
guy 1:
Since we couldn't enter the preceding node, we can not delete the given node. 
We can just copy the next node to the given node and delete the next one.
With Java, no need to free() the last Node, since JVM will do the cleanup for you (garbage collection),
Java Virtual Machine removes objects that have no reference pointing to them.

guy 2:
However, this question is INCORRECT for sure, since you don't really "delete" a node, 
"Deleting means free the memory".
you are replacing the value. In fact, this is a terrible design leading to memory leaks almost for sure.
I wonder what company gives such misleading question. It's better called "modify" a node, 
instead of "deleting". Deleting means free the memory, 
and the incorrect description will mislead any person with slight experience on C++.
*/