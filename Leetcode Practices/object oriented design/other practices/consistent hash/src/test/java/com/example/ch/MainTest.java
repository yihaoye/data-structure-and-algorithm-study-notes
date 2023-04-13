package com.example.ch;

import com.example.ch.model.*;
import com.example.ch.util.*;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

public class MainTest {
    @Test public void testConsistentHashClusterBasicFunctions() {
        ConsistentHashCluster cluster = new ConsistentHashCluster(10); // apply 10 vNode for simple test
        Node node = new Node(UUID.randomUUID(), "192.168.0.0", 8080, NodeType.MEMCACHE);
        cluster.nodeAdded(node);

        cluster.put("key1", "value1");
        assertEquals(cluster.get("key1"), "value1");

        cluster.put("key1", "update1");
        assertEquals(cluster.get("key1"), "update1");

        cluster.remove("key1");
        assertEquals(cluster.get("key1"), null);
    }

    @Test public void testConsistentHashClusterNodeAddAndDataReassign() {
        ConsistentHashCluster cluster = new ConsistentHashCluster();
        Node node0 = new Node(UUID.randomUUID(), "192.168.0.0", 8080, NodeType.MEMCACHE);
        cluster.nodeAdded(node0);

        int keyCount = 10;
        for (int j=0; j<keyCount; j++) cluster.put("key" + j, "value" + j);
        for (int j=0; j<keyCount; j++) assertEquals(cluster.get("key" + j), "value" + j);
        assertEquals(node0.getDataSize(), keyCount);

        int nodeCount = 10;
        for (int i=1; i<nodeCount; i++) {
            Node node = new Node(UUID.randomUUID(), "192.168.0." + i, 8080, NodeType.REDIS);
            cluster.nodeAdded(node);
        }

        // node 0 should have data reassigned to other new node
        assertNotEquals(node0.getDataSize(), keyCount);

        // data should as same as before totally
        for (int j=1; j<keyCount; j++) assertEquals(cluster.get("key" + j), "value" + j);
        int dataCount = 0;
        for (Node node : cluster.getNodes().values()) dataCount += node.getDataSize();
        assertEquals(dataCount, keyCount);
    }

    @Test public void testConsistentHashClusterNodeShuttingDownAndDataReassign() {
        ConsistentHashCluster cluster = new ConsistentHashCluster();
        int nodeCount = 3;
        for (int i=0; i<nodeCount; i++) {
            Node node = new Node(UUID.randomUUID(), "192.168.0." + i, 8080, NodeType.REDIS);
            cluster.nodeAdded(node);
        }

        int keyCount = 10;
        for (int j=0; j<keyCount; j++) cluster.put("key" + j, "value" + j);
        for (int j=0; j<keyCount; j++) assertEquals(cluster.get("key" + j), "value" + j);

        int dataCount = 0;
        for (Node node : cluster.getNodes().values()) dataCount += node.getDataSize();
        assertEquals(dataCount, keyCount);

        Node removeNode = cluster.getMatchNodeByKey("key1");
        cluster.nodeRemoved(removeNode);
        Node reAssignNode = cluster.getMatchNodeByKey("key1");
        assertNotEquals(removeNode.getNodeId(), reAssignNode.getNodeId());

        // data should as same as before totally
        for (int j=1; j<keyCount; j++) assertEquals(cluster.get("key" + j), "value" + j);
        dataCount = 0;
        for (Node node : cluster.getNodes().values())
            if (node.isActive()) dataCount += node.getDataSize();
        assertEquals(dataCount, keyCount);
    }

    @Test public void testConsistentHashClusterNodeFailure() {
        ConsistentHashCluster cluster = new ConsistentHashCluster(10);
        Node node = new Node(UUID.randomUUID(), "192.168.0.0", 8080, NodeType.MEMCACHE);
        cluster.nodeAdded(node);
        cluster.nodeAdded(new Node(UUID.randomUUID(), "192.168.0.1", 8080, NodeType.MEMCACHE));

        cluster.put("key1", "value1");
        assertEquals(cluster.get("key1"), "value1");

        node.setStatus(NodeStatus.INACTIVE);
        boolean removeFailure = false;
        try {
            cluster.nodeRemoved(node);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            removeFailure = true;
        }
        assertEquals(removeFailure, true);

        // node add still work
        cluster.nodeAdded(new Node(UUID.randomUUID(), "192.168.0.2", 8080, NodeType.MEMCACHE));
    }
}

