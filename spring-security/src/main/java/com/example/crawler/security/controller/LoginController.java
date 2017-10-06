package com.example.crawler.security.controller;

import com.example.crawler.security.entity.CasProperties;
import com.example.crawler.security.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by luoxx on 2017/10/1.
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private CasProperties casProperties;

    @ApiOperation(value = "cas登陆页面")
    @GetMapping("/login")
    public String login() {
        return "redirect:" + casProperties.getCasServerLoginUrl();
    }

    @ApiOperation(value = "查看用户信息")
    @GetMapping("/profile")
    @ResponseBody
    public Object profile() {
        User user = new User();
        user.setUsername("tianyi");
        user.setPassword("123");
        return user;
    }

    @GetMapping("/profile2")
    @ResponseBody
    public Object profile2() {
        User user = new User();
        user.setUsername("tianyi");
        user.setPassword("123");
        return user;
    }

}
