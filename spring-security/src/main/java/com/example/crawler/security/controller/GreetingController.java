package com.example.crawler.security.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by luoxx on 2017/10/14.
 */
@Controller
public class GreetingController {
    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping("/hello-socket")
    public String index() {
        return "socket/index";
    }

    @MessageMapping("/change-notice2")
    public void greeting2(String value) {
        this.simpMessagingTemplate.convertAndSend("/topic/notice", value);
    }

    @MessageMapping("/change-notice")
    @SendTo("/topic/notice")
    public String greeting(String value) {
        return value;
    }

}