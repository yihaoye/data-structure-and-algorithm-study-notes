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
