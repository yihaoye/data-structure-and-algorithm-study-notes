//Question:
/*
Given an integer n, return 1 - n in lexicographical order.

For example, given 13, return: [1,10,11,12,13,2,3,4,5,6,7,8,9].

Please optimize your algorithm to use less time and space. The input size may be as large as 5,000,000. 
*/





//Other's Answer: (iterative solution)
public class Solution {
    public List<Integer> lexicalOrder(int n) {
        List<Integer> list = new ArrayList<>(n);
        int curr = 1;
        for (int i = 1; i <= n; i++) {
            list.add(curr);
            if (curr * 10 <= n) {
                curr *= 10;
            } else if (curr % 10 != 9 && curr + 1 <= n) {
                curr++;
            } else {
                while ((curr / 10) % 10 == 9) {
                    curr /= 10;
                }
                curr = curr / 10 + 1;
            }
        }
        return list;
    }
}


//Other's Answer 2: (Recursive Java Solution using pre-order traversal)
public class Solution {
    public List<Integer> lexicalOrder(int n) {
        List<Integer> res = new ArrayList<>();
        lexicalOrderHelper(res, 1, n);
        return res;
    }

    private void lexicalOrderHelper(List<Integer> res, int i, int n) {
        if(i > n) return;
        res.add(i);
        lexicalOrderHelper(res, i * 10, n);
        if(i + 1 <= (i / 10) * 10 + 9) lexicalOrderHelper(res, i + 1, n);
        else return;
    }
}