package com.xjhqre.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.stp.StpUtil;

@RestController
@RequestMapping("/user")
public class UserController {

    // 测试登录，浏览器访问： http://localhost:8081/user/doLogin?username=zhang&password=123456
    @RequestMapping("/doLogin")
    public String doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if ("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

    // 查询当前账号所拥有的权限集合，浏览器访问： http://localhost:8081/user/getPermissionList
    @RequestMapping("getPermissionList")
    public List<String> getPermissionList() {
        return StpUtil.getPermissionList();
    }

    // 判断：当前账号是否含有指定权限, 返回 true 或 false，浏览器访问： http://localhost:8081/user/hasPermission
    @RequestMapping("hasPermission")
    public boolean hasPermission() {
        return StpUtil.hasPermission("user.add");
    }
}
