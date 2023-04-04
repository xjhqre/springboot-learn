package com.xjhqre;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.xjhqre.Main;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * JdbcTemplateTest
 * </p>
 *
 * @author xjhqre
 * @since 4月 04, 2023
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@ContextConfiguration(classes = Main.class)
public class JdbcTemplateTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void queryTest() {
        List<Map<String, Object>> maps = this.jdbcTemplate.queryForList("select * from iot_device");
        log.info("查询结果：{}", maps);
    }
}
