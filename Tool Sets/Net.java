// 可以参考 https://restful-api.dev/
import java.net.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;

public class Net { // 该类只展示了 GET 和 第三方库 JSON Parse 或简单的自定义 JSON Parse，POST 请下拉看另两个类
    public static void main(String args[]) {
      try {
            // https://www.youtube.com/watch?v=zZoboXqsCNw

            URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder data = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    data.append(scanner.nextLine());
                }
                // Close the scanner
                scanner.close();

                System.out.println(data); // {"time":{"updated":"Feb 15, 2023 11:15:00 UTC","updatedISO":"2023-02-15T11:15:00+00:00","updateduk":"Feb 15, 2023 at 11:15 GMT"},"disclaimer":"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org","chartName":"Bitcoin","bpi":{"USD":{"code":"USD","symbol":"&#36;","rate":"22,242.3013","description":"United States Dollar","rate_float":22242.3013},"GBP":{"code":"GBP","symbol":"&pound;","rate":"18,585.4890","description":"British Pound Sterling","rate_float":18585.489},"EUR":{"code":"EUR","symbol":"&euro;","rate":"21,667.2488","description":"Euro","rate_float":21667.2488}}}
                
                // 1. 使用 org.json 包解析 json - import org.json.*;
                JSONObject jsonObj = new JSONObject(str);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





// https://sqa.stackexchange.com/questions/47097/free-sites-for-testing-post-rest-api-calls
import java.net.*;
import java.util.*;
import javax.net.ssl.*;
import java.io.*;

public class Net {
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

public class HttpUrlConnectionToInterface { // 通过JDK网络类Java.net.HttpURLConnection；

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
            while ((str = br.readLine()) != null){
                result += str;
            }
            System.out.println(result);
            //关闭流
            is.close();
            //断开连接，disconnect是在底层tcp socket链接空闲时才切断，如果正在被其他线程使用就不切断。
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null){
                    out.close();
                }
                if (br != null){
                    br.close();
                }
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
