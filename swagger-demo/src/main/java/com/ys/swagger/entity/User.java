package com.ys.swagger.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author yusheng
 * Created on 2020-04-29 00:51
 **/
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String Age;

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
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(300);
        sb.append("{ ");
        Field[] fields = User.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String modifier = Modifier.toString(field.getModifiers());
                if (modifier.contains("static final")) {
                    continue;
                }
                String typeName = field.getGenericType().getTypeName();
                Object value = field.get(this);
                if (typeName.equals("java.lang.String") || typeName.equals("java.util.Date")) {
                    if (value != null) {
                        sb.append(field.getName()).append(" : \"").append(value).append("\" , ");
                    } else {
                        sb.append(field.getName()).append(" : \"\" , ");
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
