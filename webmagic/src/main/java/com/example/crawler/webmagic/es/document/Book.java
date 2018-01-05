package com.example.crawler.webmagic.es.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;


/**
 * @author tianyi
 */
@Data
@Document(indexName = "book", type = "novel", shards = 2, replicas = 1)
public class Book {

    @Id
    private String id;

    private Long wordCount;

    private String title;

    private String author;

    private Date publishDate;

}
