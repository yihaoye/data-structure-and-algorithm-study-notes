import com.commonsorts.*;

public class Tests {
    public static void main(String[] args) {
        int[] messArr = new int[]{1, 4, 8, 2, 55, 3, 4, 8, 6, 4, 0, 11, 34, 90, 23, 54, 77, 9, 2, 9, 4, 10};
        
        // quickSort
        int[] arr = messArr;
        new QuickSort().quickSort(arr);
        String out = "";
        for (int digit : arr) {
            out += (digit + ",");
        }
        System.out.println(out);

        // insertSort
        arr = messArr;
        new InsertSort().insertSort(arr);
        out = "";
        for (int digit : arr) {
            out += (digit + ",");
        }
        System.out.println(out);

        // shellSort
        arr = messArr;
        new ShellSort().shellSort(arr);
        out = "";
        for (int digit : arr) {
            out += (digit + ",");
        }
        System.out.println(out);

        // selectSort
        arr = messArr;
        new SelectSort().selectSort(arr);
        out = "";
        for (int digit : arr) {
            out += (digit + ",");
        }
        System.out.println(out);

        // bubbleSort
        arr = messArr;
        new BubbleSort().bubbleSort(arr);
        out = "";
        for (int digit : arr) {
            out += (digit + ",");
        }
        System.out.println(out);

        // heapSort
        arr = messArr;
        new HeapSort().heapSort(arr);
        out = "";
        for (int digit : arr) {
            out += (digit + ",");
        }
        System.out.println(out);

        // mergeSort
        arr = messArr;
        new MergeSort().mergeSort(arr);
        out = "";
        for (int digit : arr) {
            out += (digit + ",");
        }
        System.out.println(out);

        // bucketSort
        arr = messArr;
        new BucketSort().bucketSort(arr);
        out = "";
        for (int digit : arr) {
            out += (digit + ",");
        }
        System.out.println(out);

        // radixSort
        arr = messArr;
        new RadixSort().radixSort(arr);
        out = "";
        for (int digit : arr) {
            out += (digit + ",");
        }
        System.out.println(out);
    }
}