package com.example.crawler.webmagic.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by luoxx on 2017/10/1.
 */
@RestController
@RequestMapping("/demo")
public class DemoController {


    @ApiOperation(value = "value", notes = "notes")
    @GetMapping("/json")
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

}
