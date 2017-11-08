/**
 * Copyright (C), 2017-2017, 帮5采
 * FileName: Tests
 * Author:   tianyi
 * Date:     2017/11/8 13:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.crawler.bigdata;

import com.example.crawler.BaseTest;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author tianyi
 * @create 2017/11/8
 * @since 1.0.0
 */
public class Tests extends BaseTest {

    @Autowired
    private HbaseTemplate template;

    @Test
    public void testFind() {
        List<String> rows = template.find("user", "personal info", "name", new RowMapper<String>() {
            public String mapRow(Result result, int i) throws Exception {
                return result.toString();
            }
        });
        System.err.println(rows);
        Assert.assertNotNull(rows);
    }

    @Test
    public void testPut() {
        template.put("user", "0001", "personal info", "name", Bytes.toBytes("tianyi"));
    }

}