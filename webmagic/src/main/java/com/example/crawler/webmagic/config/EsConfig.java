package com.example.crawler.webmagic.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tianyi
 */
@Configuration
public class EsConfig {

    @Bean
    public void transportClient() {

        RestClient lowLevelRestClient = RestClient.builder(
                new HttpHost("cluster", 9200, "http"),
                new HttpHost("cluster", 9201, "http"),
                new HttpHost("cluster", 9202, "http"))
                .build();

        RestHighLevelClient client =
                new RestHighLevelClient(lowLevelRestClient);

    }


}
