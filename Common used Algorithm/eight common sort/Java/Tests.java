import com.commonsorts.*;

public class Tests {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 4, 8, 2, 55, 3, 4, 8, 6, 4, 0, 11, 34, 90, 23, 54, 77, 9, 2, 9, 4, 10};
        
        // quickSort
        new QuickSort().quickSort(arr, 0, arr.length - 1);
        String out = "";
        for (int digit : arr) {
            out += (digit + ",");
        }
        System.out.println(out);

        // insertSort
        new InsertSort().insertSort(arr);
        out = "";
        for (int digit : arr) {
            out += (digit + ",");
        }
        System.out.println(out);

        // shellSort
        new ShellSort().shellSort(arr);
        out = "";
        for (int digit : arr) {
            out += (digit + ",");
        }
        System.out.println(out);

        // selectSort
        new SelectSort().selectSort(arr);
        out = "";
        for (int digit : arr) {
            out += (digit + ",");
        }
        System.out.println(out);

        // bubbleSort
        new BubbleSort().bubbleSort(arr);
        out = "";
        for (int digit : arr) {
            out += (digit + ",");
        }
        System.out.println(out);

        // heapSort
        new HeapSort().heapSort(arr);
        out = "";
        for (int digit : arr) {
            out += (digit + ",");
        }
        System.out.println(out);
    }
}