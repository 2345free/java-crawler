package com.example.crawler.webmagic.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Created by luoxx on 2017/10/2.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {

    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static String request(HttpRequestBase http) throws IOException {
        try {
            CloseableHttpResponse response = httpClient.execute(http);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return content;
        } finally {
            http.releaseConnection();
        }
    }

    public static String postJson(HttpPost httpPost, String jsonParam) throws IOException {
        try {
            httpPost.addHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
            HttpEntity entity = new StringEntity(jsonParam, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
            }
        } finally {
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
        }
        return null;
    }

    public static String postXml(String url, String xml) throws IOException {
        HttpPost httpPost = new HttpPost();
        try {
            String defaultCharset = "UTF-8";
            httpPost.setURI(new URI(url));
            HttpEntity httpEntity = new StringEntity(xml);
            httpPost.addHeader("accept", "*/*");
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.addHeader("connection", "Keep-Alive");
            httpPost.addHeader("charset", defaultCharset);
            httpPost.setEntity(httpEntity);

            CloseableHttpResponse response = httpClient.execute(httpPost);

            return EntityUtils.toString(response.getEntity(), defaultCharset);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        return null;
    }

    public static String postXmlJdk(String url, String param, String charset) throws Exception {

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 设置本地代理,抓包测试用
//            Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1", 8888));
//            URLConnection conn = realUrl.openConnection(proxy);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-Type", "text/xml");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("charset", charset);
            conn.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream(), true);
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


}
