/**
You are asked to design a file system that allows you to create new paths and associate them with different values.

The format of a path is one or more concatenated strings of the form: / followed by one or more lowercase English letters. For example, "/leetcode" and "/leetcode/problems" are valid paths while an empty string "" and "/" are not.

Implement the FileSystem class:

bool createPath(string path, int value) Creates a new path and associates a value to it if possible and returns true. Returns false if the path already exists or its parent path doesn't exist.
int get(string path) Returns the value associated with path or returns -1 if the path doesn't exist.
 

Example 1:

Input: 
["FileSystem","createPath","get"]
[[],["/a",1],["/a"]]
Output: 
[null,true,1]
Explanation: 
FileSystem fileSystem = new FileSystem();

fileSystem.createPath("/a", 1); // return true
fileSystem.get("/a"); // return 1
Example 2:

Input: 
["FileSystem","createPath","createPath","get","createPath","get"]
[[],["/leet",1],["/leet/code",2],["/leet/code"],["/c/d",1],["/c"]]
Output: 
[null,true,true,2,false,-1]
Explanation: 
FileSystem fileSystem = new FileSystem();

fileSystem.createPath("/leet", 1); // return true
fileSystem.createPath("/leet/code", 2); // return true
fileSystem.get("/leet/code"); // return 2
fileSystem.createPath("/c/d", 1); // return false because the parent path "/c" doesn't exist.
fileSystem.get("/c"); // return -1 because this path doesn't exist.
 

Constraints:

The number of calls to the two functions is less than or equal to 10^4 in total.
2 <= path.length <= 100
1 <= value <= 10^9
 */



// My Solution:
class FileSystem {
    class TrieNode {
        Map<String, TrieNode> children;
        Integer value;
        
        public TrieNode() {
            this.children = new HashMap<>();
            this.value = null;
        }
    }
    
    private TrieNode root;

    public FileSystem() {
        // Trie + HashMap
        root = new TrieNode();
    }
    
    public boolean createPath(String path, int value) {
        if (path.isEmpty()) {
            throw new RuntimeException("path should not be empty");
        }
        String[] pathArr = path.split("/");
        return dfsCreate(pathArr, 0, root, value);
    }
    
    public int get(String path) {
        if (path.isEmpty()) {
            throw new RuntimeException("path should not be empty");
        }
        String[] pathArr = path.split("/");
        TrieNode findNode = dfsFind(pathArr, 0, root);
        if (findNode == null || findNode.value == null) return -1;
        return findNode.value;
    }
    
    private boolean dfsCreate(String[] pathArr, int curIndex, TrieNode curNode, int value) {
        if (curIndex == pathArr.length) {
            if (curNode.value != null) return false;
            curNode.value = value;
            return true;
        } else {
            if (pathArr[curIndex] == "") { // curIndex == 0
                return dfsCreate(pathArr, curIndex+1, curNode, value);
            }
            TrieNode nextNode = null;
            if (!curNode.children.containsKey(pathArr[curIndex])) {
                if (curIndex != pathArr.length-1) return false;
                nextNode = new TrieNode();
                curNode.children.put(pathArr[curIndex], nextNode);
            }
            nextNode = curNode.children.get(pathArr[curIndex]);
            return dfsCreate(pathArr, curIndex+1, nextNode, value);
        }
    }
    
    private TrieNode dfsFind(String[] pathArr, int curIndex, TrieNode curNode) {
        if (curIndex == pathArr.length) return curNode;
        TrieNode nextNode = null;
        if (pathArr[curIndex] == "") nextNode = curNode;
        else nextNode = curNode.children.get(pathArr[curIndex]);

        if (nextNode == null) return null;
        return dfsFind(pathArr, curIndex+1, nextNode);
    }
}
/**
 * Your FileSystem object will be instantiated and called as such:
 * FileSystem obj = new FileSystem();
 * boolean param_1 = obj.createPath(path,value);
 * int param_2 = obj.get(path);
 */
