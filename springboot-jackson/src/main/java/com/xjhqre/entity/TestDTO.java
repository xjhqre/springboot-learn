package com.xjhqre.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * TestDTO
 * <p>
 *
 * @author xjhqre
 * @since 9月 08, 2023
 */
public class TestDTO {

    // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime localDateTime;

    List<String> list;

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "TestDTO{" + "localDateTime=" + localDateTime + '}';
    }
}
