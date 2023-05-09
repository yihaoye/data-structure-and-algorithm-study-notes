/*
Generic methods are a very efficient way to handle multiple datatypes using a single method. This problem will test your knowledge on Java Generic methods.

Let's say you have an integer array and a string array. You have to write a single method printArray that can print all the elements of both arrays. The method should be able to accept both integer arrays or string arrays.

You are given code in the editor. Complete the code so that it prints the following lines:

1
2
3
Hello
World
Do not use method overloading because your answer will not be accepted.
*/



import java.io.IOException;
import java.lang.reflect.Method;

class Printer {
    /**
     泛型中通配符
        在定义泛型类，泛型方法，泛型接口的时候经常会碰见很多不同的通配符，比如 T，E，K，V 等等，这些通配符又都是什么意思呢？
        常用的 T，E，K，V，？
        本质上这些个都是通配符，没啥区别，只不过是编码时的一种约定俗成的东西。比如上述代码中的 T ，可以换成 A-Z 之间的任何一个字母都可以，
        并不会影响程序的正常运行，但是如果换成其他的字母代替 T，在可读性上可能会弱一些。通常情况下，T，E，K，V，？ 是这样约定的：

        ？                  表示不确定的 java 类型
        T                   (type) 表示具体的一个java类型
        K V                 (key value) 分别代表java键值中的Key Value
        E                   (element) 代表Element
     */
   public <T> void printArray(T[] array) { // 方法返回值前的<T>的左右是告诉编译器，当前的方法的值传入类型可以和类初始化的泛型类不同，也就是该方法的泛型类可以自定义，不需要跟类初始化的泛型类相同
       for (T item: array) {
            System.out.println(item);
       }
   }
}

public class Solution {

    public static void main( String args[] ) {
        Printer myPrinter = new Printer();
        Integer[] intArray = { 1, 2, 3 };
        String[] stringArray = {"Hello", "World"};
        myPrinter.printArray(intArray);
        myPrinter.printArray(stringArray);
        int count = 0;

        for (Method method : Printer.class.getDeclaredMethods()) {
            String name = method.getName();

            if(name.equals("printArray"))
                count++;
        }

        if(count > 1)System.out.println("Method overloading is not allowed!");
      
    }
}
// ![](./泛型.png)