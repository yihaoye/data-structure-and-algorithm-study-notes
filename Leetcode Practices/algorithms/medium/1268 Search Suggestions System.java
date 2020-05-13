/*
Given an array of strings products and a string searchWord. We want to design a system that suggests at most three product names from products after each character of searchWord is typed. Suggested products should have common prefix with the searchWord. If there are more than three products with a common prefix return the three lexicographically minimums products.

Return list of lists of the suggested products after each character of searchWord is typed. 

 

Example 1:

Input: products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
Output: [
["mobile","moneypot","monitor"],
["mobile","moneypot","monitor"],
["mouse","mousepad"],
["mouse","mousepad"],
["mouse","mousepad"]
]
Explanation: products sorted lexicographically = ["mobile","moneypot","monitor","mouse","mousepad"]
After typing m and mo all products match and we show user ["mobile","moneypot","monitor"]
After typing mou, mous and mouse the system suggests ["mouse","mousepad"]
Example 2:

Input: products = ["havana"], searchWord = "havana"
Output: [["havana"],["havana"],["havana"],["havana"],["havana"],["havana"]]
Example 3:

Input: products = ["bags","baggage","banner","box","cloths"], searchWord = "bags"
Output: [["baggage","bags","banner"],["baggage","bags","banner"],["baggage","bags"],["bags"]]
Example 4:

Input: products = ["havana"], searchWord = "tatiana"
Output: [[],[],[],[],[],[],[]]
 

Constraints:

1 <= products.length <= 1000
There are no repeated elements in products.
1 <= Σ products[i].length <= 2 * 10^4
All characters of products[i] are lower-case English letters.
1 <= searchWord.length <= 1000
All characters of searchWord are lower-case English letters.
*/



// My Solution:
class Solution {
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);
        ArrayList<List<String>> res = new ArrayList<List<String>>();
        int[] startIndex = new int[]{0}; // reusable start index flag to improve performance, not must
        for (int i=1; i<=searchWord.length(); i++) {
            res.add(matchProducts(products, searchWord.substring(0, i), startIndex));
        }
        
        return res;
    }
    
    public List<String> matchProducts(String[] products, String word, int[] index) {
        ArrayList<String> res = new ArrayList<String>();
        int count = 0;
        for (int i=index[0]; i<products.length; i++) { // can apply binary-search first time to improve performance?
            if (products[i].indexOf(word) == 0) {
                res.add(products[i]);
                if (count == 0) index[0] = i;
                if (++count == 3) break;
            }
        }
        
        return res;
    }
}