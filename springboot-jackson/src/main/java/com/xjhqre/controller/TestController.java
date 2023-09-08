package com.xjhqre.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xjhqre.entity.TestDTO;

/**
 * <p>
 * TestController
 * <p>
 *
 * @author xjhqre
 * @since 9月 08, 2023
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test1")
    public LocalDateTime test1() {
        return LocalDateTime.now();
    }

    /**
     * 反序列化测试，postman参数： { "localDateTime": "2023-09-08 16:07:42" } 必须传json格式
     */
    @PostMapping("/test2")
    public void test2(@RequestBody TestDTO testDTO) {
        System.out.println(testDTO);
    }

    /**
     * UNWRAP_SINGLE_VALUE_ARRAYS，测试 参数： [ { "localDateTime": "2023-09-08T16:23:03.565" } ]
     */
    @PostMapping("/test3")
    public void test3(@RequestBody TestDTO testDTO) {
        System.out.println(testDTO);
    }

}
