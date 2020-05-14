package com.ys.elasticsearch.es.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;

/**
 * @author yusheng
 * Created on 2020-04-30 20:45
 **/
@Document(indexName = "video-album", type = "video-info")
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String name;
    private String pages;
    private String type;
    private String tags;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(300);
        sb.append("{ ");
        Field[] fields = Book.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String modifier = Modifier.toString(field.getModifiers());
                if (modifier.contains("static final")) {
                    continue;
                }
                String typeName = field.getGenericType().getTypeName();
                Object value = field.get(this);
                if (typeName.equals("java.lang.String")) {
                    if (value != null) {
                        sb.append(field.getName()).append(" : \"").append(value).append("\" , ");
                    } else {
                        sb.append(field.getName()).append(" : \"\" , ");
                    }
                } else if (typeName.equals("java.util.Date")) {
                    if (value != null) {
                        sb.append(field.getName()).append(" : \"").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value)).append("\" , ");
                    } else {
                        sb.append(field.getName()).append(" : null , ");
                    }
                } else {
                    sb.append(field.getName()).append(" : ").append(field.get(this)).append(" , ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("}");
        return sb.toString();
    }
}
