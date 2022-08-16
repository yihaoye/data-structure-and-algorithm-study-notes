//Question:
/*
Given a roman numeral, convert it to an integer.

Input is guaranteed to be within the range from 1 to 3999.
*/



//Other's Solution: (using hashmap)
public class Solution {
    public int romanToInt(String s) {
        //：Ⅰ（1）Ⅴ（5）Ⅹ（10）L（50）C（100）D（500）M（1000） 
        // rules:位于大数的后面时就作为加数；位于大数的前面就作为减数
        //eg：Ⅲ=3,Ⅳ=4,Ⅵ=6,XIX=19,XX=20,XLV=45, MCMLXXX=1980
        //"DCXXI"

        if(s == null || s.length() == 0) return 0;
        int len = s.length();
        HashMap<Character,Integer> map = new HashMap<Character,Integer>();
        map.put('I',1);
        map.put('V',5);
        map.put('X',10);
        map.put('L',50);
        map.put('C',100);
        map.put('D',500);
        map.put('M',1000);
        int result = map.get(s.charAt(len -1));
        int pivot = result;
        for(int i = len -2; i>= 0;i--){
            int curr = map.get(s.charAt(i));
            if(curr >=  pivot){
                result += curr;
            }else{
                result -= curr;
            }
            pivot = curr;
        }
        return result;
    }
}
/* string.charAt[0] indicate first left char
String text = "foo";
char a_char = text.charAt(0);
System.out.println( a_char ); // Prints f
*/



// Other's Solution:
class Solution {
    public int romanToInt(String s) {
        int nums[] = new int[s.length()];
        for (int i=0; i<s.length(); i++) {
            switch (s.charAt(i)) {
                case 'M':
                    nums[i] = 1000;
                    break;
                case 'D':
                    nums[i] = 500;
                    break;
                case 'C':
                    nums[i] = 100;
                    break;
                case 'L':
                    nums[i] = 50;
                    break;
                case 'X' :
                    nums[i] = 10;
                    break;
                case 'V':
                    nums[i] = 5;
                    break;
                case 'I':
                    nums[i] = 1;
                    break;
            }
        }
        int sum = 0;

        for (int i=0; i<nums.length-1; i++) {
            if (nums[i] < nums[i+1]) sum -= nums[i];
            else sum += nums[i];
        }

        return sum + nums[nums.length-1];
    }
}
