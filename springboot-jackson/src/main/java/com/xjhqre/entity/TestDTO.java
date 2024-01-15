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
    private LocalDateTime localDateTime;

    private String name;

    private List<String> ids;

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "TestDTO{" + "localDateTime=" + localDateTime + '}';
    }
}
