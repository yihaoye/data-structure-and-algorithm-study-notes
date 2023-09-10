// 系统学习 Java IO - https://www.cnblogs.com/czwbig/p/10007201.html
// Java IO 目的和功能
// Java IO 包含 InputStream，OutputStream，Reader 和 Writer 类的许多子类。 原因是，所有这些子类都在解决各种不同的目的。 所涉及的目的总结如下：
//     * 网络访问
//     * 内部缓冲区访问
//     * 线程间通信（管道）
//     * 缓冲
//     * 过滤
//     * 解析
//     * 读写文本（Reader/Writer）
//     * 读写基本类型数据（long，int等）
//     * 读写对象

// Java IO 类概述表
// 在讨论了 Java IO 类所针对的源，目标，输入，输出和各种 IO 目的之后，这里列出了大多数（不是全部）Java IO 类除以输入，输出，基于字节或基于字符的任何目的，以及任何他们可能正在解决的更具体的目的，如缓冲，解析等。
// 数据类型	            基于字节的 Input	                      基于字节的 Output	                        基于字符的 Input	                     基于字符的 Output
// 基础	                InputStream	                            OutputStream	                        Reader 、 InputStreamReader	            Writer、OutputStreamWriter
// 数组	                ByteArrayInputStream	                ByteArrayOutputStream	                CharArrayReader	                        CharArrayWriter
// Files	           FileInputStream、RandomAccessFile	    FileOutputStream、RandomAccessFile	    FileReader	                            FileWriter
// 管道	                PipedInputStream	                    PipedOutputStream	                    PipedReader	                            PipedWriter
// 缓冲	                BufferedInputStream	                    BufferedOutputStream	                BufferedReader	                        BufferedWriter
// 过滤	                FilterInputStream	                    FilterOutputStream	                    FilterReader	                        FilterWriter
// 解析	                PushbackInputStream、StreamTokenizer		                                    PushbackReader、LineNumberReader	
// 字符串			                                                                                    StringReader	                        StringWriter
// 数据	                DataInputStream	                        DataOutputStream		
// 数据 - 格式化		                                         PrintStream		                                                             PrintWriter
// 对象	                ObjectInputStream	                    ObjectOutputStream		
// 组合多个流	         SequenceInputStream	



// https://www.liaoxuefeng.com/wiki/1252599548343744/1298366845681698
/*
序列化是指把一个 Java 对象变成二进制内容，本质上就是一个 byte[] 数组。
为什么要把 Java 对象序列化呢？因为序列化后可以把 byte[] 保存到文件中，或者把 byte[] 通过网络传输到远程，这样，就相当于把 Java 对象存储到文件或者通过网络传输出去了。
有序列化，就有反序列化，即把一个二进制内容（也就是 byte[] 数组）变回 Java 对象。有了反序列化，保存到文件中的 byte[] 数组又可以“变回” Java 对象，或者从网络上读取 byte[] 并把它“变回” Java 对象。
来看看如何把一个 Java 对象序列化。
一个 Java 对象要能序列化，必须实现一个特殊的 java.io.Serializable 接口，它的定义如下：
*/
public interface Serializable {
}
// Serializable 接口没有定义任何方法，它是一个空接口。这样的空接口称为“标记接口”（Marker Interface），实现了标记接口的类仅仅是给自身贴了个“标记”，并没有增加任何方法。
// Serializable 接口告诉 Java 运行时系统该类可以被序列化和反序列化。序列化过程会将对象的字段按顺序转换为字节流，并将其保存到文件。反序列化过程会从文件中读取字节流并还原为对象。通过明确指定 serialVersionUID 字段，可以确保类的版本兼容性。
// 类的方法本身不会被序列化和反序列化。对象的方法行为是由类定义的，如果类在反序列化后没有发生变化，那么对象的方法行为应该保持不变。
import java.io.*;
class Data implements Serializable { // 创建一个可序列化的类
    private static final long serialVersionUID = 1L;
    private int value;
    private String description;
    // private List<Item> items; // 从属变量如果是基于自定义的类也可以一并被序列化和反序列化（下面的 Test），但是它们也必须实现 Serializable 接口

    public Data(int value, String description) {
        this.value = value;
        this.description = description;
    }
}

public class Test {
    public static void main(String[] args) {
        Data data = new Data(10, "This is some data."); // 创建一个 Data 对象
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.ser"))) {
            oos.writeObject(data); // 将 Data 对象序列化到文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.ser"))) {
            Data restoredData = (Data) ois.readObject(); // 从文件中反序列化 Data 对象
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
/*
虽然 Java 中有内置的序列化机制（Serializable），但也存在一些情况下，开发者可能会选择使用第三方序列化工具（如 Protocol Buffers，简称 Protobuf）而不是 Java 默认的序列化机制，这是因为这两种方式在某些方面有不同的优点和用途：
1. 性能和效率：Protobuf 通常比 Java 默认的序列化机制更高效。Protobuf 使用二进制编码，因此产生的序列化数据通常更小，序列化和反序列化的速度更快。这对于需要高性能的应用程序（如分布式系统、网络通信）非常有用。
2. 跨语言支持：Protobuf 是一种语言无关的序列化格式，它支持多种编程语言（不仅限于 Java）。这意味着你可以使用 Protobuf 将数据序列化为二进制格式，然后在不同编程语言之间进行交换，而无需担心兼容性问题。
3. 版本控制：Java 默认的序列化机制对类的版本变化非常敏感，这可能导致在反序列化时出现问题。Protobuf 允许更灵活的版本控制，因此你可以更容易地处理类的演化和变化。
4. 数据格式控制：Protobuf 允许你更精确地控制序列化后的数据格式，包括字段的顺序、可选性和默认值等。这使得你可以更好地管理和维护数据的格式。

尽管 Protobuf 等第三方序列化工具具有这些优点，但它们也需要开发者定义数据结构的消息描述，并使用特定的编译器生成与语言相关的代码。这与 Java 默认的序列化机制不同，后者会自动将对象序列化为字节流，无需额外的描述文件。
因此，选择使用哪种序列化方式通常取决于应用程序的需求。对于简单的应用程序，Java 默认的序列化机制可能已经足够了。但对于需要高性能、跨语言支持或更精确数据控制的应用程序，第三方序列化工具如 Protobuf 可能是更好的选择。
*/



// 序列化
// 把一个Java对象变为byte[]数组，需要使用ObjectOutputStream。它负责把一个Java对象写入一个字节流：
import java.io.*;
import java.util.Arrays;
public class Test {
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (ObjectOutputStream output = new ObjectOutputStream(buffer)) {
            // 写入int:
            output.writeInt(12345);
            // 写入String:
            output.writeUTF("Hello");
            // 写入Object:
            output.writeObject(Double.valueOf(123.456));
        }
        System.out.println(Arrays.toString(buffer.toByteArray()));
    }
}
// ObjectOutputStream既可以写入基本类型，如int，boolean，也可以写入String（以UTF-8编码），还可以写入实现了Serializable接口的Object。
// 因为写入Object时需要大量的类型信息，所以写入的内容很大。

// 反序列化
// 和ObjectOutputStream相反，ObjectInputStream负责从一个字节流读取Java对象：
try (ObjectInputStream input = new ObjectInputStream(...)) {
    int n = input.readInt();
    String s = input.readUTF();
    Double d = (Double) input.readObject();
}
// 除了能读取基本类型和String类型外，调用readObject()可以直接返回一个Object对象。要把它变成一个特定类型，必须强制转型。

/*
readObject()可能抛出的异常有：
    * ClassNotFoundException：没有找到对应的Class；
    * InvalidClassException：Class不匹配。
对于ClassNotFoundException，这种情况常见于一台电脑上的Java程序把一个Java对象，例如，Person对象序列化以后，通过网络传给另一台电脑上的另一个Java程序，但是这台电脑的Java程序并没有定义Person类，所以无法反序列化。
对于InvalidClassException，这种情况常见于序列化的Person对象定义了一个int类型的age字段，但是反序列化时，Person类定义的age字段被改成了long类型，所以导致class不兼容。

为了避免这种class定义变动导致的不兼容，Java的序列化允许class定义一个特殊的serialVersionUID静态变量，用于标识Java类的序列化“版本”，通常可以由IDE自动生成。如果增加或修改了字段，可以改变serialVersionUID的值，这样就能自动阻止不匹配的class版本：
*/
public class Person implements Serializable {
    private static final long serialVersionUID = 2709425275741743919L;
}
// 要特别注意反序列化的几个重要特点：
// 反序列化时，由JVM直接构造出Java对象，不调用构造方法，构造方法内部的代码，在反序列化时根本不可能执行。



/*
安全性
因为Java的序列化机制可以导致一个实例能直接从byte[]数组创建，而不经过构造方法，因此，它存在一定的安全隐患。一个精心构造的byte[]数组被反序列化后可以执行特定的Java代码，从而导致严重的安全漏洞。
实际上，Java本身提供的基于对象的序列化和反序列化机制既存在安全性问题，也存在兼容性问题。更好的序列化方法是通过JSON这样的通用数据结构来实现，只输出基本类型（包括String）的内容，而不存储任何与代码相关的信息。
*/

/*
总结
可序列化的Java对象必须实现java.io.Serializable接口，类似Serializable这样的空接口被称为“标记接口”（Marker Interface）；
反序列化时不调用构造方法，可设置serialVersionUID作为版本号（非必需）；
Java的序列化机制仅适用于Java，如果需要与其它语言交换数据，必须使用通用的序列化方法，例如JSON。
*/

