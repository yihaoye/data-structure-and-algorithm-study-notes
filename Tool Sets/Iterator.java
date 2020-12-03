import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
  public static void main(String[] args) {
    // Make a collection
    ArrayList<String> strs = new ArrayList<String>(Arrays.asList("str1", "str2", "str3"));
    // Get the iterator
    Iterator<String> it1 = strs.iterator();
    // Looping Through a Collection
    while (it1.hasNext()) System.out.println(it1.next());

    ArrayList<Integer> numbers = new ArrayList<Integer>(Arrays.asList(12, 8, 2, 23));
    Iterator<Integer> it2 = numbers.iterator();
    while (it2.hasNext()) {
        Integer i = it2.next();
        if (i < 10) it2.remove();
    }
    System.out.println(numbers);
  }
}
