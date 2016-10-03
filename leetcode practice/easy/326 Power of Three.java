//Question:
/*
Given an integer, write a function to determine if it is a power of three.

Follow up:
Could you do it without using any loop / recursion? 
*/





//My Solution:
public class Solution {
    public boolean isPowerOfThree(int n) {
        while(n>=3){
            if(n==3){
                return true;
            }else if(n%3==0){
                n=n/3;
            }else{
                return false;
            }
        }
        
        if(n==1){
            return true;
        }
        return false;
    }
}

/*	recursion
public class Solution {
    public boolean isPowerOfThree(int n) {
        if(n==3 || n==1){
            return true;
        }else if(n%3==0){
            isPowerOfThree(n/3);
        }else{
            return false;
        }
        
        return false;
    }
}
*/



//Other's funny solution:
/*
Base Conversion:

In Base 10, all powers of 10 start with the digit 1 and then are followed only by 0 (e.g. 10, 100, 1000). 
This is true for other bases and their respective powers. For instance in base 2, the representations of 10base2, 
100base2​​ and 1000base2​​ are 2base10​​, 4base10​​ and 8base10​​ respectively. 
Therefore if we convert our number to base 3 and the representation is of the form 100...0, then the number is a power of 3.

Proof
Given the base 3 representation of a number as the array s, with the least significant digit on index 0, 
the formula for converting from base 3 to base 10 is:
    ∑(s[i]*3^i)     ("i" is from "0" to "len(s)-1")
Therefore, having just one digit of 1 and everything else 0 means the number is a power of 3.

Implementation
All we need to do is convert the number to base 3 and check if it is written as a leading 1 followed by all 0.
A couple of built-in Java functions will help us along the way.
*/    String baseChange = Integer.toString(number, base);/*
The code above converts number into base base and returns the result as a String. 
For example, Integer.toString(5, 2) == "101" and Integer.toString(5, 3) == "12".
*/    boolean matches = myString.matches("123");/*
The code above checks if a certain Regular Expression pattern exists inside a string. 
For instance the above will return true if the substring "123" exists inside the string myString.
*/    boolean powerOfThree = baseChange.matches("^10*$")/*
We will use the regular expression above for checking if the string starts with 1 "^1", is followed by zero or more 0s "0*" 
and contains nothing else "$".
*/

//Java:
public class Solution {
    public boolean isPowerOfThree(int n) {
        return Integer.toString(n, 3).matches("^10*$");
    }
}

/*
Complexity Analysis

    Time complexity : O(log3n).

    Assumptions:
        Integer.toString() - Base conversion is generally implemented as a repeated division. 
        The complexity of should be similar to our approach #1: O(log3n).
        String.matches() - Method iterates over the entire string. 
        The number of digits in the base 3 representation of n is O(log3n).

    Space complexity : O(log3n).

    We are using two additional variables,
        The string of the base 3 representation of the number (size log3n)
        The string of the regular expression (constant size)
*/
