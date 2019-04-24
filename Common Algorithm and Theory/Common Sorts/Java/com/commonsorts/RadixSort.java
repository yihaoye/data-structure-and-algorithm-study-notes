package com.commonsorts;

// 基数排序 (时间复杂度：O(k·n)，稳定)
/*
基数排序的时间复杂度是O(k·n)，其中n是排序元素个数，k是数字位数。注意这不是说这个时间复杂度一定优于O(n·log(n))，k的大小取决于数字位的选择（比如比特位数），
和待排序数据所属数据类型的全集的大小；k决定了进行多少轮处理，而n是每轮处理的操作数目。
*/

public class RadixSort {
    private static final int base = 10; // 基数桶[0-9]
    public void radixSort(int[] a) {
        int size = a.length;
        if (a == null || size == 0) return;
    
        //找出最大数
        int max = a[0];
        int i;
        for (i = 1; i < size; ++i) {
            if (a[i] > max) {
                max = a[i];
            }
        }
    
        int exp = 1; //位数
        int[] temp = new int[size];
        while (max / exp > 0) {
            //重置基数桶
            int[] bucket = new int[base];
            //统计每个基数上有多少个数据
            for (i = 0; i < size; ++i) {
                bucket[(a[i] / exp) % base]++;
            }
            //求出基数桶的边界索引,bucket[i]值为第i个桶的右边界索引+1
            for (i = 1; i < base; ++i) {
                bucket[i] += bucket[i - 1];
            }
            //这里要从右向左扫描，保证排序稳定性
            for (i = size - 1; i >= 0; i--) {
                temp[--bucket[(a[i] / exp) % base]] = a[i];
            }
            //将基数桶排好的数据赋值到原数据，完成一趟排序
            for (i = 0; i < size; ++i) {
                a[i] = temp[i];
            }
            exp *= base; //位数递增
        }
    }
}

// from：https://blog.csdn.net/daiyudong2020/article/details/52566364 
