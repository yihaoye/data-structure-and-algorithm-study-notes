//Question:
/*
Related to question Excel Sheet Column Title

Given a column title as appear in an Excel sheet, return its corresponding column number.

For example:

    A -> 1
    B -> 2
    C -> 3
    ...
    Z -> 26
    AA -> 27
    AB -> 28 
*/



//My Solution: (with a good performance)
class Solution {
public:
    int titleToNumber(string s) {
        int sum=0;
        
        for(int i=0; i<s.size(); i++)
        {
            sum+=(s[i]-'A'+1)*pow(26,s.size()-i-1);
        }
        
        return sum;
    }
};