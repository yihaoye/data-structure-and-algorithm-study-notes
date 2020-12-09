/*
Given a non-empty list of words, return the k most frequent elements.

Your answer should be sorted by frequency from highest to lowest. If two words have the same frequency, then the word with the lower alphabetical order comes first.

Example 1:
Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
Output: ["i", "love"]
Explanation: "i" and "love" are the two most frequent words.
    Note that "i" comes before "love" due to a lower alphabetical order.
Example 2:
Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
Output: ["the", "is", "sunny", "day"]
Explanation: "the", "is", "sunny" and "day" are the four most frequent words,
    with the number of occurrence being 4, 3, 2 and 1 respectively.
Note:
You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
Input words contain only lowercase letters.
Follow up:
Try to solve it in O(n log k) time and O(n) extra space.
*/



// My Solution (after inspired from other's solution: apply HashMap and PriorityQueue):
class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        Queue<Node> pq = new PriorityQueue<>(new MyComparator());
        Map<String, Integer> map = new HashMap<>();
        
        for (String word : words) {
            if (map.containsKey(word)) {
                int preCount = map.get(word);
                map.put(word, preCount+1);
            } else {
                map.put(word, 1);
            }
        }
        
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            pq.offer(new Node(entry.getKey(), entry.getValue()));
        }
        
        List<String> res = new ArrayList<>();
        while(k > 0 && !pq.isEmpty()) {
            res.add(pq.poll().key);
            k--;
        }
        
        return res;
    }
    
    public class MyComparator implements Comparator<Node> {
        public int compare(Node n1, Node n2) {
            if (n2.val.compareTo(n1.val) != 0) return n2.val.compareTo(n1.val);
            return n1.key.compareTo(n2.key);
        } 
    }
    
    public class Node {
        public String key;
        public Integer val;
        public Node(String key, Integer val) {
            this.key = key;
            this.val = val;
        }
    }
}
