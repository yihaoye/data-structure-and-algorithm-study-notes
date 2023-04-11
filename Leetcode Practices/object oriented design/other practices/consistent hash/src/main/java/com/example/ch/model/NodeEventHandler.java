package com.example.ch.model;

public interface NodeEventHandler {
    /*
     * Difference between nodeRemoved and nodeShuttingDown is that nodeRemoved means the node is down already, while nodeShuttingDown
     * signals the intent to shutdown. nodeShuttingDown is only required in the challenge portion
     */
    void nodeAdded(Node node);
    void nodeRemoved(Node node);
    void nodeShuttingDown(Node node);
}
