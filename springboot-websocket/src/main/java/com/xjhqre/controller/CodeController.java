package com.xjhqre.controller;

import com.xjhqre.webSocket.WebSocket;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * CodeController
 * <p>
 *
 * @author xjhqre
 * @since 7月 12, 2023
 */
@RestController
@RequestMapping("/codeController")
public class CodeController {
    @Resource
    private WebSocket webSocket;

    @PostMapping("/progressBar")
    public void progressBar() throws InterruptedException {
        String msg = "";
        int a = 0;
        for(int i = 0; i <= 100; i++) {
            msg = String.valueOf(a);
            Thread.sleep(100);
            webSocket.sendMessage(msg);
            a += 1;
        }
    }
}
