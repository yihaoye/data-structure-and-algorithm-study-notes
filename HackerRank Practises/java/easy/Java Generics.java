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

class Printer
{
   public <T> void printArray(T[] array) { // 方法返回值前的<T>的左右是告诉编译器，当前的方法的值传入类型可以和类初始化的泛型类不同，也就是该方法的泛型类可以自定义，不需要跟类初始化的泛型类相同
       for(T item: array){
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