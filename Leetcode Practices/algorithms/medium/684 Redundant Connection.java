/*
In this problem, a tree is an undirected graph that is connected and has no cycles.

The given input is a graph that started as a tree with N nodes (with distinct values 1, 2, ..., N), with one additional edge added. The added edge has two different vertices chosen from 1 to N, and was not an edge that already existed.

The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] with u < v, that represents an undirected edge connecting nodes u and v.

Return an edge that can be removed so that the resulting graph is a tree of N nodes. If there are multiple answers, return the answer that occurs last in the given 2D-array. The answer edge [u, v] should be in the same format, with u < v.

Example 1:
Input: [[1,2], [1,3], [2,3]]
Output: [2,3]
Explanation: The given undirected graph will be like this:
  1
 / \
2 - 3
Example 2:
Input: [[1,2], [2,3], [3,4], [1,4], [1,5]]
Output: [1,4]
Explanation: The given undirected graph will be like this:
5 - 1 - 2
    |   |
    4 - 3
Note:
The size of the input 2D-array will be between 3 and 1000.
Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.
*/



// Other's Solution (Disjoint-Set & Union Find Algorithm):
class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        UnionFindSet ufs = new UnionFindSet(edges.length);
        
        for (int[] edge : edges) {
            if (!ufs.Union(edge[0], edge[1])) return edge; // 经测试，使用 ufs.Union 并不强制 edge[0] 一定大于或小于 edge[1]
        }
        
        return new int[1];
    }
}

class UnionFindSet {
  private int[] parents_;
  private int[] ranks_; // 这里 ranks_[x] 并不实时准确表示该节点所属树的深度（因为发生 path compression 时并不对 ranks_[i] 自减，应该可以针对此进行优化使其准确反应深度并提高效率但是实现此优化可能很复杂），但这不影响题解的正确性，只是可能在某些输入情况中性能有些影响。
 
  public UnionFindSet(int n) {
      parents_ = new int[n + 1];
      ranks_ = new int[n + 1];
      for (int i = 0; i < parents_.length; ++i) {
          parents_[i] = i;
          ranks_[i] = 1;
      }
  }
 
  public boolean Union(int u, int v) {
      int pu = Find(u);
      int pv = Find(v);
      if (pu == pv) return false;
 
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
 
  public int Find(int u) { // 一次 Find 调用不会将该节点所属树的全部节点都 path compression，甚至可能本次不会将输入节点的 parents_[x] 完成赋值为根父节点（可能只赋值为中途的某个节点）。
      while (parents_[u] != u) {
          parents_[u] = parents_[parents_[u]];
          u = parents_[u];
      }
      return u;
  }
}
// https://zxi.mytechroad.com/blog/tree/leetcode-684-redundant-connection/
/*
个人理解补充：
if (pu == pv) return false;
如上行代码，比较根节点 (Find(u)) 是否相等只是用来判定是否连通，
至于谁做根节点没关系、都是一样的，只要是该片连通的所有节点中的其中一个即可（该片连通应是树状网络的形式），
当然从效率上来说，中心的那个节点（即被直连最多即深度最深的那个节点）最好。
*/
