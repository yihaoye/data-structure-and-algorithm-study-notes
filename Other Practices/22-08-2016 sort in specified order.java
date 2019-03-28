//Question:
/*
given a integer "n" (for example: 100)
Output list<Integer>: [1,10,11,12,13,14,15,16,17,18,19,100,2,20,21,22,23,24,25,26,27,28,29,3,30,31,32,33,34,35,36,37,38,39,4,40,41,42,43,44...]
*/



//My Answer:
public class Solution {
    public List<Integer> lexicalOrder(int n) {
        int count=0;
        ArrayList<Integer> result = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> Lists = new ArrayList<ArrayList<Integer>>();
        for(int i=0; i<9; i++){
            Lists.add(new ArrayList<Integer>());
        }
        while(count<n){
            count++;
            Lists.get(get_top(count)-1).add(count);
        }
        for(ArrayList<Integer> temp : Lists){
            result.addAll(temp);
        }
        return result;
    }
    
    public int get_top(int number){
        while(number>=10){
            number = number / 10;
        }
        return number;
    }
}