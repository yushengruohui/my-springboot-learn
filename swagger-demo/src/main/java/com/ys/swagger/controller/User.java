package com.ys.swagger.controller;

import java.awt.print.Book;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;

/**
 * @author yusheng
 * Created on 2020-05-03 12:34
 **/
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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
