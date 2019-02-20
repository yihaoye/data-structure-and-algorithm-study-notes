//Question:
/*
Given a roman numeral, convert it to an integer.

Input is guaranteed to be within the range from 1 to 3999.
*/



//Other's Solution:
//I（1）、V（5）、X（10）、L（50）、C（100）、D（500）、M（1000）
class Solution {
public:
    int romanToInt(string s) {
        int num[256] = { 0 };
        int result = 0;
        num['I'] = 1; num['V'] = 5; num['X'] = 10; num['L']=50;
        num['C'] = 100; num['D'] = 500; num['M'] = 1000;
        int i = 0;
        while (i < s.size()){
            if (num[s[i]] < num[s[i+1]])	// nums[0] means first left char at string
                result -= num[s[i]];
            else
                result += num[s[i]];
            i++;
        }
        return result;
    }
};
