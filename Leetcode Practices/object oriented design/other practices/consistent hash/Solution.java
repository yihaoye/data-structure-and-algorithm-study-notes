import java.util.*;
import java.math.*;
import java.security.*;

public class Solution {
    public static void main(String[] args) {
        // test ...
        ConsistentHashCluster cluster = new ConsistentHashCluster(10);
        Node node1 = new Node(UUID.randomUUID(), "localhost", 8080, NodeType.CACHE);
        Node node2 = new Node(UUID.randomUUID(), "localhost", 8081, NodeType.CACHE);
        Node node3 = new Node(UUID.randomUUID(), "localhost", 8082, NodeType.CACHE);
        cluster.nodeAdded(node1);
        cluster.nodeAdded(node2);
        cluster.nodeAdded(node3);

        cluster.putData("key1", "value1");
        cluster.putData("key2", "value2");
        cluster.putData("key3", "value3");

        cluster.nodeRemoved(node1);
        cluster.nodeRemoved(node2);

        cluster.getData("key1");
        cluster.getData("key2");
        cluster.getData("key3");
    }
}

public interface NodeEventHandler {
    /*
    * Difference between nodeRemoved and nodeShuttingDown is that nodeRemoved means the node is down already, while nodeShuttingDown
    * signals the intent to shutdown. nodeShuttingDown is only required in the challenge portion 
    */
    void nodeAdded(Node node);
    void nodeRemoved(Node node);
    void nodeShuttingDown(Node node);
}

public class ConsistentHashCluster implements NodeEventHandler {
    private final int DEFAULT_VIRTUAL_NODE_NUM = 150; // large virtual/replicas node number makes the distribution more even
    private int vNodeNum;
    private SortedMap<Double, Node> vNodeToNode = new TreeMap<>(); // <vNode hash, real Node>, apply TreeMap to make the hash range sorted to make reassignment faster

    public ConsistentHashCluster() {
        this(DEFAULT_VIRTUAL_NODE_NUM);
    }

    public ConsistentHashCluster(int vNodeNum) {
        if (vNodeNum <= 0) throw new IllegalArgumentException("vNodeNum must be positive");
        this.vNodeNum = vNodeNum;
    }
    
    @Override
    public void nodeAdded(Node node) {
        for (int i = 0; i < vNodeNum; i++) {
            Double vNodeHash = hash(node.getNodeId() + "-" + i);

            // data reassignment
            Double fromVNode = vNodeToNode.floorKey(vNodeHash);
            if (fromVNode == null && !vNodeToNode.isEmpty()) fromVNode = vNodeToNode.lastKey();
            Double upperBound = vNodeToNode.ceilingKey(vNodeHash); // vNodeHash is the lower bound of the reassign data range
            Node fromNode = vNodeToNode.get(fromVNode);
            if (fromNode != null && !fromNode.getNodeId().equals(node.getNodeId())) {
                SortedMap<Double, Set<String>> subMap = (upperBound != null) ? fromNode.keyHashToKeys.subMap(vNodeHash, upperBound) : fromNode.keyHashToKeys.tailMap(vNodeHash);
                for (Double keyHash : subMap.keySet()) {
                    Set<String> keys = subMap.get(keyHash);
                    for (String key : keys) {
                        String value = fromNode.data.get(key);
                        node.putData(keyHash, key, value);
                        fromNode.removeData(keyHash, key);
                    }
                }
            }

            vNodeToNode.put(vNodeHash, node);
        }
    }

    @Override
    public void nodeRemoved(Node node) {
        nodeShuttingDown(node);
    }

    @Override
    public void nodeShuttingDown(Node node) {
        if (vNodeToNode.size() == 0) throw new RuntimeException("No active node in the cluster");
        if (!vNodeToNode.containsKey(hash(node.getNodeId() + "-" + 0))) throw new RuntimeException("Node " + node.getNodeId() + " is not in the cluster");
        if (vNodeToNode.size() == vNodeNum) throw new RuntimeException("Only one active node in the cluster");

        for (int i = 0; i < vNodeNum; i++) {
            Double vNodeHash = hash(node.getNodeId() + "-" + i);
            vNodeToNode.remove(vNodeHash);
        }

        // data reassignment
        node.getKeyHashToKeys().forEach((keyHash, keys) -> {
            Double toVNode = vNodeToNode.floorKey(keyHash);
            if (toVNode == null) toVNode = vNodeToNode.lastKey();
            Node toNode = vNodeToNode.get(toVNode);
            for (String key : keys) {
                String value = node.getData().get(key);
                toNode.putData(keyHash, key, value);
                node.removeData(keyHash, key);
            }
        });
    }

    public String getData(String key) {
        if (vNodeToNode.isEmpty()) throw new RuntimeException("No node in the cluster");

        Double keyHash = hash(key);
        Double matchVNode = vNodeToNode.floorKey(keyHash);
        if (matchVNode == null) matchVNode = vNodeToNode.lastKey();
        Node matchNode = vNodeToNode.get(matchVNode);
        return matchNode.getData(keyHash, key);
    }

    public void putData(String key, String value) {
        if (vNodeToNode.isEmpty()) throw new RuntimeException("No node in the cluster");

        Double keyHash = hash(key);
        Double matchVNode = vNodeToNode.floorKey(keyHash);
        if (matchVNode == null) matchVNode = vNodeToNode.lastKey();
        Node matchNode = vNodeToNode.get(matchVNode);
        matchNode.putData(keyHash, key, value);
    }

    public void removeData(String key) { // invalidate key
        if (vNodeToNode.isEmpty()) throw new RuntimeException("No node in the cluster");

        Double keyHash = hash(key);
        Double matchVNode = vNodeToNode.floorKey(keyHash);
        if (matchVNode == null) matchVNode = vNodeToNode.lastKey();
        Node matchNode = vNodeToNode.get(matchVNode);
        matchNode.removeData(keyHash, key);
    }

    /**
     * customized hash function, could be replaced by other hash functions
     * the hash value is uniformly distributed in [0, 1) and assume no collision in this example (for simplicity, and reference method in https://web.archive.org/web/20221230083731/https://michaelnielsen.org/blog/consistent-hashing/)
     * otherwise, we could apply better hash function or higher precision data type as return hash such as BigDecimal
     */
    private Double hash(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(key.getBytes());
            BigInteger bigInt = new BigInteger(1, digest);
            double hash = bigInt.doubleValue() % 1000000 / 1000000.0;
            return hash;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

public class Node {
    private UUID nodeId;
    private String hostname;
    private int port;
    private NodeType type;

    private Map<String, String> data; // real cache data
    private SortedMap<Double, Set<String>> keyHashToKeys; // <keyHash, keys> - not necessary but for performance improvement for data reassignment

    Node(String hostname, int port, NodeType type) {
        this.nodeId = UUID.randomUUID();
        this.hostname = hostname;
        this.port = port;
        this.type = type;

        this.data = new HashMap<>();
        this.keyHashToKeys = new TreeMap<>();
    }

    public UUID getNodeId() {
        return nodeId;
    }

    public Map<String, String> getData() {
        return data;
    }

    public SortedMap<Double, Set<String>> getKeyHashToKeys() {
        return keyHashToKeys;
    }

    public void putData(Double keyHash, String key, String value) {
        data.put(key, value);
        keyHashToKeys.computeIfAbsent(keyHash, x -> new HashSet<>()).add(key);
    }

    public void removeData(Double keyHash, String key) { // invalidate key
        data.remove(key);
        keyHashToKeys.get(keyHash).remove(key);
        if (keyHashToKeys.get(keyHash).isEmpty()) keyHashToKeys.remove(keyHash);
    }
}

public enum NodeType {
    REDIS, MEMCACHE
}
