package com.example.crawler.webmagic.es.document;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;


/**
 * @author tianyi
 */
@Data
public class Book {

    @Id
    private String id;

    private Long wordCount;

    private String title;

    private String author;

    private Date publishDate;

}
