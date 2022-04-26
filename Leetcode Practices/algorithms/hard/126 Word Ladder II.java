/**
A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:

Every adjacent pair of words differs by a single letter.
Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
sk == endWord
Given two words, beginWord and endWord, and a dictionary wordList, return all the shortest transformation sequences from beginWord to endWord, or an empty list if no such sequence exists. Each sequence should be returned as a list of the words [beginWord, s1, s2, ..., sk].

 

Example 1:

Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
Output: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
Explanation: There are 2 shortest transformation sequences:
"hit" -> "hot" -> "dot" -> "dog" -> "cog"
"hit" -> "hot" -> "lot" -> "log" -> "cog"
Example 2:

Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
Output: []
Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
 

Constraints:

1 <= beginWord.length <= 5
endWord.length == beginWord.length
1 <= wordList.length <= 1000
wordList[i].length == beginWord.length
beginWord, endWord, and wordList[i] consist of lowercase English letters.
beginWord != endWord
All the words in wordList are unique.
 */



// Other's Solution:
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        // BFS 
        // - https://leetcode-cn.com/problems/word-ladder-ii/solution/yan-du-you-xian-bian-li-shuang-xiang-yan-du-you--2/
        // - https://leetcode-cn.com/problems/word-ladder-ii/solution/dan-ci-jie-long-ii-by-leetcode-solution/
        // 一道非常值得学习的 hard 题，因为它有以下几大知识点：如何将一个问题抽象成图的问题？如何建图？如何解决无向图里的最短路径问题？- https://leetcode-cn.com/problems/word-ladder-ii/solution/yi-dao-fei-chang-zhi-de-xue-xi-de-hardti-w58i/
        List<List<String>> res = new ArrayList<>();
        Set<String> wordSet = new HashSet<>(wordList); // 因为需要快速判断扩展出的单词是否在 wordList 里，因此需要将 wordList 存入哈希表，这里命名为 wordSet
        if (!wordSet.contains(endWord)) return res; // 特殊用例判断
        wordSet.remove(beginWord);

        // 第 1 步：广度优先遍历建图
        Map<String, Integer> steps = new HashMap<>(); // 记录扩展出的单词是在第几次扩展的时候得到的，key：单词，value：在广度优先遍历的第几层
        steps.put(beginWord, 0);

        Map<String, List<String>> from = new HashMap<>(); // 记录了单词是从哪些单词扩展而来，key：单词，value：单词列表，这些单词可以变换到 key，它们是一对多关系
        int step = 1;
        boolean found = false;
        int wordLen = beginWord.length();
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String currWord = queue.poll();
                char[] charArray = currWord.toCharArray();
                for (int j = 0; j < wordLen; j++) { // 将每一位替换成 26 个小写英文字母
                    char origin = charArray[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        charArray[j] = c;
                        String nextWord = String.valueOf(charArray);
                        // 注意以下这几行代码的逻辑先后顺序
                        if (steps.containsKey(nextWord) && step == steps.get(nextWord)) { // 这一步不是把虚线加上，根据链接 1 的最后一张图，把这一行代码带入到最后一步，也就是 temp = 3 时往 temp = 4 时扩展；temp = 3 时有两个元素，分别是 dog、log；当 dog 进行寻找 nextWord 时找到 cog 会进行 steps.put(nextWord, step) 创建出 temp = 4 这一层，这一层包涵一个元素，但是这时并没有结束，因为 queue 不为空，log 会是下一个 currWord，这个时候执行这一行代码相当于把 cog 和 log 连接起来，如果少了这一行代码，最后进行 dfs 的结果会少一个，因为 cog 没有和 log 连接起来，图不完整。当这一行执行时，nextWord 肯定在之前探索过，所以已经从 wordSet 中移除掉了，虽然 steps 和 found 不需要更新（若之前更新过，此次更新的结果也必然一样所以没必要更新），但是 from 更新被跳过了，所以要在 continue 前的这里补上。而且 steps 如果记录过 nextWord，即第一次记录，必然是最近层，所以之后再遭遇 nextWord 时除非是同一层，否则都不予记录（因为必然更远而答案只要最近值，且不存在远的可以达到目标而近的不能达到目标）
                            from.get(nextWord).add(currWord);
                        }
                        if (!wordSet.contains(nextWord)) {
                            continue;
                        }
                        wordSet.remove(nextWord); // 如果从一个单词扩展出来的单词以前遍历过，距离一定更远，为了避免搜索到已经遍历到，且距离更远的单词，需要将它从 wordSet 中删除
                        queue.offer(nextWord); // 这一层扩展出的单词进入队列，wordSet 和 steps 承担了已经访问的功能

                        // 以下维护 from、steps、found 的定义
                        from.putIfAbsent(nextWord, new ArrayList<>()); // 记录 nextWord 从 currWord 而来
                        from.get(nextWord).add(currWord); // 同上
                        steps.put(nextWord, step); // 记录 nextWord 的 step
                        if (nextWord.equals(endWord)) found = true; // 注意：由于有多条路径到达 endWord 找到以后不能立即退出，只需要设置 found = true 即可
                    }
                    charArray[j] = origin; // 把 charArray 复原，因为下一次换一位字母遍历
                }
            }
            step++;
            if (found) break;
        }

        // 第 2 步：深度优先遍历找到所有解，从 endWord 恢复到 beginWord ，所以每次尝试操作 path 列表的头部
        if (found) {
            Deque<String> path = new ArrayDeque<>();
            path.add(endWord);
            dfs(from, path, beginWord, endWord, res);
        }
        return res;
    }

    public void dfs(Map<String, List<String>> from, Deque<String> path, String beginWord, String cur, List<List<String>> res) {
        if (cur.equals(beginWord)) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (String precursor : from.get(cur)) {
            path.addFirst(precursor);
            dfs(from, path, beginWord, precursor, res);
            path.removeFirst();
        }
    }
}
