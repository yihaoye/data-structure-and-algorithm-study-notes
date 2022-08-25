//interface Router {
//    // register a route,
//    fun withRoute(path:String, result:String) :Unit;
//
//    fun route(path:String) :String;
//}
//
//      Router.withRoute("/bar", "result")
//      Router.route("/bar") -> "result"
//
//	    Router.withRoute("/bar/abc", "abc")
//		Router.route("/bar/abc") -> "abc"
//		Router.route("/bar/abc/dd") -> null	
//
//		Router.withRoute("/bar/abc/dd", "dd")
//      Router.withRoute("/bar/abc/cde/dd") -> "ee"	
//		Router.route("/bar/abc/dd") -> "dd"	
//		Router.route("/bar/*/dd") -> "dd"	
//
//      Router.withRoute("/ccd/cde", "cde")
//      Router.withRoute("/ccd/ccc", "ccc")



/*
root:          bar(result)                       ccd(null)
                /                                /      \
           abc(abc)                         cde(cde)    ccc(ccc)
            /     \
        dd(dd)   cde(null)
                  /
                dd(ee)

遇到星号 * 时暴力遍历当前 Node 的所有 children
*/
class RouterImpl implements Router { // 解决方案：类字典树+哈希表
    class TrieNode {
        public Map<String, TrieNode> children;
        public String value;

        public TrieNode() {
            this.children = new HashMap<>();
            this.value = null;
        }
    }

    private TrieNode root;

    public RouterImpl() {
        root = new TrieNode();
    }

    public boolean withRoute(String path, String result) {
        if (path.isEmpty()) {
            throw new RuntimeException("path should not be empty");
        }
        String[] pathArr = path.split("/");
        return register(pathArr, 0, root, result);
    }

    public String route(String path) {
        if (path.isEmpty()) {
            throw new RuntimeException("path should not be empty");
        }
        String[] pathArr = path.split("/");
        TrieNode findNode = find(pathArr, 0, root);
        if (findNode == null) return null;
        return findNode.value;
    }

    private boolean register(String[] pathArr, int curIndex, TrieNode curNode, String result) {
        if (curIndex == pathArr.length) {
            curNode.value = result;
            return true;
        } else {
            if (pathArr[curIndex] == "") { // curIndex == 0
                return register(pathArr, curIndex+1, curNode, result);
            }
            TrieNode nextNode = null;
            if (!curNode.children.containsKey(pathArr[curIndex])) {
                nextNode = new TrieNode();
                curNode.children.put(pathArr[curIndex], nextNode);
            }
            nextNode = curNode.children.get(pathArr[curIndex]);
            return register(pathArr, curIndex+1, nextNode, result);
        }
    }

    private TrieNode find(String[] pathArr, int curIndex, TrieNode curNode) {
        if (curIndex == pathArr.length) return curNode;
        if (pathArr[curIndex] != "*") {
            TrieNode nextNode = null;
            if (pathArr[curIndex] == "") nextNode = curNode;
            else nextNode = curNode.children.get(pathArr[curIndex]);

            if (nextNode == null) return null;
            return find(pathArr, curIndex+1, nextNode);
        } else {
            for (TrieNode nextNode : curNode.children.values()) {
                TrieNode findNode = find(pathArr, curIndex+1, nextNode);
                if (findNode != null) return findNode;
            }
            return null;
        }
    }
}

class Solution {
    public static void main(String[] args) {
        RouterImpl routerImpl = new RouterImpl();
        routerImpl.withRoute("/bar", "result");
        System.out.println(routerImpl.route("/bar"));

        // other test cases ...
        // ...
    }
}
