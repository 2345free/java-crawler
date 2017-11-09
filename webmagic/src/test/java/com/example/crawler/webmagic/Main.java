/**
 * Copyright (C), 2017-2017, 帮5采
 * FileName: Main
 * Author:   tianyi
 * Date:     2017/9/30 17:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.crawler.webmagic;

import com.example.crawler.webmagic.utils.HttpUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author tianyi
 * @create 2017/9/30
 * @since 1.0.0
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // https://github.com/code4craft/webmagic

        csdn();

    }

    private static void csdn2() throws Exception {

        // 查看CSDN登陆页面源码发现登陆时需要post5个参数
        // name、password，另外三个在页面的隐藏域中，a good start
        // 这样登陆不行，因为第一次需要访问需要拿到上下文context
        // Document doc = Jsoup.connect(LOGIN_URL).get();
        String LOGIN_URL = "https://passport.csdn.net/account/login";
        String html = HttpUtils.request(new HttpGet(LOGIN_URL));
        Document doc = Jsoup.parse(html);
        Element form = doc.select(".user-pass").get(0);

        List<BasicNameValuePair> nvps = new ArrayList<>();
        URI uri = new URIBuilder(LOGIN_URL)
                .addParameter("username", "492222986@qq.com")
                .addParameter("password", "WzMm091208")
                .addParameter("lt", form.select("input[name=lt]").get(0).val())
                .addParameter("execution", form.select("input[name=execution]").get(0).val())
                .addParameter("_eventId", form.select("input[name=_eventId]").get(0).val()).build();

        String ret = HttpUtils.request(new HttpPost(uri));

        if (ret.indexOf("redirect_back") > -1) {
            System.err.println("登陆成功");
        } else if (ret.indexOf("登录太频繁") > -1) {
            System.err.println("登录太频繁，请稍后再试");
        } else {
            System.err.println("登陆失败");
        }

    }


    private static void csdn() throws Exception {

        CookieStore cookieStore = new BasicCookieStore();

        HttpClientContext context = new HttpClientContext();

        CloseableHttpClient client = HttpClients
                .custom()
                .setDefaultRequestConfig(RequestConfig.custom()//
                        .setSocketTimeout(5000)//
                        .setConnectTimeout(5000)//
                        .build())
                .setDefaultCookieStore(cookieStore)
                .build();

        final String loginUrl = "https://passport.csdn.net/account/login";

        // 模拟打开登陆页面,获取登陆参数lt,execution,_eventId及cookie
        CloseableHttpResponse step1 = client.execute(new HttpGet(loginUrl), context);
        String loginHtml = EntityUtils.toString(step1.getEntity(), "UTF-8");
        Document loginDoc = Jsoup.parse(loginHtml);
        Elements elements = loginDoc.select("input[name=\"lt\"],input[name=\"execution\"],input[name=\"_eventId\"]");
        Map<String, String> pageParams = new HashMap<>();
        for (Element ele : elements) {
            pageParams.put(ele.attr("name"), ele.attr("value"));
        }

        URI uri = new URIBuilder(loginUrl)
                .addParameter("username", "492222986@qq.com")
                .addParameter("password", "WzMm091208")
                // 需从登陆页面中爬取
                .addParameter("lt", pageParams.get("lt"))
                .addParameter("execution", pageParams.get("execution"))
                .addParameter("_eventId", pageParams.get("_eventId"))
                .build();

        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(new StringEntity(uri.getQuery()));

        CloseableHttpResponse response = client.execute(httpPost, context);
        String ret = EntityUtils.toString(response.getEntity());
        System.out.println(ret);

        if (ret.indexOf("redirect_back") > -1) {
            System.err.println("登陆成功");
        } else if (ret.indexOf("登录太频繁") > -1) {
            System.out.println("登录太频繁，请稍后再试");
        } else {
            System.out.println("登陆失败");
        }

//        CloseableHttpResponse my = client.execute(new HttpGet("http://my.csdn.net/"), context);
//        System.out.println(EntityUtils.toString(my.getEntity()));

    }

}