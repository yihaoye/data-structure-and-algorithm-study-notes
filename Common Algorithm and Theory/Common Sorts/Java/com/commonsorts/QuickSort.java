package com.commonsorts;

public class QuickSort {
	public void quickSort(int[] a) {
		sort(a, 0, a.length - 1);
	}
	
    public void sort(int[] arr, int l, int r) {
        if (l >= r || arr == null || arr.length <= 1) return;

        int i = l - 1, j = r + 1, x = arr[l + (r - l) / 2];
        while (i < j) {
            do {
                i++;
            } while (arr[i] < x);
            do {
                j--;
            } while (arr[j] > x);
            if (i < j) {
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        sort(arr, l, j);
        sort(arr, j + 1, r);
    }
}



/*
希尔排序相当于直接插入排序的升级，它们同属于插入排序类，堆排序相当于简单选择排序的升级，它们同属于选择排序类。

而快速排序其实就是我们前面认为最慢的冒泡排序的升级，它们都属于交换排序类。
即它也是通过不断比较和移动交换来实现排序的，只不过它的实现，增大了记录的比较和移动的距离，将关键字较大的记录从前面直接移动到后面，关键字较小的记录从后面直接移动到前面，从而减少了总的比较次数和移动交换次数。
<大话数据结构>
*/





/* <大话数据结构> 链表

// 交换顺序表L中子表的记录，使枢轴记录到位，并返回其所在位置
// 此时在它之前(后)的记录均不大(小)于它。
int Partition(SqList *L,int low,int high)
{
	int pivotkey;

	pivotkey=L->r[low]; // 用子表的第一个记录作枢轴记录
	while(low<high) // 从表的两端交替地向中间扫描
	{
		 while(low<high&&L->r[high]>=pivotkey)
			high--;
		 swap(L,low,high); // 将比枢轴记录小的记录交换到低端
		 while(low<high&&L->r[low]<=pivotkey)
			low++;
		 swap(L,low,high); // 将比枢轴记录大的记录交换到高端
	}
	return low; // 返回枢轴所在位置
}
// 通过这段代码的模拟，大家应该能够明白，Partition函数，其实就是将选取的pivotkey不断交换，将比它小的换到它的左边，比它大的换到它的右边，它也在交换中不断更改自己的位置，直到完全满足这个要求为止。

// 对顺序表L中的子序列L->r[low..high]作快速排序
void QSort(SqList *L,int low,int high)
{
	int pivot;
	if(low<high)
	{
			pivot=Partition(L,low,high); // 将L->r[low..high]一分为二，算出枢轴值pivot
			QSort(L,low,pivot-1);		// 对低子表递归排序
			QSort(L,pivot+1,high);		// 对高子表递归排序
	}
}

// 对顺序表L作快速排序
void QuickSort(SqList *L)
{
	QSort(L,1,L->length);
}



// 改进后快速排序

// 快速排序优化算法
int Partition1(SqList *L,int low,int high)
{
	int pivotkey;

	int m = low + (high - low) / 2; // 计算数组中间的元素的下标
	if (L->r[low]>L->r[high])
		swap(L,low,high);	// 交换左端与右端数据，保证左端较小
	if (L->r[m]>L->r[high])
		swap(L,high,m);		// 交换中间与右端数据，保证中间较小
	if (L->r[m]>L->r[low])
		swap(L,m,low);		// 交换中间与左端数据，保证左端较大

	pivotkey=L->r[low]; // 用子表的第一个记录作枢轴记录
	L->r[0]=pivotkey;  // 将枢轴关键字备份到L->r[0]
	while(low<high) // 从表的两端交替地向中间扫描
	{
		 while(low<high&&L->r[high]>=pivotkey)
			high--;
		 L->r[low]=L->r[high];
		 while(low<high&&L->r[low]<=pivotkey)
			low++;
		 L->r[high]=L->r[low];
	}
	L->r[low]=L->r[0];
	return low; // 返回枢轴所在位置
}

// 如果数组非常小，其实快速排序反而不如直接插入排序来得更好（直接插入是简单排序中性能最好的）。其原因在于快速排序用到了递归操作，在大量数据排序时，这点性能影响相对于它的整体算法优势而言是可以忽略的，但如果数组只有几个记录需要排序时，这就成了一个大炮打蚊子的大问题。因此我们需要改进一下QSort函数。
// 递归对性能是有一定影响的，QSort函数在其尾部有两次递归操作。如果待排序的序列划分极端不平衡，递归深度将趋近于n，而不是平衡时的log 2n，这就不仅仅是速度快慢的问题了。栈的大小是很有限的，每次递归调用都会耗费一定的栈空间，函数的参数越多，每次递归耗费的空间也越多。因此如果能减少递归，将会大大提高性能。
void QSort1(SqList *L,int low,int high)
{
	int pivot;
	if((high-low)>MAX_LENGTH_INSERT_SORT)
	{
		while(low<high)
		{
			pivot=Partition1(L,low,high); // 将L->r[low..high]一分为二，算出枢轴值pivot
			QSort1(L,low,pivot-1);		// 对低子表递归排序
			low=pivot+1;	// 尾递归
		}
	}
	else
		InsertSort(L);
}

// 对顺序表L作快速排序
void QuickSort1(SqList *L)
{
	QSort1(L,1,L->length);
}

// 三数取中对小数组来说有很大的概率选择到一个比较好的pivotkey，但是对于非常大的待排序的序列来说还是不足以保证能够选择出一个好的pivotkey，因此还有个办法是所谓九数取中（me-dian-of-nine），它先从数组中分三次取样，每次取三个数，三个样品各取出中数，然后从这三个中数当中再取出一个中数作为枢轴。显然这就更加保证了取到的pivotkey是比较接近中间值的关键字。有兴趣的同学可以自己去实现一下代码，这里不再详述了。
*/