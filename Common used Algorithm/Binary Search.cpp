

// 递归版本 ("start"原始值为0，"end"原始值为arr.length()-1)
int binary_search(const int arr[], int start, int end, int key) {
	if (start > end)
		return -1;

	int mid = start + (end - start) / 2; //直接平均可能會溢位，所以用此算法
	if (arr[mid] > key)
		return binary_search(arr, start, mid - 1, key);
	if (arr[mid] < key)
		return binary_search(arr, mid + 1, end, key);
	return mid; //最後檢測相等是因為多數搜尋狀況不是大於要不就小於
}

// while循环
int binary_search(const int arr[], int start, int end, int key) {
	int mid;
	while (start <= end) {
		mid = start + (end - start) / 2; //直接平均可能會溢位，所以用此算法
		if (arr[mid] < key)
			start = mid + 1;
		else if (arr[mid] > key)
			end = mid - 1;
		else
			return mid; //最後檢測相等是因為多數搜尋狀況不是大於要不就小於
	}
	return -1;
}
