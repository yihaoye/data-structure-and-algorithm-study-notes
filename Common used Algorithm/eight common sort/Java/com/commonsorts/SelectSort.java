package com.commonsorts;

// 简单选择排序（对链表更友好 SqList *L）（时间复杂度：O(n^2)，不稳定）
// 选择排序与冒泡排序原理基本一样，只是选择排序尽量减少数据元素移动、交换次数，比较次数仍是一样。应该说，尽管与冒泡排序同为O(n2)，但简单选择排序的性能上还是要略优于冒泡排序。<大话数据结构> 
public class SelectSort {
    public void selectSort(int[] a) { // SqList *L 作为参数也可，代码稍作链表语法改动即可
        int i, j, min;
        for (i = 1; i < a.length; i++) {
            /* 将当前下标定义为最小值下标 */
            min = i;
            /* 循环之后的数据 */
            for (j = i + 1; j < a.length; j++) {
                /* 如果有小于当前最小值的关键字 */
                if (a[min] > a[j]) {
                    /* 将此关键字的下标赋值给min */
                    min = j;
                }
            }
            /* 若min不等于i，说明找到最小值，交换 */
            if (i != min) {
                /* 交换L->r[i]与L->r[min]的值 */
                swap(a, i, min);
            }
        }
    }

    public void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /* 链表swap
    public void swap(SqList *L, int i, int j) {
        int temp = L->r[i];
        L->r[i] = L->r[j];
        L->r[j] = temp;
    }
    */
}
