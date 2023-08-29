/*
Given a data stream input of non-negative integers a1, a2, ..., an, summarize the numbers seen so far as a list of disjoint intervals.

Implement the SummaryRanges class:

SummaryRanges() Initializes the object with an empty stream.
void addNum(int value) Adds the integer value to the stream.
int[][] getIntervals() Returns a summary of the integers in the stream currently as a list of disjoint intervals [starti, endi]. The answer should be sorted by starti.
 

Example 1:

Input
["SummaryRanges", "addNum", "getIntervals", "addNum", "getIntervals", "addNum", "getIntervals", "addNum", "getIntervals", "addNum", "getIntervals"]
[[], [1], [], [3], [], [7], [], [2], [], [6], []]
Output
[null, null, [[1, 1]], null, [[1, 1], [3, 3]], null, [[1, 1], [3, 3], [7, 7]], null, [[1, 3], [7, 7]], null, [[1, 3], [6, 7]]]

Explanation
SummaryRanges summaryRanges = new SummaryRanges();
summaryRanges.addNum(1);      // arr = [1]
summaryRanges.getIntervals(); // return [[1, 1]]
summaryRanges.addNum(3);      // arr = [1, 3]
summaryRanges.getIntervals(); // return [[1, 1], [3, 3]]
summaryRanges.addNum(7);      // arr = [1, 3, 7]
summaryRanges.getIntervals(); // return [[1, 1], [3, 3], [7, 7]]
summaryRanges.addNum(2);      // arr = [1, 2, 3, 7]
summaryRanges.getIntervals(); // return [[1, 3], [7, 7]]
summaryRanges.addNum(6);      // arr = [1, 2, 3, 6, 7]
summaryRanges.getIntervals(); // return [[1, 3], [6, 7]]
 

Constraints:

0 <= value <= 10^4
At most 3 * 10^4 calls will be made to addNum and getIntervals.
At most 10^2 calls will be made to getIntervals.
 

Follow up: What if there are lots of merges and the number of disjoint intervals is small compared to the size of the data stream?
*/



// My Solution:
class SummaryRanges {
    private TreeMap<Integer, Integer> tMap; // <from, to> interval
    private int[][] cacheIntervals; // improve performance not must
    private boolean intervalChanged;

    public SummaryRanges() {
        // TreeMap + Binary Search
        tMap = new TreeMap<>();
        cacheIntervals = new int[0][2];
        intervalChanged = false;
    }
    
    public void addNum(int value) {
        Integer closestLeft = tMap.floorKey(value);
        Integer closestRight = tMap.ceilingKey(value);

        // no existing interval interact with value
        if ((closestLeft == null || tMap.get(closestLeft) < value - 1) && (closestRight == null || closestRight > value + 1)) {
            tMap.put(value, value);
            intervalChanged = true;
            return;
        }

        // closestRight interval interact with value
        if (closestRight != null) {
            if (closestRight == value) return;
            if (closestRight == value + 1) {
                tMap.put(value, tMap.get(closestRight));
                tMap.remove(closestRight);
                intervalChanged = true;
                closestRight = value;
            }
        }

        // closestLeft interval interact with value
        if (closestLeft != null) {
            if (tMap.get(closestLeft) >= value) return;
            if (tMap.get(closestLeft) == value - 1) {
                tMap.put(closestLeft, value);
                intervalChanged = true;
            }
        }

        // closestLeft interval combine closestRight interval
        if (closestLeft != null && tMap.get(closestLeft) == closestRight) {
            tMap.put(closestLeft, tMap.get(closestRight));
            tMap.remove(closestRight);
        }
    }
    
    public int[][] getIntervals() {
        if (!intervalChanged) return cacheIntervals;
        int len = tMap.size(); int idx = 0;
        cacheIntervals = new int[len][2];
        for (Map.Entry<Integer, Integer> entry : tMap.entrySet()) {
            cacheIntervals[idx][0] = entry.getKey();
            cacheIntervals[idx][1] = entry.getValue();
            idx++;
        }
        intervalChanged = false;
        return cacheIntervals;
    }
}
/**
 * Your SummaryRanges object will be instantiated and called as such:
 * SummaryRanges obj = new SummaryRanges();
 * obj.addNum(value);
 * int[][] param_2 = obj.getIntervals();
 */
