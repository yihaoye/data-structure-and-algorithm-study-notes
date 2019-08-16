/* Question:
实现一个算法，找出单向链表中倒数第 k 个结点。
*/



node* nthToLast(node* head, int k, int& i) {
    if (head == NULL) {
        return NULL;
    }
    node* nd = nthToLast(head->next, k, i);
    i = i + 1;
    if (i == k) {
        return head;
    }
    return nd;
}