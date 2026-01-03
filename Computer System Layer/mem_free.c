// C 语言中手动内存管理示例
#include <stdio.h>
#include <stdlib.h> // 必须包含此头文件

int main() {
    int n = 5;

    // 1. 申请内存 (Allocating)
    // 相当于 Java/Python 的 "new"，但系统不会自动清理
    int *arr = (int *)malloc(n * sizeof(int));

    // 检查申请是否成功
    if (arr == NULL) return 1;

    // 2. 使用内存
    for (int i = 0; i < n; i++) {
        arr[i] = i * 10;
        printf("%d ", arr[i]);
    }

    // 3. 手动释放内存 (Freeing) - GC 语言中这一步是自动的
    // 如果漏掉这一行，程序运行久了会耗尽系统内存
    free(arr); 

    // 4. 良好的习惯：将指针置为空，防止“野指针”误用
    arr = NULL;

    return 0;
}
