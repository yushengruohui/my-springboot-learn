package com.ys.elasticsearch;

import com.ys.elasticsearch.es.entity.Book;
import com.ys.elasticsearch.es.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class ElasticsearchDemoApplicationTests {
    @Resource
    private BookRepository bookRepository;

    @Test
    void demo() {
        Book book = new Book();
        book.setId("1");
        book.setName("测试之书");
        book.setPages("3");
        int currentPage = 1;
        int pageSize = 10;
        List<Book> name = bookRepository.findByNameLike("美人", PageRequest.of(currentPage - 1, pageSize));
        System.out.println("Book = " + name);
    }

}
