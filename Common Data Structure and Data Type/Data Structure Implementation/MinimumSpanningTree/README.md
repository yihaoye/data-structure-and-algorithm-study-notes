
# 最小生成树
[什么是生成树](./../SpanningTree/README.md)  

最小生成树是一副连通加权无向图中一棵权值最小的生成树。  
在一给定的无向图 G = (V, E) 中，(u, v) 代表连接顶点 u 与顶点 v 的边，而 w(u, v) 代表此边的权重，若存在 T 为 E 的子集且 (V, T) 为树，使得的 w(T) - 即该树的所有边的权重和最小，则此 T 为 G 的最小生成树。  
最小生成树其实是最小权重生成树的简称。  

https://www.youtube.com/watch?v=wmW8G8SrXDs  

![](./mst1.png)  

## Prim 算法
原理：贪心 + 优先队列  
Time: O(ElogV), Space: O(V+E)  

![](./mst2.png)  

```python
# Author: Huahua
from collections import defaultdict
from heapq import *
 
n = 4
edges = [[0,1,1],[0,3,3],[0,2,6],[2,3,2],[1,2,4],[1,3,5]]
g = defaultdict(list)
for e in edges:
  g[e[0]].append((e[1], e[2]))
  g[e[1]].append((e[0], e[2]))
 
q = []
cost = 0
seen = set()
heappush(q, (0, 0))
for _ in range(n):
  while True:
    w, u = heappop(q)
    if u in seen: continue  
    cost += w
    seen.add(u)
    for v, w in g[u]:
      if v in seen: continue
      heappush(q, (w, v))
    break
 
print(cost)
```

## Kruskal 算法
原理：并查集  
Time: O(ElogV), Space: O(V+E)  

![](./mst3.png)  

```python
# Author: Huahua
from collections import defaultdict
from heapq import *
 
n = 4
edges = [[0,1,1],[0,3,3],[0,2,6],[2,3,2],[1,2,4],[1,3,5]]
p = list(range(n))
 
 
def find(x):
  if x != p[x]: p[x] = find(p[p[x]])
  return p[x]
 
cost = 0
for u, v, w in sorted(edges, key=lambda x: x[2]):
  ru, rv = find(u), find(v)
  if ru == rv: continue
  p[ru] = rv
  cost += w
 
print(cost)
```

## 例题
* [Leetcode Q1135](./../../../Leetcode%20Practices/algorithms/medium/1135%20Connecting%20Cities%20With%20Minimum%20Cost.java)
* [Leetcode Q1168]()
