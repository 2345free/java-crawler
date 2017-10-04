package com.example.crawler.security.controller;

import com.example.crawler.security.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by luoxx on 2017/10/1.
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @ApiOperation(value = "登陆表单")
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @ApiOperation(value = "登陆")
    @PostMapping("/login")
    @ResponseBody
    public Object login(@RequestBody User user) {
        user.setUsername("tianyi");
        user.setPassword("123");
        return user;
    }

}
