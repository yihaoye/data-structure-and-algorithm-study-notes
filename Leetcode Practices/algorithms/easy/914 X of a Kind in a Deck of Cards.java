/*
In a deck of cards, each card has an integer written on it.

Return true if and only if you can choose X >= 2 such that it is possible to split the entire deck into 1 or more groups of cards, where:

Each group has exactly X cards.
All the cards in each group have the same integer.
 

Example 1:

Input: deck = [1,2,3,4,4,3,2,1]
Output: true
Explanation: Possible partition [1,1],[2,2],[3,3],[4,4].
Example 2:

Input: deck = [1,1,1,2,2,2,3,3]
Output: false´
Explanation: No possible partition.
Example 3:

Input: deck = [1]
Output: false
Explanation: No possible partition.
Example 4:

Input: deck = [1,1]
Output: true
Explanation: Possible partition [1,1].
Example 5:

Input: deck = [1,1,2,2,2,2]
Output: true
Explanation: Possible partition [1,1],[2,2],[2,2].
 

Constraints:

1 <= deck.length <= 10^4
0 <= deck[i] < 10^4
*/



// My Solution:
class Solution {
    public boolean hasGroupsSizeX(int[] deck) {
        Arrays.sort(deck);
        List<Integer> counts = new ArrayList<Integer>();
        int count = 1;
        for (int i=1; i<deck.length; i++) {
            if (deck[i] == deck[i-1]) {
                count++;
            } else {
                counts.add(count);
                count = 1;
            }
        }
        counts.add(count);
        int partition = getGcd(counts);
        if (partition < 2) return false;
        
        return true;
    }
    
    public int getGcd(List<Integer> counts) { // get Greatest Common Divisor
        Collections.sort(counts);
        int maxGcd = counts.get(counts.size() - 1);
        for (int i=2,j=0; i<=maxGcd; i++) {
            for (Integer count : counts) {
                if (count%i == 0) j++;
            }
            if (j == counts.size())  return i;
            j = 0;
        }
        return 1;
    }
}