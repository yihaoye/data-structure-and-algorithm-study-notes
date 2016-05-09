//Question:
/*
 Given a non-empty array of integers, return the k most frequent elements.

For example,
Given [1,1,1,2,2,3] and k = 2, return [1,2].

Note:

    You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
    Your algorithm's time complexity must be better than O(n log n), where n is the array's size.

*/




//Other's Solution:
public List<Integer> topKFrequent(int[] nums, int k) {

    List<Integer>[] bucket = new List[nums.length + 1];
    Map<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();

    for (int n : nums) {
        frequencyMap.put(n, frequencyMap.getOrDefault(n, 0) + 1);
    }

    for (int key : frequencyMap.keySet()) {
        int frequency = frequencyMap.get(key);
        if (bucket[frequency] == null) {
            bucket[frequency] = new ArrayList<>();
        }
        bucket[frequency].add(key);
    }

    List<Integer> res = new ArrayList<>();

    for (int pos = bucket.length - 1; pos >= 0 && res.size() < k; pos--) {
        if (bucket[pos] != null) {
            res.addAll(bucket[pos]);
        }
    }
    return res;
}    


//Other's Solution 2:
public List<Integer> topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> frequencyMap = new HashMap<>();
    int maxFrequency = 0;

    for (int n : nums) {
        int frequency = frequencyMap.getOrDefault(n, 0) + 1;
        frequencyMap.put(n, frequency);
        maxFrequency = Math.max(maxFrequency, frequency);
    }

    // here i is the frequency and bucket.get(i) is the numbers that having this frequency
    List<List<Integer>> bucket = new ArrayList<>(maxFrequency);
    while (maxFrequency-- >= 0) {
        bucket.add(new ArrayList<>());
    }

    for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
        int frequency = entry.getValue();
        bucket.get(frequency).add(entry.getKey());
    }

    List<Integer> res = new ArrayList<>();
    for (int pos = bucket.size() - 1; pos >= 0 && res.size() < k; pos--) {
        res.addAll(bucket.get(pos));
    }
    return res;
}






/* Someone's comment:

Thanks for sharing, only one nitpick:

Think about the case when K=2, and you have 1 number that has max frequency, say 10 times. and you have 10 numbers that has 2nd max frequency, say 9 times. With your algo, the returned list will contain 11 numbers instead of 2.

Any easy fix: return res.subList(0,k);

(It seems the above scenario is not covered by the existing test cases.)

*/