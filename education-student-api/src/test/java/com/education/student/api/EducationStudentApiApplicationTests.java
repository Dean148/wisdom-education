package com.education.student.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EducationStudentApiApplicationTests {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis() {
       // redisTemplate.opsForValue().set("test", "test");
        redisTemplate.boundHashOps("book_id_test").increment("number", 1);
        System.out.println(redisTemplate.boundHashOps("book_id_test").get("number"));
     //   System.out.printf(redisTemplate.toString());
    }

}
