/*
Given two sentences words1, words2 (each represented as an array of strings), and a list of similar word pairs pairs, determine if two sentences are similar.

For example, words1 = ["great", "acting", "skills"] and words2 = ["fine", "drama", "talent"] are similar, if the similar word pairs are pairs = [["great", "good"], ["fine", "good"], ["acting","drama"], ["skills","talent"]].

Note that the similarity relation is transitive. For example, if "great" and "good" are similar, and "fine" and "good" are similar, then "great" and "fine" are similar.

Similarity is also symmetric. For example, "great" and "fine" being similar is the same as "fine" and "great" being similar.

Also, a word is always similar with itself. For example, the sentences words1 = ["great"], words2 = ["great"], pairs = [] are similar, even though there are no specified similar word pairs.

Finally, sentences can only be similar if they have the same number of words. So a sentence like words1 = ["great"] can never be similar to words2 = ["doubleplus","good"].

Note:

The length of words1 and words2 will not exceed 1000.
The length of pairs will not exceed 2000.
The length of each pairs[i] will be 2.
The length of each words[i] and pairs[i][j] will be in the range [1, 20].
*/



// Other's Solution (Union Find):
class Solution {
    public boolean areSentencesSimilarTwo(String[] words1, String[] words2, List<List<String>> pairs) {
        if (words1.length != words2.length) return false;
        
        UnionFindSet ufs = new UnionFindSet(pairs.size() * 2); // 不一定全部用完，比如如果多个 pair 都有某个相同字符串时（如 ["great", "good"], ["fine", "good"] 里的 "good"），除非 pairs 里包含的所有字符串每个都是 unique 才会刚刚好用完
        
        Map<String, Integer> indies = new HashMap<>(); // word to index
        
        for (List<String> pair : pairs) {
            int u = getIndex(pair.get(0), indies, true);
            int v = getIndex(pair.get(1), indies, true);
            ufs.Union(u, v);
        }
        
        for (int i = 0; i < words1.length; ++i) {
            if (words1[i].equals(words2[i])) continue;
            int u = getIndex(words1[i], indies, false);
            int v = getIndex(words2[i], indies, false);
            if (u < 0 || v < 0) return false;
            if (ufs.Find(u) != ufs.Find(v)) return false;
        }
        
        return true;
    }
    
    private int getIndex(String word, Map<String, Integer> indies, Boolean create) {
        if (!indies.containsKey(word)) {
            if (!create) return Integer.MIN_VALUE;
            int index = indies.size(); // 这里 index = indies.size() 的意思是每插入一个新的元素就把其对应至其插入的顺序值（即 value = 它被遍历时的顺序，比如第一个被 getIndex 的则赋值 0，第二个被 getIndex 的则赋值 1，后面也如是）
            indies.put(word, index);
            return index;
        }
        
        return indies.get(word);
    }
}

class UnionFindSet {
  private int[] parents_;
  private int[] ranks_;
 
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
 
  public int Find(int u) {
      while (parents_[u] != u) {
          parents_[u] = parents_[parents_[u]];
          u = parents_[u];
      }
      return u;
  }
}
// https://zxi.mytechroad.com/blog/hashtable/leetcode-737-sentence-similarity-ii/