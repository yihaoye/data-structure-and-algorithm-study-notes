// https://www.cnblogs.com/czwbig/p/10035631.html
// ../Leetcode%20Practices/system%20design/IO模型与Web服务器工作模型.md

/*
NIO 由以下核心组件组成：

通道和缓冲区 - 在标准 IO API 中，使用字节流和字符流。在 NIO 中使用通道和缓冲区。数据总是从通道读入缓冲区，或从缓冲区写入通道。
非阻塞IO - NIO 可以执行非阻塞 IO。例如，当通道将数据读入缓冲区时，线程可以执行其他操作。并且一旦数据被读入缓冲区，线程就可以继续处理它。将数据写入通道也是如此。
选择器 - NIO 包含“选择器”的概念。选择器是一个可以监视多个事件通道的对象（例如：连接打开，数据到达等）。因此，单个线程可以监视多个通道的数据。

NIO 有比这些更多的类和组件，但 Channel，Buffer 和 Selector 构成了 API 的核心。其余的组件，如 Pipe 和 FileLock，只是与三个核心组件一起使用的实用程序类。

NIO 中的所有 IO 都以 Channel 开头。数据可以从 Channel 读入 Buffer，也可以从 Buffer 写入 Channel
有几种 Channel 和 Buffer ，以下是 NIO 中主要 Channel 实现类的列表，这些通道包括 UDP + TCP 网络 IO 和文件 IO：
    FileChannel ：文件通道
    DatagramChannel ：数据报通道
    SocketChannel ：套接字通道
    ServerSocketChannel ：服务器套接字通道

以下是 NIO 中主要 Buffer 实现类的列表：
    ByteBuffer
    CharBuffer
    DoubleBuffer
    FloatBuffer
    IntBuffer
    LongBuffer
    ShortBuffer

NIO 还有一个 MappedByteBuffer，它与内存映射文件一起使用，同样这个后续再讲。

Selectors 选择器选择器允许单个线程处理多个通道。如果程序打开了许多连接（通道），但每个连接只有较低的流量，使用选择器就很方便。
要使用选择器，需要使用它注册通道。 然后调用它的 select() 方法。 此方法将阻塞，直到有一个已注册通道的事件准备就绪。一旦该方法返回，该线程就可以处理这些事件。事件可以是传入连接，接收数据等。
*/



/*
什么时候应该使用 IO，什么时候应该使用 NIO?

NIO 允许仅使用一个（或几个）线程来管理多个通道（网络连接或文件），但成本是解析数据可能比从阻塞流中读取数据时更复杂一些。
如果需要同时管理数千个打开的连接，每个只发送一些数据，例如聊天服务器，这在 NIO 中实现服务器可能是一个优势。
同样，如果需要与其他计算机保持大量开放连接，例如，在 P2P 网络中，使用单个线程来管理所有出站连接可能是一个优势。
但如果拥有较少带宽的连接，一次连接的数据量较大，那么经典的 IO 服务器实现可能更合适的。
所以，应该根据具体的情况分析，选择更适合的，而不是更新的。
*/



public class ChannelExample {
    public static void main(String[] args) throws IOException {
	    // 文件内容是 123456789
        RandomAccessFile accessFile = new RandomAccessFile("D:\\test\\1.txt", "rw");
        FileChannel fileChannel = accessFile.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(48); //创建容量为48字节的缓冲区

        int data = fileChannel.read(buffer); // 将 Channel 的数据读入缓冲区，返回读入到缓冲区的字节数
        while (data != -1) {
            System.out.println("Read " + data); // Read 9
            buffer.flip(); // 将 buffer 从写入模式切换为读取模式
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get()); // 每次读取1byte，循环输出 123456789
            }
            buffer.clear(); // 清除当前缓冲区
            data = fileChannel.read(buffer); // 将 Channel 的数据读入缓冲区
        }
        accessFile.close();
    }
}
