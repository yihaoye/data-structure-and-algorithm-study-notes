//Question:
/* 
Given a linked list, swap every two adjacent nodes and return its head.

For example,
Given 1->2->3->4, you should return the list as 2->1->4->3.

Your algorithm should use only constant space. You may not modify the values in the list, only nodes itself can be changed. 
*/



//Other's Answer:
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
    ListNode* swapPairs(ListNode* head) {
        ListNode* dummy = new ListNode(0);
        dummy->next = head;
        ListNode* current = dummy;
        while (current->next != NULL && current->next->next != NULL) {
            ListNode* first = current->next;
            ListNode* second = current->next->next;
            first->next = second->next;
            current->next = second;
            current->next->next = first;
            current = current->next->next;
        }
        return dummy->next;
        
        /*
        if (!head || !(head->next))
            return head;
            
        ListNode* temp_node = new ListNode(0);
        ListNode* p = new ListNode(0);
        ListNode* h = new ListNode(0);
        p = head->next;
        h->next = p->next;
        for(p = head->next; p != NULL; p = p->next->next)
        {
            temp_node = p->next;
            p->next = temp_node->next;
            temp_node->next = p;
        }
        
        return h;
        */
    }
};



//My Solution: (not pass yet, still need debug)
class Solution {
public:
    ListNode* swapPairs(ListNode* head) {
        
        if (!head || !(head->next))
            return head;
            
        ListNode* temp_node = new ListNode(0);
        ListNode* p = new ListNode(0);
        ListNode* h = new ListNode(0);
        p = head->next;
        h->next = p->next;
        for(p = head->next; p != NULL; p = p->next->next)
        {
            temp_node = p->next;
            p->next = temp_node->next;
            temp_node->next = p;
        }
        
        return h;
    }
};