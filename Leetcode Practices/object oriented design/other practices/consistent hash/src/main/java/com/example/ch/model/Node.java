package com.example.ch.model;

import com.example.ch.util.*;
import java.util.*;

public class Node {
    private UUID nodeId;
    private String hostname;
    private int port;
    private NodeType type;
    private NodeStatus status;

    // real cache data, apply <String, String> for simplicity, could be <String, Object> or <Object, Object> for advance use case
    private Map<String, String> data;
    // <keyHash, keys> - not necessary but for performance improvement for data reassignment
    private TreeMap<Double, Set<String>> keyHashToKeys;

    public Node(UUID nodeId, String hostname, int port, NodeType type) {
        this.nodeId = nodeId;
        this.hostname = hostname;
        this.port = port;
        this.type = type;

        this.status = NodeStatus.ACTIVE;
        this.data = new HashMap<>();
        this.keyHashToKeys = new TreeMap<>();
    }

    public UUID getNodeId() {
        return nodeId;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public boolean isActive() {
        if (status == NodeStatus.ACTIVE) return true;
        return false;
    }

    public int getDataSize() {
        return data.size();
    }

    public String get(String key) {
        return data.get(key);
    }

    public Set<String> getKeysByKeyHash(Double keyHash) {
        return new HashSet<>(keyHashToKeys.get(keyHash));
    }

    public Set<Double> getKeyHashes() {
        return new HashSet<>(keyHashToKeys.keySet());
    }

    public Set<Double> getKeyHashesByRange(Double fromKeyHash, Double toKeyHash) {
        return new HashSet<>(keyHashToKeys.subMap(fromKeyHash, toKeyHash).keySet());
    }

    public NodeStatus getStatus() {
        return status;
    }

    public void setStatus(NodeStatus status) {
        this.status = status;
    }

    public void put(Double keyHash, String key, String value) {
        data.put(key, value);
        keyHashToKeys.computeIfAbsent(keyHash, x -> new HashSet<>()).add(key);
    }

    // invalidate key
    public void remove(Double keyHash, String key) {
        data.remove(key);
        keyHashToKeys.get(keyHash).remove(key);
        if (keyHashToKeys.get(keyHash).isEmpty()) keyHashToKeys.remove(keyHash);
    }

    public void clear() {
        data.clear();
        keyHashToKeys.clear();
    }

    public void shutDown() {
        setStatus(NodeStatus.INACTIVE);
        clear();
    }
}
