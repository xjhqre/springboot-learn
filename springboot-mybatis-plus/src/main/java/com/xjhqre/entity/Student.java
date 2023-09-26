package com.xjhqre.entity;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.EqualsAndHashCode;

/**
 * <p>
 * Student
 * <p>
 *
 * @author xjhqre
 * @since 9月 26, 2023
 */
@EqualsAndHashCode(callSuper = true)
@TableName("student")
@Component
public class Student extends BaseEntity {
    private Long id;

    private String name;

    private Integer age;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
