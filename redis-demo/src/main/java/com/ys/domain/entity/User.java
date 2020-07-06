package com.ys.domain.entity;

import java.io.Serializable;

/**
 * @author yusheng
 * Created on 2020-07-05 23:33
 **/
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Integer gender;
    private Integer age;

    public User() {
    }

    public User(Integer id, String name, Integer gender, Integer age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public static UserBuilder builder() {
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.user = new User();
        return userBuilder;
    }

    public static UserBuilder builder(User user) {
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.user = user == null ? new User() : user;
        return userBuilder;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return this.gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{id='" + id + "'" +
                ", name='" + name + "'" +
                ", gender='" + gender + "'" +
                ", age='" + age + "'" +
                "}";
    }

    public static class UserBuilder {

        private User user;

        public UserBuilder id(Integer id) {
            user.id = id;
            return this;
        }

        public UserBuilder name(String name) {
            user.name = name;
            return this;
        }

        public UserBuilder gender(Integer gender) {
            user.gender = gender;
            return this;
        }

        public UserBuilder age(Integer age) {
            user.age = age;
            return this;
        }

        public User build() {
            return this.user;
        }

    }

}