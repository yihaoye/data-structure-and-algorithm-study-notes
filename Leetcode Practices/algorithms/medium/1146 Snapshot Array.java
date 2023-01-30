/**
Implement a SnapshotArray that supports the following interface:

SnapshotArray(int length) initializes an array-like data structure with the given length. Initially, each element equals 0.
void set(index, val) sets the element at the given index to be equal to val.
int snap() takes a snapshot of the array and returns the snap_id: the total number of times we called snap() minus 1.
int get(index, snap_id) returns the value at the given index, at the time we took the snapshot with the given snap_id
 

Example 1:

Input: ["SnapshotArray","set","snap","set","get"]
[[3],[0,5],[],[0,6],[0,0]]
Output: [null,null,0,null,5]
Explanation: 
SnapshotArray snapshotArr = new SnapshotArray(3); // set the length to be 3
snapshotArr.set(0,5);  // Set array[0] = 5
snapshotArr.snap();  // Take a snapshot, return snap_id = 0
snapshotArr.set(0,6);
snapshotArr.get(0,0);  // Get the value of array[0] with snap_id = 0, return 5
 

Constraints:

1 <= length <= 5 * 10^4
0 <= index < length
0 <= val <= 10^9
0 <= snap_id < (the total number of times we call snap())
At most 5 * 10^4 calls will be made to set, snap, and get.
 */



// My Solution:
class SnapshotArray {
    Map<Integer, TreeMap<Integer, Integer>> snaps = new HashMap<>(); // <elem idx, <snap_id, value>>
    Map<Integer, Integer> curTemp = new HashMap<>(); // <snap_id, value>
    int snapId = 0;

    public SnapshotArray(int length) {
        // HashMap (store different snap version) + HashMap (cur temp version) + binary search
        for (int i=0; i<length; i++) {
            snaps.put(i, new TreeMap<>());
        }
    }
    
    public void set(int index, int val) {
        curTemp.put(index, val);
    }
    
    public int snap() {
        while (!curTemp.isEmpty()) {
            Map.Entry<Integer, Integer> entry = curTemp.entrySet().iterator().next();
            int index = entry.getKey(); int val = entry.getValue();
            snaps.get(index).put(snapId, val);
            curTemp.remove(index);
        }
        snapId++;
        return snapId - 1;
    }
    
    public int get(int index, int snap_id) {
        TreeMap<Integer, Integer> elemVersions = snaps.get(index);
        Integer actualVersion = elemVersions.floorKey(snap_id);
        if (actualVersion == null) return 0; // default value;
        return elemVersions.get(actualVersion);
    }
}
/**
 * Your SnapshotArray object will be instantiated and called as such:
 * SnapshotArray obj = new SnapshotArray(length);
 * obj.set(index,val);
 * int param_2 = obj.snap();
 * int param_3 = obj.get(index,snap_id);
 */



// Other's Solution: 
// https://leetcode.com/problems/snapshot-array/solutions/350562/java-python-binary-search/?orderBy=most_votes
class SnapshotArray {
    TreeMap<Integer, Integer>[] A;
    int snap_id = 0;
    public SnapshotArray(int length) {
        A = new TreeMap[length];
        for (int i = 0; i < length; i++) {
            A[i] = new TreeMap<Integer, Integer>();
            A[i].put(0, 0);
        }
    }

    public void set(int index, int val) {
        A[index].put(snap_id, val);
    }

    public int snap() {
        return snap_id++;
    }

    public int get(int index, int snap_id) {
        return A[index].floorEntry(snap_id).getValue();
    }
}
