//Question:
/*
Write a function that takes an unsigned integer and returns the number of ’1' bits it has (also known 
as the Hamming weight).

For example, the 32-bit integer ’11' has binary representation 00000000000000000000000000001011, 
so the function should return 3.
*/



//My Solution: (perform well)
class Solution {
public:
    int hammingWeight(uint32_t n) {
        int comp_num = 1;
        int count=0;
        
        for(int i=0; i<32; i++)
        {
            if(comp_num & n)
            {
                count++;
            }
            comp_num = comp_num << 1;
        }
        
        return count;
    }
};