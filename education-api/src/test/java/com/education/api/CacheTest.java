package com.education.api;

import com.baomidou.mybatisplus.annotation.TableName;
import com.education.common.cache.CaffeineCacheBean;
import com.education.common.cache.CaffeineCacheElement;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/17 10:21
 */
public class CacheTest {

    @Test
    public void cache() throws InterruptedException {
        /*Cache cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();

        cache.put("test", "test1");

        System.out.println(cache.getIfPresent("test"));

        Thread.sleep(5000);
        System.out.println(cache.getIfPresent("test"));

        Thread.sleep(6000);
        System.out.println(cache.getIfPresent("test"));*/

      /*  CaffeineCacheElement caffeineCacheElement = new CaffeineCacheElement("1");
        caffeineCacheElement.setCreateTime(new Date());

        caffeineCacheElement.setLiveSeconds(10);

        Thread.sleep(8000);
        System.out.println(caffeineCacheElement.isTimeOut());*/

        CaffeineCacheBean caffeineCacheBean = new CaffeineCacheBean();
        caffeineCacheBean.put("test", 11, 6);
        Thread.sleep(6010);
        System.out.println((Object) caffeineCacheBean.get("test"));
    }
}
