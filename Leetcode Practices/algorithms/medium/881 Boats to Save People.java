/*
You are given an array people where people[i] is the weight of the ith person, and an infinite number of boats where each boat can carry a maximum weight of limit. Each boat carries at most two people at the same time, provided the sum of the weight of those people is at most limit.

Return the minimum number of boats to carry every given person.

 

Example 1:

Input: people = [1,2], limit = 3
Output: 1
Explanation: 1 boat (1, 2)
Example 2:

Input: people = [3,2,2,1], limit = 3
Output: 3
Explanation: 3 boats (1, 2), (2) and (3)
Example 3:

Input: people = [3,5,3,4], limit = 5
Output: 4
Explanation: 4 boats (3), (3), (4), (5)
 

Constraints:

1 <= people.length <= 5 * 10^4
1 <= people[i] <= limit <= 3 * 10^4
*/



// My Solution:
class Solution {
    public int numRescueBoats(int[] people, int limit) {
        // 贪心 + TreeMap
        TreeMap<Integer, Integer> tMap = new TreeMap<>();
        for (int p : people) tMap.put(p, tMap.getOrDefault(p, 0) + 1);

        int res = 0;
        while (!tMap.isEmpty()) {
            res++;
            int firstPeople = tMap.lastKey();
            int rest = limit - firstPeople;
            if (tMap.get(firstPeople) == 1) tMap.remove(firstPeople);
            else tMap.put(firstPeople, tMap.get(firstPeople) - 1);

            Integer secPeople = tMap.floorKey(rest);
            if (secPeople == null) continue;
            if (tMap.get(secPeople) == 1) tMap.remove(secPeople);
            else tMap.put(secPeople, tMap.get(secPeople) - 1);
        }

        return res;
    }
}
