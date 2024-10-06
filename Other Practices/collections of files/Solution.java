/*
Given a list of file names, the collection the file belongs to and the size of each file. 
Write a program to find the top N collections by size and the total size of all the files in the system. 

Follow up - collections can be nested, find top N collections by size

Topic: Tree (Node)
*/

import java.util.*;

class Solution {
    static TreeSet<Node> tSet = new TreeSet<>();

    public static List<Node> findTopN(int n) {
        List<Node> result = new ArrayList<>();
        Iterator<Node> iterator = tSet.iterator();
        for (int i = 0; i < n && iterator.hasNext(); i++) {
            result.add(iterator.next());
        }
        return result;
    }

    public static void main(String[] args) {
        // 构建示例文件系统 - 这里模拟实际使用场景，通常我们在已存在的路径里创建文件夹或文件，因此这里按序创建它们，另外这里先不实现删除或移动文件夹或文件的功能
        Node root = new Node("root", 0, false, null);

        Node docs = new Node("docs", 0, false, root);
        Node file1 = new Node("file1.txt", 150, true, docs);
        Node file2 = new Node("file2.txt", 200, true, docs);

        Node medias = new Node("medias", 0, false, root);
        
        Node pictures = new Node("pictures", 0, false, medias);
        Node pic1 = new Node("pic1.jpg", 300, true, pictures);
        
        Node musics = new Node("musics", 0, false, medias);
        Node music1 = new Node("music1.mp3", 400, true, musics);

        tSet.add(docs);
        tSet.add(medias);
        tSet.add(pictures);
        tSet.add(musics);

        // 查找最大的 3 个文件夹
        List<Node> topN = findTopN(3);
        for (Node node : topN) {
            System.out.println(node.name + ": " + node.size + " bytes");
        }

        // 输出总大小
        System.out.println("Total size: " + root.size + " bytes");
    }
}

class Node implements Comparable<Node> {
    static int idInc = 0;
    private int id;

    public String name;
    public final boolean isFile;
    public long size;

    private Node parent;
    private Set<Node> children;

    Node(String name, long size, boolean isFile, Node parent) {
        this.id = idInc; idInc++;
        this.name = name;
        this.isFile = isFile;
        if (isFile) this.size = size; // collections initial size should be 0, set a check here
        this.parent = parent;
        if (parent != null) parent.children.add(this);
        Node tmpParent = parent;
        while (tmpParent != null) {
            tmpParent.size += size;
            tmpParent = tmpParent.parent;
        }
        if (!isFile) children = new HashSet<>(); // file node will not have children, so add/remove will throw exception
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(Node o) {
        int cmp = Long.compare(o.size, size);
        return cmp != 0 ? cmp : Integer.compare(this.id, o.id);
    }



    /***************** Not in used yet *****************/
    public void move(Node node, Node from, Node to) {
        from.remove(node);
        to.children.add(node);
        node.parent = to;
        
        Node tmpParent = node.parent;
        while (tmpParent != null) {
            tmpParent.size += node.size;
            tmpParent = tmpParent.parent;
        }
    }

    public void remove(Node node) {
        if (!children.remove(node)) return;
        node.parent = null;
        size -= node.size;

        Node tmpParent = parent;
        while (tmpParent != null) {
            tmpParent.size -= node.size;
            tmpParent = tmpParent.parent;
        }
    }

    public void setName(String name) {
        if (parent == null) {
            this.name = name;
            return;
        }
        parent.children.remove(this);
        this.name = name;
        parent.children.add(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return id == node.id;
    }
}
