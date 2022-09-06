/**
There is a new alien language that uses the English alphabet. However, the order among the letters is unknown to you.

You are given a list of strings words from the alien language's dictionary, where the strings in words are sorted lexicographically by the rules of this new language.

Return a string of the unique letters in the new alien language sorted in lexicographically increasing order by the new language's rules. If there is no solution, return "". If there are multiple solutions, return any of them.

A string s is lexicographically smaller than a string t if at the first letter where they differ, the letter in s comes before the letter in t in the alien language. If the first min(s.length, t.length) letters are the same, then s is smaller if and only if s.length < t.length.

 

Example 1:

Input: words = ["wrt","wrf","er","ett","rftt"]
Output: "wertf"
Example 2:

Input: words = ["z","x"]
Output: "zx"
Example 3:

Input: words = ["z","x","z"]
Output: ""
Explanation: The order is invalid, so return "".
 

Constraints:

1 <= words.length <= 100
1 <= words[i].length <= 100
words[i] consists of only lowercase English letters.
 */



// My Solution (inspired by https://www.youtube.com/watch?v=B5hxqxBL2d0&list=PLbaIOC0vpjNVRXM5J4Y1jrZwhoDTyMNXU&index=4):
class Solution {
    Map<Integer, Set<Integer>> indegree = new HashMap<>(); // <itemA, <pre-requestA-of-itemA, pre-requestB-of-itemA, ...>>，这里 indegree 可以改为一个一维整数数组，元素里记录的是 itemA 的 pre-request 个数，然后在 topoSort 里通过 BFS 对元素--，若减至 0 就可以放入 BFS 队列中，如此性能会更好一点且逻辑更简易（但是 indegree 没有记录依赖项具体是哪些），具体实现也可参考本代码示例被注释的逻辑或 LC Q210 My Solution 3
	Map<Integer, Set<Integer>> outdegree = new HashMap<>(); // <pre-requestA, <itemA-relys-pre-requestA, itemB-relys-pre-requestA, ...>>
    boolean findInvalid = false;
    
    public String alienOrder(String[] words) {
		// 拓扑排序
        for (String word : words) {
            for (char c : word.toCharArray()) {
                indegree.computeIfAbsent(c-'a', x -> new HashSet<>());
                outdegree.computeIfAbsent(c-'a', x -> new HashSet<>());
            }
        }
        List<int[]> graph = buildGraph(words);
        if (findInvalid) return "";
		init(graph);
		List<Integer> sortedList = topoSort();
        StringBuilder strB = new StringBuilder();
        for (int charIdx : sortedList) strB.append((char) ('a' + charIdx));
        return strB.toString();
    }
    
    private List<int[]> buildGraph(String[] words) {
        List<int[]> graph = new ArrayList<>();
        for (int i=0; i<words.length-1; i++) {
            String str1 = words[i];
            String str2 = words[i+1];
            for (int j=0; j<Math.min(str1.length(), str2.length()); j++) {
                if (str1.charAt(j) != str2.charAt(j)) {
                    graph.add(new int[]{str1.charAt(j)-'a', str2.charAt(j)-'a'});
                    break;
                } else if (j == Math.min(str1.length(), str2.length())-1 && str1.length() > str2.length()) { // 特殊处理情况如 ["abc","ab"] -> Expected "" 因为无论如何 ab 理应在 abc 前
                    findInvalid = true;
                }
            }
        }
        return graph;
    }

	private void init(List<int[]> graph) { // [[i, j], ...] means j depends on i -> i is pre-request of j, 且假设不同的 item/node 数字不重复且 >= 0（后面在找不到无依赖项的 item 时会返回 -1）
		for (int[] edge : graph) {
			indegree.get(edge[1]).add(edge[0]);
			outdegree.get(edge[0]).add(edge[1]);
		}
	}

	private int findAvailableNode() { // available node means node has no pre-request
		for (Map.Entry<Integer, Set<Integer>> entry : indegree.entrySet()) {
			if (entry.getValue().size() == 0) return entry.getKey();
		}
		return -1;
	}

	private boolean removeNode(int node) { // normally this is used for remove pre-request
		if (!outdegree.containsKey(node)) return false;
		Set<Integer> relyers = outdegree.get(node);
		for (int relyer : relyers) {
			indegree.get(relyer).remove(node);
		}
		outdegree.remove(node);
		indegree.remove(node);
		return true;
	}

	private List<Integer> topoSort() {
		List<Integer> sortedList = new ArrayList<>();
		while (indegree.size() > 0) {
			int nextAvailable = findAvailableNode();
			if (nextAvailable == -1) return new ArrayList<>(); // Graph has at least one cycle. Topological sorting is not possible
			sortedList.add(nextAvailable);
			removeNode(nextAvailable);
		}

		return sortedList;
	}
}
