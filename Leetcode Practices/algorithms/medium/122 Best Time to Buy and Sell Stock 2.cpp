//Question:
/*
Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete as many transactions as you 
like (ie, buy one and sell one share of the stock multiple times). 
However, you may not engage in multiple transactions at the same time (ie, you must sell the stock 
before you buy again).
*/



//My Solution:
class Solution {
public:
    int maxProfit(vector<int>& prices) {
        int length = prices.size();
        if(length==1)
            return 0;
            
        int max_profit = 0;
        for(int i=0; i<length; i++)
        {
            if(i<=length-2)
            {
                if(prices[i]<=prices[i+1])
                {
                    max_profit -= prices[i];
                    max_profit += prices[i+1];
                }
            }
        }
        return max_profit;
    }
};