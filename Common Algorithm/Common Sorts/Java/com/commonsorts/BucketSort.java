package com.commonsorts;

// 桶排序 (时间复杂度：O(n+k)，稳定)


public class BucketSort {
	public void bucketSort(int[] a, int maxVal) {
        int[] bucket = new int[maxVal+1];

        for (int i = 0; i < bucket.length; i++) {
            bucket[i] = 0;
        }

        for (int i = 0; i < a.length; i++) {
            bucket[a[i]]++;
        }

        int outPos = 0;
        for (int i = 0; i < bucket.length; i++) {
            for (int j = 0; j < bucket[i]; j++) {
                a[outPos++] = i;
            }
        }
    }
}


// 参考BYVoid Solution: https://www.byvoid.com/zhs/blog/sort-radix
/*
 * Problem: Bucket Sort
 * Author: Guo Jiabao
 * Time: 2009.3.29 16:32
 * State: Solved
 * Memo: 桶排序特殊实现
*/
/*
#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <cmath>
#include <cstring>

using namespace std;
void BucketSort(int *A,int *B,int N,int K)
{
    int *C=new int[K+1];
    int i,j,k;
    memset(C,0,sizeof(int)*(K+1));
    for (i=1;i<=N;i++) //把A中的每个元素按照值放入桶中
        C[A[i]]++;
    for (i=j=1;i<=K;i++,j=k) //统计每个桶元素的个数，并输出到B
        for (k=j;k<j+C[i];k++)
            B[k]=i;
}

int main()
{
    int *A,*B,N=1000,K=1000,i;
    A=new int[N+1];
    B=new int[N+1];
    for (i=1;i<=N;i++)
        A[i]=rand()%K+1; //生成1..K的随机数
    BucketSort(A,B,N,K);
    for (i=1;i<=N;i++)
        printf("%d ",B[i]);
    return 0;
}
*/