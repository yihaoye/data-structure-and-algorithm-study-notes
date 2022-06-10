// 状态压缩

/* 比如 Q688 */
public int arrToInt(int[] arr) {
    return (arr[0] << 5) | arr[1];
}

public int[] intToArr(int i) {
    return new int[]{i >> 5, i & 31};
}
/************/ 
