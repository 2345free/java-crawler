package com.example.crawler.webmagic.controller;

import com.example.crawler.webmagic.es.document.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @GetMapping("/book/list")
    public ResponseEntity list() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .build();

        AggregatedPage<Book> books = esTemplate.queryForPage(searchQuery, Book.class);
        return new ResponseEntity(books.getContent(), HttpStatus.OK);
    }

}
