package com.commonsorts;

// 堆排序 (时间复杂度：O(nlogn)，稳定，是简单选择排序的改良版)
public class HeapSort {
    public void heapSort(int[] a) {
        int i;
        for (i = a.length/2-1; i>0; i--) { /*  把a中的所有元素构建成一个大根堆 */
            HeapAdjust(a, i, a.length-1); /* 以数组形式存在的堆（以数组index暗示在二叉树的哪个、第几个结点上。完全二叉树 - 根结点数值比其任何孩子结点数值都大，但左子树未必大于右子树） */
        }
        for (i = a.length-1; i>1; i--) { 
            swap(a, 1, i); /* 将堆顶记录和当前未经排序子序列的最后一个记录交换 */
            HeapAdjust(a, 1, i-1); /*  将a[1..i-1]重新调整为大根堆 */
        }
    }

    /* 已知a[s..m]中记录的关键字除a[s]之外均满足堆的定义， */
    /* 本函数调整a[s]的关键字,使a[s..m]成为一个大顶堆 */
    public void HeapAdjust(int[] a, int s, int m) { 
        int temp, j;
        temp = a[s];
        for (j = 2*s; j <= m; j *= 2) { /* 沿关键字较大的孩子结点向下筛选 */
            if (j < m && a[j] < a[j+1]) {
                ++j; /* j为关键字中较大的记录的下标 */
            }
            if (temp >= a[j]) {
                break; /* rc应插入在位置s上 */
            }
            a[s] = a[j];
            s = j;
        }
        a[s] = temp; /* 插入 */
    }

    public void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}


/* 大话数据结构 
    // 对顺序表L进行堆排序
    void HeapSort(SqList *L)
    {
        int i;
        for(i=L->length/2;i>0;i--) // 把L中的r构建成一个大根堆
            HeapAdjust(L,i,L->length);

        for(i=L->length;i>1;i--)
        { 
            swap(L,1,i); // 将堆顶记录和当前未经排序子序列的最后一个记录交换
            HeapAdjust(L,1,i-1); // 将L->r[1..i-1]重新调整为大根堆
        }
    }

    // 已知L->r[s..m]中记录的关键字除L->r[s]之外均满足堆的定义
    // 本函数调整L->r[s]的关键字,使L->r[s..m]成为一个大顶堆
    void HeapAdjust(SqList *L,int s,int m)
    { 
        int temp,j;
        temp=L->r[s];
        for(j=2*s;j<=m;j*=2) // 沿关键字较大的孩子结点向下筛选
        {
            if(j<m && L->r[j]<L->r[j+1])
                ++j; // j为关键字中较大的记录的下标
            if(temp>=L->r[j])
                break; // rc应插入在位置s上
            L->r[s]=L->r[j];
            s=j;
        }
        L->r[s]=temp; // 插入
    }
 */