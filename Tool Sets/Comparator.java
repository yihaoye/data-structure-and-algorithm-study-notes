// The Comparator interface defines a compare(arg1, arg2) method with two arguments that represent compared objects and works similarly to the Comparable.compareTo() method.

public class Obj {
 
    private int val1;

    // constructor, getters, setters
}

public class ObjVal1Comparator implements Comparator<Obj> {
 
    @Override
    public int compare(Obj firstObj, Obj secondObj) {
       return Integer.compare(firstObj.getVal1(), secondObj.getVal1());
    }
}

public class StringComparator implements Comparator<String> {

    @Override
    public int compare(String str1, String str2) {
        int l1 = str1.length(); 
        int l2 = str2.length(); 
        int lmin = Math.min(l1, l2); 
  
        for (int i = 0; i < lmin; i++) { 
            int str1_ch = (int)str1.charAt(i); 
            int str2_ch = (int)str2.charAt(i); 
            if (str1_ch != str2_ch) return str1_ch - str2_ch; 
        } 
  
        if (l1 != l2) return l1 - l2; // Edge case for strings like str1="ABC" and str2="ABCC"
        else return 0; // If none of the above conditions is true, it implies both the strings are equal 
    }
}



import java.util.*; // Collections, Arrays, Comparator etc

public static void main(String[] args) {
    // 对象 Comparator
    List<Obj> objs = new ArrayList<>();
    Obj obj1 = new Obj(59);
    Obj obj2 = new Obj(67);
    Obj obj3 = new Obj(45);
    objs.add(obj1);
    objs.add(obj2);
    objs.add(obj3);
 
    System.out.println("Before Sorting : " + objs);
    ObjVal1Comparator objVal1Comparator = new ObjVal1Comparator();
    Collections.sort(objs, objVal1Comparator);
    System.out.println("After Sorting : " + objs);


    // 字符串 Comparator（自定义其一）
    String[] sArray = {"ABC", "DAB", "ABCC", "ABC", "abc"}
    Arrays.sort(sArray, new StringComparator());


    // 多维数组 Comparator
    double[][] array= {{1, 5}, {13, 1.55}, {12, 100.6}, {12.1, .85}};
    Arrays.sort(array, new Comparator<double[]>() {
        @Override
        public int compare(double[] a, double[] b) {
            return Double.compare(a[0], b[0]);
            // or
            // return a[0].compareTo(b[0]);
        }
    });
    Arrays.sort(array, Comparator.comparingDouble(o -> o[0])); // Java 8 lambda function
    Arrays.sort(array, Comparator.comparingDouble(o -> o[1])); // sort 2D array based on 2nd element
    Arrays.sort(array, (a, b) -> Double.compare(a[0], b[0]));
}



// 多重排序，比如首先按 primaryComparator 排序然后按 secondaryComparator 排序
// If you are sorting an array, use Arrays.sort().
Arrays.sort(array, secondaryComparator);  // This must come first!
Arrays.sort(array, primaryComparator);
// If you are sorting a List, use Collections.sort().
Collections.sort(list, secondaryComparator);  // This must come first!
Collections.sort(list, primaryComparator);
// Both of above methods are guaranteed to be stable.
