package com.example.crawler.security.entity.websocket.message;

import lombok.Data;

@Data
public class ToUserMessage {

    private String userId;

    private String message;

}
