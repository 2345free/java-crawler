package com.example.crawler.webmagic.config;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.sniff.Sniffer;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tianyi
 */
@Configuration
public class EsConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient() {

        RestClientBuilder builder = RestClient.builder(
                new HttpHost("cluster", 9200, "http")/*,
                new HttpHost("cluster", 9201, "http"),
                new HttpHost("cluster", 9202, "http")*/);

        Header[] defaultHeaders = new Header[]{
                new BasicHeader("content-type", XContentType.JSON.mediaType())
        };
        builder.setDefaultHeaders(defaultHeaders); // 设置默认头文件，避免每个请求都必须指定。

        /**
         * 设置在同一请求进行多次尝试时应该遵守的超时时间。默认值为30秒，与默认`socket`超时相同。
         * 如果自定义设置了`socket`超时，则应该相应地调整最大重试超时。
         */
        builder.setMaxRetryTimeoutMillis(10 * 1000);

        RestClient restClient = builder.build();

        Sniffer sniffer = Sniffer.builder(restClient)
                // The Sniffer updates the nodes by default every 5 minutes
                .setSniffIntervalMillis(30 * 1000).build();

        RestHighLevelClient client = new RestHighLevelClient(restClient);
        return client;
    }


}
