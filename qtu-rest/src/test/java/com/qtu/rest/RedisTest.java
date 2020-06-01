package com.qtu.rest;

import com.qtu.rest.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Hu Shengkai
 * @create 2019-12-05 16:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisUtil redisUtil;
    @Test
    public void contextLoads() {
//		stringRedisTemplate.hasKey(key)
        redisUtil.set("qtu", "河南量子");
        redisUtil.del("qtu");
        Boolean flag = redisUtil.hasKey("qtu");
        if (flag) {
            System.out.println("存在");
        }else {
            System.out.println("不存在");
        }
        // 第一次读取redis 读取  ，如果存在 ，直接从redis 获得数据
        // 如果不存在，我们就从数据库中，获取。获取完，存入redis 中
    }
}
