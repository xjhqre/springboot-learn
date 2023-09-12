package com.xjhqre;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.xjhqre.utils.RedisUtils;

/**
 * <p>
 * RedisTest
 * <p>
 *
 * @author xjhqre
 * @since 9月 12, 2023
 */
@SpringBootTest
public class RedisTest {

    @Resource
    RedisUtils redisUtils;

    @Test
    void test2() {
        Object add = redisUtils.getCacheObject("add");
        System.out.println(add);
    }

    @Test
    void test1() {
        redisUtils.setCacheObject("add", "xjhqre");
    }
}
