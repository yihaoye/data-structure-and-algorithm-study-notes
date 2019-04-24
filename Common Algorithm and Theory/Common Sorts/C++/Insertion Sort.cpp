template<typename T> //整數或浮點數皆可使用,若要使用物件(class)時必須設定大於(>)的運算子功能
void insertion_sort(T arr[], int len) {
 	int i, j;
 	T temp;
 	for (i = 1; i < len; i++) {
 		temp = arr[i];
 		j = i - 1; // 如果将赋值放到下一行的for循环内, 会导致在第10行出现j未声明的错误
 		for (; j >= 0 && arr[j] > temp; j--)
 			arr[j + 1] = arr[j];//最后一次执行该语句后，跳出当前for循环前，会再一次执行j--
 		arr[j + 1] = temp;//执行完上一个语句（即for语句）后，跳出的位置保存在j中，此时arr[j]的值是没有经过移动的，不能替换，应该替换的是arr[j+1]
 	}
}