int val1 = 10;
Integer.hashCode(val1);

String val2 = "test";
val2.hashCode();



public class Obj {
    private long id;
    private String val1;
    private String val2;
 
    // standard getters/setters/constructors
        
    @Override
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + (int) id;
        hash = 31 * hash + (val1 == null ? 0 : val1.hashCode());
        hash = 31 * hash + (val2 == null ? 0 : val2.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Obj obj = (Obj) o;
        return id == obj.id 
          && (val1.equals(obj.val1) 
          && val2.equals(obj.val2));
    }
}
