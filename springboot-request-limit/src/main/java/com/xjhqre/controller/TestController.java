package com.xjhqre.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xjhqre.annotation.RateLimiter;
import com.xjhqre.enums.LimitType;

/**
 * <p>
 * TestController
 * <p>
 *
 * @author xjhqre
 * @since 9月 12, 2023
 */
@RestController
public class TestController {

    @RateLimiter
    @GetMapping("/limitTest")
    public String limitTest() {
        return "success";
    }

    @RateLimiter(limitType = LimitType.IP)
    @GetMapping("/limitTest2")
    public String limitTest2() {
        return "success2";
    }
}
