package com.example.crawler.webmagic.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luoxx on 2017/10/1.
 */
@Api(value = "value")
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

}
