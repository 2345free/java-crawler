package com.example.crawler.webmagic.es.repository;

import com.example.crawler.webmagic.es.document.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepository extends ElasticsearchRepository<Book, String> {

}
