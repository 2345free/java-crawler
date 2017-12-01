package com.example.crawler.webmagic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Lockable{
    // redis缓存key
    String key();
    // redis缓存key中的数据
    String value() default "";
    // 过期时间(秒)，默认为一分钟
    long expire() default 60;
}