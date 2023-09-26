package com.xjhqre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * <p>
 * MybatisPlusApp
 * <p>
 *
 * @author xjhqre
 * @since 9月 26, 2023
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class MybatisPlusApp {
    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApp.class, args);
    }
}
