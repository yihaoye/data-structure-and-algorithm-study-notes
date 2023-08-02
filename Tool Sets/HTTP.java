// HTTP GET 示例 by Copilot
import java.net.*;
import java.util.*;
import java.io.*;

public class HTTP {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://www.google.com"); // or "https://api.coindesk.com/v1/bpi/currentprice.json"
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        int responseCode = conn.getResponseCode(); // 没有显示调用 connect()，因为 getResponseCode() 会隐式调用 connect()。
        StringBuilder data = new StringBuilder();
        if (responseCode != 200) { // HttpURLConnection.HTTP_OK
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }
        InputStream inputStream = conn.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            data.append(line);
        }
        bufferedReader.close();
        conn.disconnect();
        System.out.println(data.toString());
    }
}


// HTTP POST 示例 by ChatGPT
import java.net.*;
import java.util.*;
import java.io.*;

public class HTTP {

    public static void main(String[] args) {
        String url = "https://example.com/api/endpoint";
        String requestBody = "{\"key\":\"value\"}"; // 请求体，根据实际情况填充

        try {
            String response = sendHttpPostRequest(url, requestBody);
            System.out.println("Response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String sendHttpPostRequest(String url, String requestBody) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder responseContent = new StringBuilder();

        try {
            URL apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();

            // 设置请求方法为POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json"); // 根据实际情况设置请求头
            connection.setDoOutput(true); // 允许写入请求体

            // 写入请求体
            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }

            // 读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
        } finally {
            // 关闭连接和读取器
            if (connection != null) connection.disconnect();
            if (reader != null) reader.close();
        }

        return responseContent.toString();
    }
}
// 在try-with-resources结构中，只要实现了AutoCloseable接口的资源在try块中声明，无论是否发生异常，都会自动关闭该资源。在这个示例中，OutputStream和BufferedReader都实现了AutoCloseable接口，所以不需要手动调用close()方法。
// 在执行try-with-resources结构的时候，会自动在try块结束后关闭资源，保证资源的正确释放。因此，在这个例子中，OutputStream会在写入数据后自动被关闭，而BufferedReader会在读取响应后自动被关闭，无需显式调用close()方法。





// 可以参考 https://restful-api.dev/
import java.net.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;

import org.json.*;

public class HTTP { // 该类只展示了 GET 和 第三方库 JSON Parse 或简单的自定义 JSON Parse，POST 请下拉看另两个类
    public static String doGet(String endpoint) { // REST GET
        try {
            // https://www.youtube.com/watch?v=zZoboXqsCNw
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // 默认是 GET
            conn.setConnectTimeout(5000); // 设置连接超时时间，默认是无限等待，单位毫秒
            conn.setReadTimeout(5000); // 设置读取超时时间，默认是无限等待，单位毫秒
            conn.connect();

            int responseCode = conn.getResponseCode();
            // 200 OK
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }
            StringBuilder data = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                data.append(scanner.nextLine());
            }
            scanner.close();

            return data.toString(); // {"time":{"updated":"Feb 15, 2023 11:15:00 UTC","updatedISO":"2023-02-15T11:15:00+00:00","updateduk":"Feb 15, 2023 at 11:15 GMT"},"disclaimer":"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org","chartName":"Bitcoin","bpi":{"USD":{"code":"USD","symbol":"&#36;","rate":"22,242.3013","description":"United States Dollar","rate_float":22242.3013},"GBP":{"code":"GBP","symbol":"&pound;","rate":"18,585.4890","description":"British Pound Sterling","rate_float":18585.489},"EUR":{"code":"EUR","symbol":"&euro;","rate":"21,667.2488","description":"Euro","rate_float":21667.2488}}}
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String args[]) {
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        String data = doGet(url);
        if (data == null) {
            System.out.println("Error: data is null");
            return;
        }

        // 1. 使用 org.json 包解析 json - import org.json.*;
        JSONObject jsonObj = new JSONObject(data);
        String time = jsonObj.getString("time");

        // 2. 简陋的 json parse，不能处理复杂的嵌套关系只能简单解析出字符串并 flat，更完整的解析参考 Tool Sets 里的 JSONParser.java
        Map<String, String> json = new LinkedHashMap<>();
        Pattern pattern = Pattern.compile("\"([A-Za-z0-9]+)\":\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            json.put(matcher.group(1), matcher.group(2));
        }
        System.out.println(json); // {updated=Feb 16, 2023 08:29:00 UTC, updatedISO=2023-02-16T08:29:00+00:00, updateduk=Feb 16, 2023 at 08:29 GMT, disclaimer=This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org, chartName=Bitcoin, code=EUR, symbol=&euro;, rate=23,987.9543, description=Euro}
    }
}





// https://sqa.stackexchange.com/questions/47097/free-sites-for-testing-post-rest-api-calls
import java.net.*;
import java.util.*;
import javax.net.ssl.*;
import java.io.*;

public class HTTP { // HTTP/HTTPS POST
    public static void main(String[] args) {
        try {
            // https://stackoverflow.com/questions/40107482/parsing-json-file-in-java-without-using-org-json
            
            // URL url = new URL("https://httpbin.org/post");
            // HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            // httpConnection.setRequestMethod("POST");
            // httpConnection.setDoOutput(true);
            // OutputStream os = httpConnection.getOutputStream();
            // String jsonText = "{\"name\":\"AppleMacBookPro16\",\"data\":{\"year\":2019,\"price\":1849.99,\"CPUmodel\":\"IntelCorei9\",\"Harddisksize\":\"1TB\"}}";
            // os.write(jsonText.getBytes());
            // os.flush();
            // os.close();

            // int responseCode = httpConnection.getResponseCode();
            // System.out.println(responseCode);

            // if (responseCode == HttpURLConnection.HTTP_OK) {
            //     BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            //     String input;
            //     StringBuffer response = new StringBuffer();

            //     while ((input = br.readLine()) != null) {
            //         response.append(input);
            //     }
            //     br.close();
            //     String responseJson = response.toString();
            //     System.out.println(responseJson);
            // }

            String body = "{\"name\": \"Apple iPad Air\", \"data\": { \"Generation\": \"4th\", \"Price\": \"519.99\", \"Capacity\": \"256 GB\" }}";
            URL url = new URL("https://api.restful-api.dev/objects");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
                dos.writeBytes(body);
            }

            try (BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = bf.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}





// 链接：https://juejin.cn/post/7158840048970891278
/**
    在Java项目中调用第三方接口的方式有：
    ①通过JDK网络类Java.net.HttpURLConnection；
    ②通过common封装好的HttpClient；
    ③通过Apache封装好的HttpClient；由②发展来的
    ④通过SpringBoot-RestTemplate；
 */

import java.net.*;

public class HttpUrlConnectionToInterface { // 通过 JDK 网络类 Java.net.HttpURLConnection；

    /**
     * 以post或get方式调用对方接口方法，
     * @param pathUrl
     */
    public static void doPostOrGet(String pathUrl, String data){
        OutputStreamWriter out = null;
        BufferedReader br = null;
        String result = "";
        try {
            URL url = new URL(pathUrl);
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //请求方式
            conn.setRequestMethod("POST");
            //conn.setRequestMethod("GET");

            //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            //DoOutput设置是否向httpUrlConnection输出，DoInput设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            conn.setDoOutput(true);
            conn.setDoInput(true);

            /**
             * 下面的三句代码，就是调用第三方http接口
             */
            //获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            //发送请求参数即数据
            out.write(data);
            //flush输出流的缓冲
            out.flush();

            /**
             * 下面的代码相当于，获取调用第三方http接口后返回的结果
             */
            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
                result += str;
            }
            System.out.println(result);
            //关闭流
            is.close();
            //断开连接，disconnect是在底层tcp socket链接空闲时才切断，如果正在被其他线程使用就不切断。
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        /**
         *手机信息查询接口：http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=手机号
　　　　 *　　　　　　http://api.showji.com/Locating/www.showji.com.aspx?m=手机号&output=json&callback=querycallback
         */
        doPostOrGet("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13026194071", "");
    }
}





import java.net.URI;
import java.net.http.*;
import java.net.http.HttpClient.Version;
import java.time.Duration;
import java.util.*;

public class HTTP { // HTTP GET and POST
    // 全局 HttpClient:
    static HttpClient httpClient = HttpClient.newBuilder().build();

    public static void main(String[] args) {
        // ...
    }

    public static String doGet(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(new URI(url))
            .header("User-Agent", "Java HttpClient").header("Accept", "*/*") // 设置 Header:
            .timeout(Duration.ofSeconds(5)) // 设置超时:
            .version(Version.HTTP_2).build(); // 设置版本:
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        // Map<String, List<String>> headers = response.headers().map(); // HTTP 允许重复的 Header，因此一个 Header 可对应多个 Value:
        // for (String header : headers.keySet()) {
        //     System.out.println(header + ": " + headers.get(header).get(0));
        // }
        return response.body();
    }

    public static String doPost(String url, String body) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(new URI(url))
            .header("Accept", "*/*")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .timeout(Duration.ofSeconds(5))
            .version(Version.HTTP_2)
            .POST(BodyPublishers.ofString(body, StandardCharsets.UTF_8)).build(); // 使用 POST 并设置 Body:
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}