//Question:
/*
Given an array of integers, every element appears twice except for one. Find that single one.

Note:
Your algorithm should have a linear runtime complexity. 
Could you implement it without using extra memory? 
*/



//Other's Solution:
class Solution {
public:
    int singleNumber(vector<int>& nums) {
        // XOR (^) is both commutative and associative 
        // The numbers which appear twice will be cancelled
        // Only the number that appear twice survive 
        int value = 0;
        int i, n;
        n = nums.size();
        for(i=0; i<n; i++)
            value = value ^ nums[i]; // "^" means XOR
        return value;
    }
};

//Explain:
/*
First, single number is a classic interview question in many famouse company.

Then, we use XOR here, it means when two bits have same value(1/0), XOR result will be 0, 
when two bits have different value, XOR result will be 1.
for example, 2^4=6, since (0010 XOR 0100 = 0110).

Also, when the number XOR itself, result would be 0,
futher more, it does not matter with the different order for the same group numbers to carry out XOR,
for example:
		0110					0111
		1100					1000
		0001					1100
		1000					0110
		0111					0001
		  XOR					  XOR
	---------				---------
		0100					0100
*/