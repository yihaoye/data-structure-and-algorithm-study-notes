/**
You are given two string arrays creators and ids, and an integer array views, all of length n. The ith video on a platform was created by creator[i], has an id of ids[i], and has views[i] views.

The popularity of a creator is the sum of the number of views on all of the creator's videos. Find the creator with the highest popularity and the id of their most viewed video.

If multiple creators have the highest popularity, find all of them.
If multiple videos have the highest view count for a creator, find the lexicographically smallest id.
Return a 2D array of strings answer where answer[i] = [creatori, idi] means that creatori has the highest popularity and idi is the id of their most popular video. The answer can be returned in any order.

 

Example 1:

Input: creators = ["alice","bob","alice","chris"], ids = ["one","two","three","four"], views = [5,10,5,4]
Output: [["alice","one"],["bob","two"]]
Explanation:
The popularity of alice is 5 + 5 = 10.
The popularity of bob is 10.
The popularity of chris is 4.
alice and bob are the most popular creators.
For bob, the video with the highest view count is "two".
For alice, the videos with the highest view count are "one" and "three". Since "one" is lexicographically smaller than "three", it is included in the answer.
Example 2:

Input: creators = ["alice","alice","alice"], ids = ["a","b","c"], views = [1,2,2]
Output: [["alice","b"]]
Explanation:
The videos with id "b" and "c" have the highest view count.
Since "b" is lexicographically smaller than "c", it is included in the answer.
 

Constraints:

n == creators.length == ids.length == views.length
1 <= n <= 10^5
1 <= creators[i].length, ids[i].length <= 5
creators[i] and ids[i] consist only of lowercase English letters.
0 <= views[i] <= 10^5
 */



// My Solution:
class Solution {
    public List<List<String>> mostPopularCreator(String[] creators, String[] ids, int[] views) {
        // HashMap + List
        int maxViewSum = 0, n = creators.length;
        Set<String> creatorsRes = new HashSet<>();
        Map<String, int[]> creatorPopularity = new HashMap<>(); // Map<creator, [creatorViewSum, creatorTopViewIndex]>
        for (int i=0; i<n; i++) {
            creatorPopularity.putIfAbsent(creators[i], new int[]{0, -1});
            int[] creatorData = creatorPopularity.get(creators[i]);
            creatorData[0] += views[i];
            if (creatorData[1] == -1 || views[i] > views[creatorData[1]] || (views[i] == views[creatorData[1]] && ids[i].compareTo(ids[creatorData[1]]) < 0)) creatorData[1] = i;
            if (creatorData[0] > maxViewSum) {
                maxViewSum = creatorData[0];
                creatorsRes = new HashSet<>();
                creatorsRes.add(creators[i]);
            } else if (creatorData[0] == maxViewSum) {
                creatorsRes.add(creators[i]);
            }
        }
        
        List<List<String>> res = new ArrayList<>();
        for (String creator : creatorsRes) {
            int[] creatorData = creatorPopularity.get(creator);
            List<String> tmp = new ArrayList<>();
            tmp.add(creator); tmp.add(ids[creatorData[1]]);
            res.add(tmp);
        }
        return res;
    }
}
