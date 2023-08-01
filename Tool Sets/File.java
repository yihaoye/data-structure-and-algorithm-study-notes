// Method 1: URL class
URL url = new URL("file:/D:/test/test.txt");

URLConnection urlConnection = url.openConnection();
InputStream input = urlConnection.getInputStream();

int data = input.read();
while (data != -1) {
    System.out.printf((char) data);
    data = input.read();
}
input.close();


// Method 2: File class
File file = new File("file:/D:/test/test.txt");
Scanner reader = new Scanner(file);
while (reader.hasNextLine()) {
    String record = reader.nextLine();
    System.out.printf(record);
}
reader.close();


// Method 3: FileInputStream + Reader class
InputStream inputStream = new FileInputStream("file:/D:/test/test.txt");
try (Reader inputStreamReader = new InputStreamReader(inputStream)) {
    int data = inputStreamReader.read();
    while(data != -) {
        System.out.print((char) data));
        data = inputStreamReader.read();
    } 
} catch (IOException e) {
    e.printStackTrace();
}
// inputStreamReader.close(); // 不需要，因为 try with resources 会关闭流。同样，只要关闭最外层的包装流，里面的流会被系统自动关闭。


// Method 3.5: + BufferedReader class
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



// 写入文件
OutputStream outputStream = new FileOutputStream("file:/D:/test/test.txt");
try (Writer outputStreamWriter = new OutputStreamWriter(outputStream)) {
    outputStreamWriter.write("Hello World!");
} catch (IOException e) {
    e.printStackTrace();
}


// 写入文件 method 2
FileWriter fileWriter = new FileWriter("file:/D:/test/test.txt");
try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
    bufferedWriter.write("Hello World!");
} catch (IOException e) {
    e.printStackTrace();
}


// 写入文件 append mode
FileWriter fileWriter = new FileWriter("file:/D:/test/test.txt", true);
try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
    bufferedWriter.write("Append!");
} catch (IOException e) {
    e.printStackTrace();
}
