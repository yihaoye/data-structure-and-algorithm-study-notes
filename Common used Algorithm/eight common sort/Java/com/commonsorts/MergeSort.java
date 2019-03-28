package com.commonsorts;

// 归并排序（时间复杂度：O(nlog2n)，空间复杂度：O(n+logn)，稳定）
public class MergeSort {
    public void mergeSort(int[] a)
    { 
        mSort(a, 0, a.length-1);
    }

    /**
     * 归并排序
     *
     * @param a
     * @param L      指向数组第一个元素
     * @param R      指向数组最后一个元素
     */
    public void mSort(int[] a, int L, int R) {
        //如果只有一个元素，那就不用排序了
        if (L == R) {
            return;
        } else {
            //取中间的数，进行拆分
            int M = (L + R) / 2;
            //左边的数不断进行拆分
            mSort(a, L, M);
            //右边的数不断进行拆分
            mSort(a, M + 1, R);
            //合并
            merge(a, L, M + 1, R);
        }
    }

    /**
     * 合并数组
     *
     * @param a
     * @param L      指向数组第一个元素
     * @param M      指向数组分隔的元素
     * @param R      指向数组最后的元素
     */
    public void merge(int[] a, int L, int M, int R) {
        //左边的数组的大小
        int[] leftArray = new int[M - L];
        //右边的数组大小
        int[] rightArray = new int[R - M + 1];
        //往这两个数组填充数据
        for (int i = L; i < M; i++) {
            leftArray[i - L] = a[i];
        }
        for (int i = M; i <= R; i++) {
            rightArray[i - M] = a[i];
        }

        int i = 0, j = 0;
        // a数组的第一个元素
        int  k = L;

        //比较这两个数组的值，哪个小，就往数组上放
        while (i < leftArray.length && j < rightArray.length) {
            //谁比较小，谁将元素放入大数组中,移动指针，继续比较下一个
            if (leftArray[i] < rightArray[j]) {
                a[k] = leftArray[i];
                i++;
                k++;
            } else {
                a[k] = rightArray[j];
                j++;
                k++;
            }
        }
        //如果左边的数组还没比较完，右边的数都已经完了，那么将左边的数抄到大数组中(剩下的都是大数字)
        while (i < leftArray.length) {
            a[k] = leftArray[i];
            i++;
            k++;
        }
        //如果右边的数组还没比较完，左边的数都已经完了，那么将右边的数抄到大数组中(剩下的都是大数字)
        while (j < rightArray.length) {
            a[k] = rightArray[j];
            k++;
            j++;
        }
    }
}
// 作者：Java3y
// 链接：https://juejin.im/post/5ab4c7566fb9a028cb2d9126



/* 
    // 大话数据结构
    // 将有序的SR[i..m]和SR[m+1..n]归并为有序的TR[i..n] 
    void Merge(int SR[],int TR[],int i,int m,int n)
    {
        int j,k,l;
        for(j=m+1,k=i;i<=m && j<=n;k++)	// 将SR中记录由小到大地并入TR
        {
            if (SR[i]<SR[j])
                TR[k]=SR[i++];
            else
                TR[k]=SR[j++];
        }
        if(i<=m)
        {
            for(l=0;l<=m-i;l++)
                TR[k+l]=SR[i+l];		// 将剩余的SR[i..m]复制到TR
        }
        if(j<=n)
        {
            for(l=0;l<=n-j;l++)
                TR[k+l]=SR[j+l];		// 将剩余的SR[j..n]复制到TR
        }
    }


    // 递归法
    // 将SR[s..t]归并排序为TR1[s..t]
    void MSort(int SR[],int TR1[],int s, int t)
    {
        int m;
        int TR2[MAXSIZE+1];
        if(s==t)
            TR1[s]=SR[s];
        else
        {
            m=(s+t)/2;				// 将SR[s..t]平分为SR[s..m]和SR[m+1..t]
            MSort(SR,TR2,s,m);		// 递归地将SR[s..m]归并为有序的TR2[s..m]
            MSort(SR,TR2,m+1,t);	// 递归地将SR[m+1..t]归并为有序的TR2[m+1..t]
            Merge(TR2,TR1,s,m,t);	// 将TR2[s..m]和TR2[m+1..t]归并到TR1[s..t]
        }
    }
    // 对顺序表L作归并排序
    void MergeSort(SqList *L)
    { 
        MSort(L->r,L->r,1,L->length);
    }

    
    // 非递归法
    // 将SR[]中相邻长度为s的子序列两两归并到TR[]
    void MergePass(int SR[],int TR[],int s,int n)
    {
        int i=1;
        int j;
        while(i <= n-2*s+1)
        {// 两两归并
            Merge(SR,TR,i,i+s-1,i+2*s-1);
            i=i+2*s;        
        }
        if(i<n-s+1) // 归并最后两个序列
            Merge(SR,TR,i,i+s-1,n);
        else // 若最后只剩下单个子序列
            for(j =i;j <= n;j++)
                TR[j] = SR[j];
    }
    // 对顺序表L作归并非递归排序
    void MergeSort2(SqList *L)
    {
        int* TR=(int*)malloc(L->length * sizeof(int));// 申请额外空间
        int k=1;
        while(k<L->length)
        {
            MergePass(L->r,TR,k,L->length);
            k=2*k;// 子序列长度加倍
            MergePass(TR,L->r,k,L->length);
            k=2*k;// 子序列长度加倍
        }
    }
*/