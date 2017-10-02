package com.example.crawler.webmagic.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by luoxx on 2017/10/2.
 */
public class CompanyProcessor implements PageProcessor {


    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(3000)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");


    public void process(Page page) {
        // 需要把resources\static\chromedriver.exe放在C:\Windows\System32目录下
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.sse.com.cn/assortment/stock/list/info/company/index.shtml?COMPANY_CODE=600000");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement webElement = driver.findElement(By.id("tableData_stockListCompany"));
//        WebElement webElement = driver.findElement(By.xpath("//div[@class='table-responsive sse_table_T05']"));
        String str = webElement.getAttribute("outerHTML");
        System.out.println(str);

        Html html = new Html(str);
        System.out.println(html.xpath("//tbody/tr").all());
        String companyCode = html.xpath("//tbody/tr[1]/td/text()").get();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = html.xpath("//tbody/tr[3]/td/text()").get().split("/")[0];

        String stockCode = html.xpath("//tbody/tr[2]/td/text()").get().split("/")[0];
        String name = html.xpath("//tbody/tr[5]/td/text()").get().split("/")[0];
        String department = html.xpath("//tbody/tr[14]/td/text()").get().split("/")[0];
        System.out.println(companyCode);
        System.out.println(stockCode);
        System.out.println(name);
        System.out.println(department);
        driver.close();

    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new CompanyProcessor())
                .addUrl("http://www.sse.com.cn/assortment/stock/list/info/company/index.shtml?COMPANY_CODE=600000")
                .thread(5)
                .run();
    }
}
