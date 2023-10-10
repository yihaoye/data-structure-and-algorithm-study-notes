/*
There is a directed graph consisting of n nodes numbered from 0 to n - 1 and n directed edges.

You are given a 0-indexed array edges where edges[i] indicates that there is an edge from node i to node edges[i].

Consider the following process on the graph:

You start from a node x and keep visiting other nodes through edges until you reach a node that you have already visited before on this same process.
Return an array answer where answer[i] is the number of different nodes that you will visit if you perform the process starting from node i.

 

Example 1:
https://assets.leetcode.com/uploads/2023/08/31/graaphdrawio-1.png

Input: edges = [1,2,0,0]
Output: [3,3,3,4]
Explanation: We perform the process starting from each node in the following way:
- Starting from node 0, we visit the nodes 0 -> 1 -> 2 -> 0. The number of different nodes we visit is 3.
- Starting from node 1, we visit the nodes 1 -> 2 -> 0 -> 1. The number of different nodes we visit is 3.
- Starting from node 2, we visit the nodes 2 -> 0 -> 1 -> 2. The number of different nodes we visit is 3.
- Starting from node 3, we visit the nodes 3 -> 0 -> 1 -> 2 -> 0. The number of different nodes we visit is 4.

Example 2:
https://assets.leetcode.com/uploads/2023/08/31/graaph2drawio.png

Input: edges = [1,2,3,4,0]
Output: [5,5,5,5,5]
Explanation: Starting from any node we can visit every node in the graph in the process.
 

Constraints:

n == edges.length
2 <= n <= 10^5
0 <= edges[i] <= n - 1
edges[i] != i
*/



// My Solution: (after inspired)
class Solution {
    public int[] countVisitedNodes(List<Integer> edges) {
        // 图遍历 + 双端队列 - 每一个节点只有一个对外指向，但可能被多个节点指向
        // Time: O(N)
        int n = edges.size();
        int[] res = new int[n];
        for (int i=0; i<n; i++) {
            bfs(i, edges, res, new HashSet<>());
        }

        return res;
    }

    public void bfs(int node, List<Integer> edges, int[] res, Set<Integer> visited) {
        if (res[node] != 0) return;
        Deque<Integer> deque = new LinkedList<>();
        deque.offer(node); visited.add(node);
        while (true) {
            int next = edges.get(deque.peekLast());
            if (res[next] != 0) { // 只要找到第一个之前设置过的（必然不会在此之前有 next 重复，因为如果之前重复则已经会在下一个逻辑中处理完），就可以对现有队列里全部进行设置了
                while (!deque.isEmpty()) {
                    res[deque.poll()] = deque.size() + 1 + res[next]; // + 1 因为刚被 poll 出来，需要补上
                }
                return;
            } else if (visited.contains(next)) { // 只要找到第一个重复的，就可以对现有队列里全部进行设置了
                while (!deque.isEmpty()) {
                    int poll = deque.poll();
                    if (res[next] == 0) res[poll] = deque.size() + 1;
                    else res[poll] = res[next]; // 剩下的都是头尾环里，全部设置为 res[next] 即可
                }
                return;
            } else {
                deque.offer(next);
                visited.add(next);
            }
        }
    }
}
