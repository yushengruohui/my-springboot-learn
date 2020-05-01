package com.ys.elasticsearch.es.repository;

import com.ys.elasticsearch.es.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookRepository extends ElasticsearchRepository<Book, String> {
    List<Book> findByNameLike(String name, Pageable pageable);

    List<Book> findByNameMatches(String name, Pageable pageable);
}
