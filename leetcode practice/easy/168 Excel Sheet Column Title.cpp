//Question:
/*
Given a positive integer, return its corresponding column title as appear in an Excel sheet.

For example:

    1 -> A
    2 -> B
    3 -> C
    ...
    26 -> Z
    27 -> AA
    28 -> AB 
*/



//Other's Solution: 
class Solution {
public:
    string convertToTitle(int n) {
        string str ="";
        while(n>0)
        {
            char letter = 'A'+(n-1)%26;
            str = letter+str;
            n = (n-1)/26;	// do not understand why n-1?
        }
        return str;
    }
};