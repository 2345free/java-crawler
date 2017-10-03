package com.example.crawler.webmagic.test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * Created by luoxx on 2017/10/3.
 */
public class MoocProcessor implements PageProcessor {
    private Site site = new Site().setRetryTimes(3).setSleepTime(100)
            //添加cookie之前一定要先设置主机地址，否则cookie信息不生效
            .setDomain("www.imooc.com")
            //添加抓包获取的cookie信息
            .addCookie("Hm_lpvt_f0cfcccd7b1393990c78efdeebff3968", "1507005815")
            .addCookie("Hm_lvt_f0cfcccd7b1393990c78efdeebff3968", "1507005175")
            .addCookie("PHPSESSID", "3mp9mc5roradtk0utr8nfu0p23")
            .addCookie("apsid", "QzZDY3MmJkNTA4MGU5OTAwYWI0NDAwNzBkNWZmMmYAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMjgzNDYyNAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA0OTIyMjI5ODZAcXEuY29tAAAAAAAAAAAAAAAAAAAAADliYWEzOWRjNThlM2IxY2QyMmMzOTdkNzY3NTYwMzcxdhXTWXYV01k%3DOW")
            .addCookie("cvde", "59d314e4baa72-7")
            .addCookie("imooc_isnew", "1")
            .addCookie("imooc_isnew_ct", "1507005668")
            .addCookie("imooc_uuid", "131a7faf-0ae1-4fde-8257-a66857b2fd4b")
            .addCookie("last_login_username", "你的用户名")
            .addCookie("loginstate", "1")
            //添加请求头，有些网站会根据请求头判断该请求是由浏览器发起还是由爬虫发起的
            .addHeader("User-Agent",
                    "ozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36 Core/1.47.516.400 QQBrowser/9.4.8188.400")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
            .addHeader("Accept-Encoding", "gzip, deflate, sdch").addHeader("Accept-Language", "zh-CN,zh;q=0.8")
            .addHeader("Connection", "keep-alive").addHeader("Referer", "http://www.imooc.com/");

    @Override
    public void process(Page page) {
        // jsoup获取页面数据
//        Document doc = Jsoup.parse(page.getHtml().toString());
//        Elements infos = doc.getElementsByClass("info-box");
//        for (Element info : infos) {
//            String key = info.getElementsByTag("label").get(0).text();
//            String field = info.getElementsByTag("div").get(1).text();
//            page.putField(key, field);
//        }

        // xpath获取页面数据
        List<Selectable> nodes = page.getHtml().xpath("//div[@class='info-box']").nodes();
        for (Selectable node : nodes) {
            page.putField(node.xpath("//label/text()").toString(), node.xpath("//div[1]/text()").toString());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new MoocProcessor())
                // 从"http://www.imooc.com/user/setprofile"开始抓
                .addUrl("http://www.imooc.com/user/setprofile").addPipeline(new ConsolePipeline())
                // 开启5个线程抓取
                .thread(1)
                // 启动爬虫
                .run();
    }

}
