import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket")
public class WebSocketServer {
    private static Set<Session> sessions = new CopyOnWriteArraySet<>(); // 用于存储所有连接的 WebSocket 会话
    private static BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session); // 当有新连接建立时，将会话添加到集合中
    }

    @OnMessage
    public void onMessage(String message, Session session) { // 处理客户端发送的消息，可以在这里往消息队列里 publish 新消息等逻辑
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session); // 当连接关闭时，从集合中移除会话
    }

    public static void run(int threadCount) {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) { // 启动多个线程从队列中读取数据并进行处理
            executor.execute(() -> {
                try {
                    while (true) {
                        String msg = queue.take(); // 阻塞直到队列非空
                        pushMessageToSubscribers(msg);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        executor.shutdown();
    }

    public static void pushMessageToSubscribers(String message) {
        try {
            for (Session session : sessions) {
                session.getBasicRemote().sendText(message); // 遍历所有会话并推送消息。可以维护一个会话订阅主题映射关系，只向需要接收消息的会话推送消息
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
示例中使用的是 Java WebSocket API，其中 Session 是 WebSocket 的会话抽象，它已经封装了底层的 Socket 处理，使得无需直接操作 Socket。
WebSocket 是一种在 HTTP 之上的协议，用于实现双向通信，而 Java WebSocket API 提供了一组用于处理 WebSocket 通信的高级抽象，
包括 Session 来表示 WebSocket 会话、Endpoint 来处理 WebSocket 端点逻辑等。

通过使用 Session，可以更方便地处理 WebSocket 通信，包括接收和发送消息，而不必亲自处理底层的 Socket 通信。这样可以简化开发，并提供了更高级的抽象，使 WebSocket 开发更加便捷。

在这个示例中，Session 的 getBasicRemote().sendText(message) 方法用于向客户端发送文本消息，而 @OnOpen、@OnMessage 和 @OnClose 等注解用于处理 WebSocket 的生命周期事件。
*/
