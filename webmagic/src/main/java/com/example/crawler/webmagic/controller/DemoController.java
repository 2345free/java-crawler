package com.example.crawler.webmagic.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.crawler.webmagic.annotation.ApiLog;
import com.example.crawler.webmagic.model.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Created by luoxx on 2017/10/1.
 */
@RestController
@RequestMapping("/demo")
public class DemoController {


    @ApiOperation(value = "value", notes = "notes")
    @GetMapping("/json")
    @ApiLog(remark = "获取用户信息json")
    public Object demo() {
        JSONObject o = new JSONObject();
        o.put("userName", "tianyi");
        return o;
    }

    @ApiIgnore
    @GetMapping({"/m1", "/m2", "/m3"})
    public Object m1() {
        JSONObject o = new JSONObject();
        o.put("userName", "tianyi");
        return o;
    }

    @GetMapping("/user/add")
    public Object addUser(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        return bindingResult.getFieldErrors();
    }

}
