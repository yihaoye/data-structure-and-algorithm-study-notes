package com.commonsorts;

// 冒泡排序（改良，时间复杂度：O(n^2)，稳定）
public class BubbleSort {
	public void bubbleSort(int[] a) { 
		int i, j;
		/* flag用来作为标记 */
		boolean flag = true;
		/* 若flag为true说明有过数据交换，否则停止循环 */
		for (i = 1; i < a.length && flag; i++) {
			/* 初始为False */
			flag = false;
			for (j = a.length - 1; j >= i; j--) {
				if (a[j-1] > a[j]) {
					/* 交换a[j-1]与a[j]的值 */
					swap(a, j-1, j);
					/* 如果有数据交换，则flag为true */
					flag = true;
				}
			}
		}
	}

	public void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}