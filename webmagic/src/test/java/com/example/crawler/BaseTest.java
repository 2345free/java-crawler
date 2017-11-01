/**
 * Copyright (C), 2017-2017, 帮5采
 * FileName: BaseTest
 * Author:   tianyi
 * Date:     2017/11/1 15:53
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.crawler;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author tianyi
 * @create 2017/11/1
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/context-*.xml")
@WebAppConfiguration
public class BaseTest {

    @Autowired
    private HbaseTemplate template;

    @Test
    public void testFind() {
        List<String> rows = template.find("user", "cf", "name", new RowMapper<String>() {
            public String mapRow(Result result, int i) throws Exception {
                return result.toString();
            }
        });
        Assert.assertNotNull(rows);
    }

    @Test
    public void testPut() {
        template.put("user", "1", "cf", "name", Bytes.toBytes("Alice"));
    }
}