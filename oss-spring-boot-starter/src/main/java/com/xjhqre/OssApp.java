package com.xjhqre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * <p>
 * OssApp
 * <p>
 *
 * @author xjhqre
 * @since 9月 11, 2023
 */
@EnableConfigurationProperties
@SpringBootApplication
public class OssApp {
    public static void main(String[] args) {
        SpringApplication.run(OssApp.class, args);
    }
}
