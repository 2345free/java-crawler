/**
 * Copyright (C), 2017-2017, 帮5采
 * FileName: SpringMvcConfig
 * Author:   tianyi
 * Date:     2017/9/30 16:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.crawler.webmagic;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author tianyi
 * @create 2017/9/30
 * @since 1.0.0
 */
@Configuration
@EnableWebMvc
@ComponentScan
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui.html");
    }

    // fastJosn
    @Bean
    public FastJsonConfig fastJsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 全局配置命名策略
        fastJsonConfig.getSerializeConfig().setPropertyNamingStrategy(PropertyNamingStrategy.CamelCase); // 下划线规则: SnakeCase
        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect); // 禁用循环引用检测
        return fastJsonConfig;
    }

}