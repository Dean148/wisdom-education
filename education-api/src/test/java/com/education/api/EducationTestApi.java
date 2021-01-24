package com.education.api;

import com.education.common.cache.CacheBean;
import com.education.common.cache.CaffeineCacheBean;
import com.education.common.cache.EhcacheBean;
import com.jfinal.kit.Kv;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/1/22 12:01
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class EducationTestApi {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CacheBean redisCacheBean;

    private CacheBean caffeineCacheBean = new CaffeineCacheBean();

    private CacheBean ehcacheBean = new EhcacheBean();

    /**
     * redis 存储string value
     */
    @Test
    public void redisStringValue() throws InterruptedException {
        redisTemplate.opsForValue().set("test", "test:value", 10, TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("test"));
        Thread.sleep(10050);
        System.out.println(redisTemplate.opsForValue().get("test"));
    }

    @Test
    public void redisHashValue() {
        Kv kv = Kv.create().set("id", 1).set("age", 10).set("address", "江西");
        redisTemplate.opsForHash().putAll("params", kv);
        Map map = redisTemplate.opsForHash().entries("params");
        System.out.println(map);
    }

    @Test
    public void redisList() {
        for (int i = 0; i < 5; i++) {
            redisTemplate.opsForList().leftPush("list", i);
        }
    }

    public static void main(String[] args) {
        new ArrayList<>().add(1);
    }
    @Test
    public void getRedisList() {
        int size = redisTemplate.opsForList().size("list").intValue();
        List<Integer> list = redisTemplate.opsForList().range("list", 0, size - 1);
        System.out.println(list);
    }

    public void testRedisZset() {
        //Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
       // redisTemplate.opsForZSet().add("2012-12-10:10", 10, 10);
    }

    @Test
    public void testCache() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            redisCacheBean.put("test", i);
        }
        System.out.println("redisCacheBean 耗时:" + (System.currentTimeMillis() - start));
    }

    @Test
    public void test1() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            ehcacheBean.put("test", i);
        }

        System.out.println("ehcacheBean 耗时:" + (System.currentTimeMillis() - start));
    }

    @Test
    public void test2() {
        long start = System.currentTimeMillis();
        int n = 0;
        for (int i = 0; i < 10000; i++) {
           // caffeineCacheBean.put("test", i);
            n++;
        }
        System.out.println("caffeineCacheBean 耗时:" + (System.currentTimeMillis() - start));
    }
}
