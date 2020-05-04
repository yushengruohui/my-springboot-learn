package com.ys.rabbitmq.config.amqp;

import java.io.Serializable;

public class User implements Serializable {

    private String id;

    private String name;

    private Integer age;

    public User() {
    }

    public User(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{id='" + id + "'" +
                ", name='" + name + "'" +
                ", age='" + age + "'" +
                "}";
    }

    public static class UserBuilder {

        private User user;

        public UserBuilder id(String id) {
            user.id = id;
            return this;
        }

        public UserBuilder name(String name) {
            user.name = name;
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