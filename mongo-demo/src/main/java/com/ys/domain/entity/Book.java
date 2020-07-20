package com.ys.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yusheng
 * Created on 2020-07-17 21:22
 **/
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id; //不能自动增长
    // private ObjectId id; //mongo-db自动增长的id类型
    private String name;
    private String pages;
    private String author;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreated;


    public Book() {
    }

    public Book(Long id, String name, String pages, String author, Date gmtCreated) {
        this.id = id;
        this.name = name;
        this.pages = pages;
        this.author = author;
        this.gmtCreated = gmtCreated;
    }

    public static BookBuilder builder() {
        BookBuilder bookBuilder = new BookBuilder();
        bookBuilder.book = new Book();
        return bookBuilder;
    }

    public static BookBuilder builder(Book book) {
        BookBuilder bookBuilder = new BookBuilder();
        bookBuilder.book = book == null ? new Book() : book;
        return bookBuilder;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPages() {
        return this.pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getGmtCreated() {
        return this.gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    @Override
    public String toString() {
        return "Book{id='" + id + "'" +
                ", name='" + name + "'" +
                ", pages='" + pages + "'" +
                ", author='" + author + "'" +
                ", gmtCreated='" + gmtCreated + "'" +
                "}";
    }

    public static class BookBuilder {

        private Book book;

        public BookBuilder id(Long id) {
            book.id = id;
            return this;
        }

        public BookBuilder name(String name) {
            book.name = name;
            return this;
        }

        public BookBuilder pages(String pages) {
            book.pages = pages;
            return this;
        }

        public BookBuilder author(String author) {
            book.author = author;
            return this;
        }

        public BookBuilder gmtCreated(Date gmtCreated) {
            book.gmtCreated = gmtCreated;
            return this;
        }

        public Book build() {
            return this.book;
        }

    }

}