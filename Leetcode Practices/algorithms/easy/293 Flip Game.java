/*
You are playing the following Flip Game with your friend: Given a string that contains only these two characters: + and -, you and your friend take turns to flip two consecutive "++" into "--". The game ends when a person can no longer make a move and therefore the other person will be the winner.

Write a function to compute all possible states of the string after one valid move.

Example:

Input: s = "++++"
Output: 
[
  "--++",
  "+--+",
  "++--"
]
Note: If there is no valid move, return an empty list [].
*/



// My Solution:
class Solution {
    public List<String> generatePossibleNextMoves(String s) {
        List<String> res = new ArrayList<String>();
        if (s.length() < 2) return res;
        char[] cArray = s.toCharArray();
        for (int i=0; i<cArray.length-1; i++) {
            if (cArray[i] == '+' && cArray[i] == cArray[i+1]) {
                flip(cArray, i, i+1);
                res.add(new String(cArray));
                flip(cArray, i, i+1); // flip back
            }
        }
        
        return res;
    }
    
    public void flip(char[] cArray, int i, int j) {
        cArray[i] = cArray[i] == '+' ? '-' : '+';
        cArray[j] = cArray[i];
    }
}