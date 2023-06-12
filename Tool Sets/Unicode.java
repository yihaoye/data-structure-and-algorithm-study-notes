// https://www.liaoxuefeng.com/wiki/1016959663602400/1017075323632896
// 最早只有 127 个字符被编码到计算机里，也就是大小写英文字母、数字和一些符号，这个编码表被称为 ASCII 编码
// 但是各国语言还有很多字符，Unicode 把所有语言都统一到一套编码里，形成标准。
// ASCII 编码是 1 个字节，而 Unicode 编码通常是 2 个字节。
// 但是，如果写的文本基本上全部是英文的话，用 Unicode 编码比 ASCII 编码需要多一倍的存储空间，在存储和传输上就十分不划算。
// 所以又出现了把 Unicode 编码转化为 “可变长编码” 的 UTF-8 编码。
// UTF-8 编码把一个 Unicode 字符根据不同的数字大小编码成 1-6 个字节，常用的英文字母被编码成 1 个字节，汉字通常是 3 个字节，只有很生僻的字符才会被编码成 4-6 个字节。
// 如果要传输的文本包含大量英文字符，用 UTF-8 编码就能节省空间（通常在操作系统里的如 txt 文本都是以 UTF-8 保存数据的）



// https://www.liaoxuefeng.com/wiki/1252599548343744/1260469698963456

// 在 Java 中，char 类型实际上就是两个字节的 Unicode 编码。如果要手动把字符串转换成其他编码，可以这样做：
byte[] b1 = "Hello".getBytes(); // 按系统默认编码转换，不推荐
byte[] b2 = "Hello".getBytes("UTF-8"); // 按 UTF-8 编码转换
byte[] b3 = "Hello".getBytes("GBK"); // 按 GBK 编码转换
byte[] b4 = "Hello".getBytes(StandardCharsets.UTF_8); // 按 UTF-8 编码转换
// 注意：转换编码后，就不再是 char 类型，而是 byte 类型表示的数组。

// 如果要把已知编码的 byte[] 转换为 String，可以这样做：
byte[] b = ...
String s1 = new String(b, "GBK"); // 按 GBK 转换
String s2 = new String(b, StandardCharsets.UTF_8); // 按 UTF-8 转换

// 始终牢记：Java 的 String 和 char 在内存中总是以 Unicode 编码表示。



// https://jenkov.com/tutorials/java-internationalization/unicode.html
// Converting to and from Unicode UTF-8 Using the String Class
byte[] bytes = new byte[10];
String str = new String(bytes, Charset.forName("UTF-8"));
System.out.println(str);

byte[] bytes2 = str.getBytes(Charset.forName("UTF-8"));

// Converting to and from Unicode UTF-8 Using the Reader and Writer Classes
InputStream inputStream = new FileInputStream("c:\\data\\utf-8-text.txt");
Reader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
int data = reader.read();
while (data != -1) {
    char theChar = (char) data;
    data = reader.read();
}
reader.close();

OutputStream outputStream = new FileOutputStream("c:\\data\\output.txt");
Writer writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
writer.write("Hello World");
writer.close();
