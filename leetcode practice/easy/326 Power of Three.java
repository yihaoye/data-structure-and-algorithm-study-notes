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