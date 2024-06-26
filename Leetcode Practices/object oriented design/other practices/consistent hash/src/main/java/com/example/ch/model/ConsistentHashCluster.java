package com.example.ch.model;

import java.math.*;
import java.security.*;
import java.util.*;

public class ConsistentHashCluster implements NodeEventHandler {
    // apply virtual node and large virtual/replicas node number makes the distribution more even
    private final int DEFAULT_VIRTUAL_NODE_NUM = 150;
    private int vNodeNum;
    // request key hash will be redirected to the vNode which is the largest vNode hash equals or smaller than the request key hash, 
    // if the request key hash find no smaller vNode, it will be redirected to the largest vNode
    // i.e. find the first vNode on the counterclockwise direction of the unit circle
    private TreeMap<Double, Node> vNodeToNode = new TreeMap<>(); // <vNode hash, real Node>, apply TreeMap to make the hash range sorted to make reassignment and load balance faster
    private Map<UUID, Node> nodes = new HashMap<>();

    public ConsistentHashCluster() {
        this.vNodeNum = DEFAULT_VIRTUAL_NODE_NUM;
    }

    public ConsistentHashCluster(int vNodeNum) {
        if (vNodeNum <= 0) throw new IllegalArgumentException("vNodeNum must be positive");
        this.vNodeNum = vNodeNum;
    }

    @Override
    public synchronized void nodeAdded(Node node) {
        if (nodes.containsKey(node.getNodeId()))
            throw new RuntimeException("Node: " + node.getNodeId() + " hash already exist in cluster");

        for (int i = 0; i < vNodeNum; i++) {
            Double vNodeHash = hash(node.getNodeId() + "-" + i);
            if (vNodeToNode.containsKey(vNodeHash))
                throw new RuntimeException("Virtual Node: " + node.getNodeId() + "-" + i + " hash already exist in cluster, it may caused by hash collision");

            // data reassignment
            Double fromVNode = vNodeToNode.floorKey(vNodeHash);
            if (fromVNode == null && !vNodeToNode.isEmpty()) fromVNode = vNodeToNode.lastKey();
            // vNodeHash is the lower bound of the reassign data range, data equals or larger than upperBound should not be reassign since it may belongs to some other vNode of the same fromVNode
            Double upperBound = vNodeToNode.ceilingKey(vNodeHash);
            if (upperBound == null) upperBound = 1.0;
            Node fromNode = fromVNode != null ? vNodeToNode.get(fromVNode) : null;
            // if fromNode and node is the same node, no need to reassign data, this is possible when the new vNode is next to the old vNode which created in the same for loop
            if (fromNode != null && !fromNode.getNodeId().equals(node.getNodeId())) {
                Set<Double> keyHashes = fromNode.getKeyHashesByRange(vNodeHash, upperBound);
                for (Double keyHash : keyHashes) {
                    Set<String> keys = fromNode.getKeysByKeyHash(keyHash);
                    for (String key : keys) {
                        String value = fromNode.get(key);
                        node.put(keyHash, key, value);
                        fromNode.remove(keyHash, key);
                    }
                }
            }

            vNodeToNode.put(vNodeHash, node);
        }
        nodes.put(node.getNodeId(), node);
    }

    @Override
    public synchronized void nodeRemoved(Node node) {
        nodeShuttingDown(node);
        nodes.remove(node.getNodeId());
    }

    @Override
    public synchronized void nodeShuttingDown(Node node) {
        if (!node.isActive())
            throw new RuntimeException("Node " + node.getNodeId() + " is failure");
        if (vNodeToNode.size() == 0)
            throw new RuntimeException("No active node in the cluster");
        if (!vNodeToNode.containsKey(hash(node.getNodeId() + "-" + 0)))
            throw new RuntimeException("Node " + node.getNodeId() + " is not in the cluster");
        if (vNodeToNode.size() == vNodeNum)
            throw new RuntimeException("Only one active node in the cluster");

        // remove vNode from vNodeToNode to make following data reassignment logic simpler and cleaner
        for (int i = 0; i < vNodeNum; i++) {
            Double vNodeHash = hash(node.getNodeId() + "-" + i);
            vNodeToNode.remove(vNodeHash);
        }

        // data reassignment
        node.getKeyHashes().forEach(keyHash -> {
            Set<String> keys = node.getKeysByKeyHash(keyHash);
            Double toVNode = vNodeToNode.floorKey(keyHash);
            if (toVNode == null) toVNode = vNodeToNode.lastKey();
            Node toNode = vNodeToNode.get(toVNode);
            for (String key : keys) {
                String value = node.get(key);
                toNode.put(keyHash, key, value);
            }
        });
        node.shutDown();
    }

    public synchronized String get(String key) {
        Node matchNode = getMatchNodeByKey(key);
        if (!matchNode.isActive())
            throw new RuntimeException("Matched Node " + matchNode.getNodeId() + " is failure");
        return matchNode.get(key);
    }

    public synchronized void put(String key, String value) {
        Node matchNode = getMatchNodeByKey(key);
        if (!matchNode.isActive())
            throw new RuntimeException("Matched Node " + matchNode.getNodeId() + " is failure");
        matchNode.put(hash(key), key, value);
    }

    // invalidate key
    public synchronized void remove(String key) {
        Node matchNode = getMatchNodeByKey(key);
        if (matchNode.get(key) == null)
            throw new RuntimeException("Key " + key + " is not in cache data store");
        if (!matchNode.isActive())
            throw new RuntimeException("Matched Node " + matchNode.getNodeId() + " is failure");
        matchNode.remove(hash(key), key);
    }

    public synchronized Node getNodeById(UUID nodeId) {
        return nodes.get(nodeId);
    }

    // get matched node by request
    public synchronized Node getMatchNodeByKey(String key) {
        if (vNodeToNode.isEmpty()) 
            throw new RuntimeException("No node in the cluster");

        Double keyHash = hash(key);
        Double matchVNode = vNodeToNode.floorKey(keyHash);
        if (matchVNode == null) matchVNode = vNodeToNode.lastKey();
        return vNodeToNode.get(matchVNode);
    }

    public synchronized Map<UUID, Node> getNodes() {
        return new HashMap<>(nodes);
    }

    /**
     * customized hash function, could be replaced by other hash functions
     * the hash value is uniformly distributed in [0, 1) and assume no collision in this example (for simplicity, and reference method in https://web.archive.org/web/20221230083731/https://michaelnielsen.org/blog/consistent-hashing/)
     * the easiest way to solve it is apply a new Node with new Node ID (UUID)
     * otherwise, we could apply better hash function or add hash collision resolver or higher precision data type as return hash such as BigDecimal
     * 
     * 这里采用 MD5 作为哈希函数，一般还可以采用 SHA-1、SHA-256 等哈希函数，也可以采用一些非加密哈希函数，比如 MurmurHash（尤其适合于需要高性能的场景）等
     */
    private Double hash(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(key.getBytes());
            BigInteger bigInt = new BigInteger(1, digest);
            double hash = bigInt.doubleValue() % 1e16 / 1e16; // double 小数点后最多可为 16 位，精度更低（比如 6 位）会容易出现哈希冲突，精度高在之前会造成 node add test fail，暂时不知道原因，目前不再出现该情况
            return hash;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
