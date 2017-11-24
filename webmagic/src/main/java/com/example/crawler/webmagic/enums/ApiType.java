package com.example.crawler.webmagic.enums;

public enum ApiType {

    DEMO("demo接口", "0");

    private String desc;
    private String code;

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return code;
    }

    ApiType(String desc, String code) {
        this.desc = desc;
        this.code = code;
    }
}
