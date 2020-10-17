// As the name suggests, Comparable is an interface defining a strategy of comparing an object with other objects of the same type. This is called the class's “natural ordering”.

public class Obj implements Comparable<Obj> {
 
    private int val1;

    // constructor, getters, setters
 
    @Override
    public int compareTo(Obj otherObj) {
        return Integer.compare(getVal1(), otherObj.getVal1());
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
    Collections.sort(objs);
    System.out.println("After Sorting : " + objs);
}