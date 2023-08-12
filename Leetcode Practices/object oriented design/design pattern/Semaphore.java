// 信号量模式 Semaphore Pattern

/*
实现 ConnectionPool 的类，能够重复使用 connection
每个获取连接的请求都应该从池中获取一个连接，如果池为空，则创建一个新连接。池必须是线程安全的，并且在没有连接可用时（有最多连接数量限制），获取连接的请求应该被阻塞。

注意：在哪加锁更高效？open connection 是一个 expensive 操作
*/

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

class ConnectionPool { // By ChatGPT
    private static volatile ConnectionPool instance;
    private BlockingQueue<Connection> connections;
    private Semaphore semaphore;

    private ConnectionPool(int maxConnections) {
        connections = new LinkedBlockingQueue<>();
        semaphore = new Semaphore(maxConnections);
    }

    public static ConnectionPool getInstance(int maxConnections) {
        if (instance != null) return instance;

        synchronized (ConnectionPool.class) {
            if (instance == null) instance = new ConnectionPool(maxConnections);
        }
        return instance;
    }

    public Connection getConnection() throws InterruptedException {
        semaphore.acquire(); // Acquire a permit, or block if none is available
        Connection connection = connections.poll();
        if (connection == null) {
            connection = new Connection(); // open a new connection
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            connections.offer(connection);
            semaphore.release(); // Release a permit
        }
    }
}


class Connection {
    public Connection() {
        // Expensive operation to open a connection
    }

    public String read() {
        // Read operation
        return null;
    }

    public void write(String data) {
        // Write operation
    }
}
