//Question:
/*
You are given two linked lists representing two non-negative numbers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 0 -> 8
*/



//Answer:
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {} //构造函数
 * };
 */

class Solution {
public:
    ListNode* addTwoNumbers(ListNode* l1, ListNode* l2) {
        ListNode *p = new ListNode(0);
        ListNode *point = p;
        int sum = 0;
        while(l1 != NULL || l2 != NULL)
        {
            
            sum += (l1!=NULL ? l1->val:0) + (l2!=NULL ? l2->val:0);
            p->val = sum % 10;
            sum = sum / 10;
            l1 = (l1==NULL ? NULL:l1->next); // if(l1)l1 = l1->next;
            l2 = (l2==NULL ? NULL:l2->next); 
            if(l1||l2||sum>0){
                p->next = new ListNode(sum);
                p = p->next;
            }
        }
        return point;
    }
};