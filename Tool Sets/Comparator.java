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



import java.util.Collections;

public static void main(String[] args) {
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
}
