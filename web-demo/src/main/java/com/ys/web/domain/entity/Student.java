package com.ys.web.domain.entity;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author yusheng
 * Created on 2020-05-10 19:27
 **/
public class Student {
    private Integer id;
    private String name;
    private String sno;
    private String address;

    public Student() {
    }

    protected Student(Integer id, String name, String sno, String address) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.sno = Objects.requireNonNull(sno);
        this.address = Objects.requireNonNull(address);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = Objects.requireNonNull(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = Objects.requireNonNull(sno);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = Objects.requireNonNull(address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Student that = (Student) o;

        return Objects.equals(this.getId(), that.getId()) && Objects.equals(this.getName(), that.getName()) && Objects.equals(this.getSno(), that.getSno()) && Objects.equals(this.getAddress(), that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSno(), getAddress());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("id=" + getId()).add("name=" + getName()).add("sno=" + getSno()).add("address=" + getAddress()).toString();
    }

    public static Student.Builder builder() {
        return new Student.Builder();
    }

    public static Student.Builder builder(Student data) {
        return new Student.Builder(data);
    }

    public static final class Builder {

        private Integer id;
        private String name;
        private String sno;
        private String address;

        private Builder() {
        }

        private Builder(Student initialData) {
            this.id = initialData.getId();
            this.name = initialData.getName();
            this.sno = initialData.getSno();
            this.address = initialData.getAddress();
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSno(String sno) {
            this.sno = sno;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Student build() {
            return new Student(id, name, sno, address);
        }
    }
}
