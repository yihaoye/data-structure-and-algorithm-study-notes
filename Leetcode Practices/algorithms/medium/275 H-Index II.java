/*
Given an array of citations sorted in ascending order (each citation is a non-negative integer) of a researcher, write a function to compute the researcher's h-index.

According to the definition of h-index on Wikipedia: "A scientist has index h if h of his/her N papers have at least h citations each, and the other N − h papers have no more than h citations each."

Example:

Input: citations = [0,1,3,5,6]
Output: 3 
Explanation: [0,1,3,5,6] means the researcher has 5 papers in total and each of them had 
             received 0, 1, 3, 5, 6 citations respectively. 
             Since the researcher has 3 papers with at least 3 citations each and the remaining 
             two with no more than 3 citations each, her h-index is 3.
Note:

If there are several possible values for h, the maximum one is taken as the h-index.

Follow up:

This is a follow up problem to H-Index, where citations is now guaranteed to be sorted in ascending order.
Could you solve it in logarithmic time complexity?
*/



// My Solution:
class Solution {
    public int hIndex(int[] citations) {
        int len = citations.length;
        if (len == 0) return 0;
        if (citations[0] >= len) return len;
        if (citations[len-1] == 0) return 0;
        
        // Binary Search
        return binarySearch(citations, 0, len-1);
    }
    
    public int binarySearch(int[] citations, int start, int end) {
        int mid = 0, len = end+1;
        while (start < end) {
            mid = start + (end - start) / 2;
            if (citations[mid] > len-mid) end = mid;
            else start = mid + 1;
        }
        if (citations[start-1] == len-start+1) start--;
        return len-start;
    }
}