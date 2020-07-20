package com.ys.controller;

import com.mongodb.client.result.UpdateResult;
import com.ys.domain.entity.Book;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Pattern;


/**
 * 学习mongoDB的测试类
 *
 * @author yusheng
 * Created on 2020-07-17 21:18
 **/
@RestController
@RequestMapping("/mongo")
public class MongoController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MongoController.class);
    /*
     * 推荐每次操作都指定集合名，防止子类错误，提高执行效率。
     */
    private static String COLLECTION_NAME = "book";
    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 插入文档，注意，必须指定id，否则报错。
     * 如果是自动增长类型的主键，可以不指定id则插入文档，指定id，已存在则报错。
     *
     * @param book 文档对象
     */
    @PostMapping("insert")
    public void post2(Book book) {
        log.info("插入book集合的一个文档");
        Book book1 = mongoTemplate.insert(book, COLLECTION_NAME);
        log.info(">>>> book1 = " + book1 + " <<<<");
    }

    /**
     * 保存一个文档，注意，必须指定id[非 ObjectId 类型的主键，不能自动增长]，否则报错。
     * 如果是自动增长类型的主键，可以不指定id则插入文档，指定id，已存在则更新，不存在则插入。
     *
     * @param book 文档对象
     */
    @PostMapping("save")
    public void post(Book book) {
        log.info("插入book集合的一个文档");
        Book book1 = mongoTemplate.save(book, COLLECTION_NAME);
        log.info(">>>> book1 = " + book1 + " <<<<");
    }

    @DeleteMapping
    public void delete(Book book) {
        log.info("删除一个文档");
        mongoTemplate.remove(Book.class);
        log.info(">>>> book = " + book + " <<<<");
    }

    @GetMapping
    public void get(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        // 获取集合中一个符合查询条件的文档
        Book book1 = mongoTemplate.findOne(query, Book.class, COLLECTION_NAME);
        log.info(">>>> book1 = " + book1 + " <<<<");
        // 获取集合中所有符合查询条件的文档
        List<Book> book2 = mongoTemplate.find(query, Book.class, COLLECTION_NAME);
        log.info(">>>> book2 = " + book2 + " <<<<");
        // 获取集合中的所有文档
        List<Book> book3 = mongoTemplate.findAll(Book.class);
        log.info(">>>> book3 = " + book3 + " <<<<");
        // 分页查询
        List<Book> book4 = mongoTemplate.find(query.limit(10).skip(200L), Book.class, COLLECTION_NAME);
        log.info(">>>> book4 = " + book4 + " <<<<");
        // 模糊查询案例
        Query fuzzyQuery = new Query();
        // CASE_INSENSITIVE 启动大小写不敏感匹配。
        Pattern pattern = Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE);
        // 模糊查询，只支持字段属性是字符串的查询
        fuzzyQuery.addCriteria(Criteria.where("name").regex(pattern));
        /*
         * 一个模糊查询匹配多个字段的写法
         * Criteria criteria = new Criteria();
         * criteria.orOperator(Criteria.where("name").regex(pattern),Criteria.where("sex").regex(pattern),Criteria.where("age").regex(pattern),Criteria.where("class").regex(pattern));
         * fuzzyQuery.addCriteria(criteria);
         */
        List<Book> book5 = mongoTemplate.find(fuzzyQuery, Book.class, COLLECTION_NAME);
        log.info(">>>> book5 = " + book5 + " <<<<");
    }

    @PutMapping
    public void update(String name, Integer age) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        Update update = new Update();
        update.set("age", age);
        // 修改一个最先找到并确定存在的文档[不存在则报错]
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Book.class, COLLECTION_NAME);
        log.info(">>>> updateFirst = " + updateFirst + " <<<<");
        // 修改所有符合查询条件并确定存在的文档
        UpdateResult updateMulti = mongoTemplate.updateMulti(query, update, Book.class, COLLECTION_NAME);
        log.info(">>>> updateMulti = " + updateMulti + " <<<<");
        // 获取符合查询条件的文档，如果存在则修改文档，不存在则插入一条新记录[自增id]
        UpdateResult upsert = mongoTemplate.upsert(query, update, Book.class, COLLECTION_NAME);
        log.info(">>>> upsert = " + upsert + " <<<<");
    }
}
