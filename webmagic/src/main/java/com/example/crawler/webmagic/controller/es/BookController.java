package com.example.crawler.webmagic.controller.es;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by luoxx on 2018/1/7.
 */
@RequestMapping("/es/book")
@RestController
public class BookController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/get/{id}")
    public Object get(@PathVariable String id) {
        GetRequest getRequest = new GetRequest("book", "novel", "1");
        try {
            GetResponse getResponse = restHighLevelClient.get(getRequest);
            return getResponse.getSource();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
