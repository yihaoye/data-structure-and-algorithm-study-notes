/**
In a warehouse, there is a row of barcodes, where the ith barcode is barcodes[i].

Rearrange the barcodes so that no two adjacent barcodes are equal. You may return any answer, and it is guaranteed an answer exists.

 

Example 1:

Input: barcodes = [1,1,1,2,2,2]
Output: [2,1,2,1,2,1]
Example 2:

Input: barcodes = [1,1,1,1,2,2,3,3]
Output: [1,3,1,3,1,2,1,2]
 

Constraints:

1 <= barcodes.length <= 10000
1 <= barcodes[i] <= 10000
 */



// My Solution:
class Solution {
    public int[] rearrangeBarcodes(int[] barcodes) {
        // greedy + map count + priorityqueue
        int[] res = new int[barcodes.length];
        Map<Integer, Integer> map = new HashMap<>();
        for (int barcode : barcodes) map.put(barcode, map.getOrDefault(barcode, 0) + 1);
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] == b[1] ? a[0] - b[0] : b[1] - a[1]);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            pq.offer(new int[]{entry.getKey(), entry.getValue()});
        }
        int[] lastPick = new int[]{-1, 0};
        for (int i=0; i<res.length; i++) {
            int[] unPick = null;
            if (!pq.isEmpty() && pq.peek()[0] == lastPick[0]) unPick = pq.poll();
            lastPick = pq.poll();
            res[i] = lastPick[0]; lastPick[1]--;
            if (unPick != null) pq.offer(unPick);
            if (lastPick[1] > 0) pq.offer(lastPick);
        }
        return res;
    }
}
