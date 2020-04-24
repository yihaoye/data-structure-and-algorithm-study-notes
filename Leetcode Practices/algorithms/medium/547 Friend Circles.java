/*
There are N students in a class. Some of them are friends, while some are not. Their friendship is transitive in nature. For example, if A is a direct friend of B, and B is a direct friend of C, then A is an indirect friend of C. And we defined a friend circle is a group of students who are direct or indirect friends.

Given a N*N matrix M representing the friend relationship between students in the class. If M[i][j] = 1, then the ith and jth students are direct friends with each other, otherwise not. And you have to output the total number of friend circles among all the students.

Example 1:
Input: 
[[1,1,0],
 [1,1,0],
 [0,0,1]]
Output: 2
Explanation:The 0th and 1st students are direct friends, so they are in a friend circle. 
The 2nd student himself is in a friend circle. So return 2.
Example 2:
Input: 
[[1,1,0],
 [1,1,1],
 [0,1,1]]
Output: 1
Explanation:The 0th and 1st students are direct friends, the 1st and 2nd students are direct friends, 
so the 0th and 2nd students are indirect friends. All of them are in the same friend circle, so return 1.
Note:
N is in range [1,200].
M[i][i] = 1 for all students.
If M[i][j] = 1, then M[j][i] = 1.
*/



// My Solution (Union Find):
class Solution {
    public int findCircleNum(int[][] M) {
        UnionFindSet ufs = new UnionFindSet(M.length);
        for (int i=0; i<M.length; i++) {
            for (int j=0; j<M.length; j++) {
                if (M[i][j] == 1) {
                    ufs.Union(i, j);
                }
            }
        }
        
        return ufs.count;
    }
}

class UnionFindSet {
    private int[] parents_;
    private int[] ranks_;
    public int count;
 
    public UnionFindSet(int n) {
        parents_ = new int[n + 1];
        ranks_ = new int[n + 1];
        count = n;
        for (int i = 0; i < parents_.length; ++i) {
            parents_[i] = i;
            ranks_[i] = 1;
        }
    }
 
    public boolean Union(int u, int v) {
        int pu = Find(u);
        int pv = Find(v);
        if (pu == pv) return false;
        count--;

        if (ranks_[pv] > ranks_[pu])
            parents_[pu] = pv;           
        else if (ranks_[pu] > ranks_[pv])
            parents_[pv] = pu;
        else {
            parents_[pv] = pu;
            ranks_[pu] += 1;
        }

        return true;
    }
 
    public int Find(int u) {
        while (parents_[u] != u) {
            parents_[u] = parents_[parents_[u]];
            u = parents_[u];
        }
        return u;
    }
}