// 读取 Method 1: File + Scanner class - 是否推荐？推荐，因为它可以读取任何类型的文件。
File file = new File("file:/D:/test/test.txt"); // File 类只能读取文件的元数据，不能读取文件内容
try (Scanner reader = new Scanner(file)) {
    while (reader.hasNextLine()) {
        String record = reader.nextLine();
        System.out.printf(record);
    }
} catch (FileNotFoundException e) {
    e.printStackTrace();
}
// reader.close(); // 不需要，因为 try with resources 会关闭流。


// 读取 Method 2: FileInputStream + Reader class - 是否推荐？推荐，因为它可以读取任何类型的文件。
InputStream inputStream = new FileInputStream("file:/D:/test/test.txt");
try (Reader inputStreamReader = new InputStreamReader(inputStream)) {
    int data = inputStreamReader.read();
    while (data != -1) {
        System.out.print((char) data);
        data = inputStreamReader.read();
    } 
} catch (IOException e) {
    e.printStackTrace();
}
// inputStreamReader.close(); // 不需要，因为 try with resources 会关闭流。同样，只要关闭最外层的包装流，里面的流会被系统自动关闭。


// 读取 Method 2.5: + BufferedReader class - 是否推荐？推荐，因为它可以读取任何类型的文件，而且可以一次读取一行。
InputStream inputStream = new FileInputStream("file:/D:/test/test.txt");
try (Reader inputStreamReader = new InputStreamReader(inputStream);
     BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
    String line = bufferedReader.readLine();
    while (line != null) {
        System.out.println(line);
        line = bufferedReader.readLine();
    }
} catch (IOException e) {
    e.printStackTrace();
}


// 读取 Method 3: RandomAccessFile，意思是可以随机访问文件的任何位置，而不是从头开始读取。
RandomAccessFile randomAccessFile = new RandomAccessFile("file:/D:/test/test.txt", "r"); // 参数 2：r 代表只读，rw 代表读写，rws 代表读写并同步文件内容，rwd 代表读写并同步文件内容和元数据。
try {
    String line = randomAccessFile.readLine();
    while (line != null) {
        System.out.println(line);
        line = randomAccessFile.readLine();
    }
} catch (IOException e) {
    e.printStackTrace();
} finally {
    randomAccessFile.close();
}


// 读取 Method 4: Path + Files + Stream class - 是否推荐？推荐，因为它可以读取任何类型的文件。
Path path = Paths.get("file:/D:/test/test.txt");
try (Stream<String> lines = Files.lines(path)) {
    lines.forEach(System.out::println);
} catch (IOException e) {
    e.printStackTrace();
}


// 读取 Method 5: URL class - 是否推荐？不推荐，因为 URL 类是为了访问网络资源而设计的，而不是本地文件。
URL url = new URL("file:/D:/test/test.txt");
URLConnection urlConnection = url.openConnection();
InputStream input = urlConnection.getInputStream();
int data = input.read();
while (data != -1) {
    System.out.printf((char) data);
    data = input.read();
}
input.close();



// 写入文件
OutputStream outputStream = new FileOutputStream("file:/D:/test/test.txt"); // 如果文件不存在，会自动创建文件。参数 2：true 代表 append mode，false 代表 overwrite mode，默认是 false。
try (Writer outputStreamWriter = new OutputStreamWriter(outputStream)) {
    outputStreamWriter.write("Hello World!");
} catch (IOException e) {
    e.printStackTrace();
}


// 写入文件 append mode
OutputStream outputStream = new FileOutputStream("file:/D:/test/test.txt", true);
try (Writer outputStreamWriter = new OutputStreamWriter(outputStream)) {
    outputStreamWriter.write("Append!");
} catch (IOException e) {
    e.printStackTrace();
}


// 写入文件 buffer + append - Buffer 好在哪里？Buffer 可以提高写入文件的效率。
OutputStream outputStream = new FileOutputStream("file:/D:/test/test.txt", true);
try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream))) {
    bufferedWriter.write("Append!");
} catch (IOException e) {
    e.printStackTrace();
}



// 通常推荐使用 InputStream/OutputStream 和 Reader/Writer 类，因为它们可以读取和写入任何类型的文件。
// 但是，如果读取和写入文本文件，推荐使用 FileReader/FileWriter 类 或 Scanner/PrintWriter 类，因为它们的使用更加简单，但是这些个类只能读取和写入文本文件，不能读取和写入二进制文件。

// PrintWriter 类
PrintWriter printWriter = new PrintWriter("file:/D:/test/test.txt"); // 参数 2：true 代表 append mode，false 代表 overwrite mode，默认是 false。
printWriter.println("Hello World!");
printWriter.close();
