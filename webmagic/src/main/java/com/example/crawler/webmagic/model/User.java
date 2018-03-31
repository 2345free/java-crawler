package com.example.crawler.webmagic.model;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Pattern(regexp = "\\w{3,10}", message = "姓名不需为3~10个单词字符")
    private String name;
    @Digits(integer = 2, fraction = 0, message = "{nan}")
    private Integer age;

}
