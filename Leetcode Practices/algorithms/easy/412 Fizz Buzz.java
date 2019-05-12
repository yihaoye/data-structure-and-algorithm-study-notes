/*
Write a program that outputs the string representation of numbers from 1 to n.

But for multiples of three it should output “Fizz” instead of the number and for the multiples of five output “Buzz”. For numbers which are multiples of both three and five output “FizzBuzz”.

Example:

n = 15,

Return:
[
    "1",
    "2",
    "Fizz",
    "4",
    "Buzz",
    "Fizz",
    "7",
    "8",
    "Fizz",
    "Buzz",
    "11",
    "Fizz",
    "13",
    "14",
    "FizzBuzz"
]
*/



// My Solution:
class Solution {
    public List<String> fizzBuzz(int n) {
        List<String> res = new ArrayList<String>();
        int i = 0;
        while (i+1 <= n) {
            i++;
            if (i%15 == 0) {
                res.add("FizzBuzz");
                continue;
            }
            if (i%5 == 0) {
                res.add("Buzz");
                continue;
            }
            if (i%3 == 0) {
                res.add("Fizz");
                continue;
            }
            res.add(Integer.toString(i));
        }
        
        return res;
    }
}