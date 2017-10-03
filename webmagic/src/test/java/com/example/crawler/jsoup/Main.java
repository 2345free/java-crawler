/**
 * Copyright (C), 2017-2017, 帮5采
 * FileName: Main
 * Author:   tianyi
 * Date:     2017/9/30 17:33
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.crawler.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.io.IOException;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author tianyi
 * @create 2017/9/30
 * @since 1.0.0
 */
public class Main {
    public static void main(String[] args) throws IOException {

        // http://blog.csdn.net/u010814849/article/details/52526582
        // https://www.ibm.com/developerworks/cn/java/j-lo-jsouphtml/
        // http://www.open-open.com/jsoup/

        // 直接从字符串中输入 HTML 文档
//        String html = "<html><head><title> 开源中国社区 </title></head>"
//                + "<body><p> 这里是 jsoup 项目的相关文章 </p></body></html>";
//        Document doc = Jsoup.parse(html);
//        System.out.println(doc);
//
//        // 从 URL 直接加载 HTML 文档
        Document doc2 = Jsoup.connect("http://www.oschina.net/").get();
//        String title = doc.title();
//        System.out.println(doc2);
//
//        Document doc3 = Jsoup.connect("http://www.oschina.net/")
//                .data("query", "Java")   // 请求参数
//                .userAgent("I ’ m jsoup") // 设置 User-Agent
//                .cookie("auth", "token") // 设置 cookie
//                .timeout(3000)           // 设置连接超时时间
//                .post();
//        System.out.println(doc3);

//
//        Element content = doc.getElementById("v_news");
////        Elements links = content.getElementsByTag("a");
//        Elements links = content.select("a[href]");
//        for (Element link : links) {
//            String linkHref = link.attr("href");
//            String linkText = link.text();
//            System.out.println(linkHref);
//        }

        String unsafe = "<p><a href='http://www.oschina.net/' onclick='stealCookies()'>开源中国社区 </a></p>";
        String safe = Jsoup.clean(unsafe, Whitelist.basicWithImages());
        System.out.println(safe);

    }
}