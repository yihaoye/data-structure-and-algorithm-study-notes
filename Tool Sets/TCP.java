import java.net.*; // Socket, ServerSocket etc
import java.io.*;


// 当客户端想要打开到服务器的 TCP / IP 连接时，它使用 Java Socket 类来实现。 套接字被告知连接到哪个 IP 地址和 TCP 端口，其余部分由 Java 完成。
public class TcpClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 80); // i.e. clientSocket, 1st param could be ip address or domain name: new Socket("xxx.com", 80);
        OutputStream outputStream = socket.getOutputStream();

        outputStream.write("some data".getBytes());
        outputStream.flush();
        outputStream.close();
        socket.close();
    }
}


// 如果要启动服务器以侦听来自某个 TCP 端口上的客户端的传入连接，则必须使用 Java ServerSocket 类。
public class TcpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        Socket clientSocket = serverSocket.accept(); // 监听端口，accept 为阻塞方法

        InputStream inputStream = clientSocket.getInputStream(); // 获取输入流，读取数据
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String message;
        while ((message = bufferedReader.readLine()) != null) {
            System.out.printf(message); // "some data" -- from above client
        }
        clientSocket.shutdownInput();
        bufferedReader.close();
        serverSocket.close();
    }
}


// 当客户端通过客户端套接字连接到服务器的 ServerSocket 时，服务器上会为该连接分配一个 Socket。客户端和服务器的通信就是 Socket 到 Socket 的通信了。


/*
References:
    https://www.cnblogs.com/czwbig/p/10018118.html
    https://www.cnblogs.com/nedulee/p/12026342.html
*/


// NIO SocketChannel
import java.nio.channels.*;

public class TcpClient {
    public static void main(String[] args) throws IOException {
        // https://jenkov.com/tutorials/java-nio/socketchannel.html
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("http://xxx.com", 80));
        ByteBuffer buf = ByteBuffer.allocate(48);
        // read
        int bytesRead = socketChannel.read(buf);
        // write
        buf.clear();
        String newData = "New Data";
        buf.put(newData.getBytes());
        buf.flip();

        socketChannel.close();

        /* Non-blocking Mode */
        // socketChannel.configureBlocking(false);
        // socketChannel.connect(new InetSocketAddress("http://xxx.com", 80));

        // while(! socketChannel.finishConnect() ){
        //     // wait, or do something else...    
        // }
    }
}


// NIO ServerSocketChannel
public class TcpServer {
    public static void main(String[] args) throws IOException {
        // https://jenkov.com/tutorials/java-nio/server-socket-channel.html
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            // do something with socketChannel...
        }

        /* Non-blocking Mode */
        // ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        // serverSocketChannel.configureBlocking(false);

        // while (true) {
        //     SocketChannel socketChannel = serverSocketChannel.accept();
        //     if (socketChannel != null) {
        //         // do something with socketChannel...
        //     }
        // }
    }
}
