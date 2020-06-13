package com.education.admin.api;


import com.education.common.cache.EhcacheBean;
import com.education.common.cache.CacheBean;
import com.education.common.model.AdminUserSession;
import com.education.common.model.ModelBeanMap;
import com.education.common.utils.IpUtils;
import com.education.common.utils.MapTreeUtils;
import com.education.common.utils.ObjectUtils;
import com.education.mapper.system.SystemMenuMapper;
import com.jfinal.kit.HttpKit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.management.modelmbean.ModelMBean;
import java.util.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class EducationAdminApiApplicationTests {

    @Autowired
    @Qualifier("redisCacheBean")
    public CacheBean cacheBean;
    static final String cacheName = "user:cache";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SystemMenuMapper systemMenuMapper;

  //  @Autowired
   // private RedissonClient redissonClient;

    @Test
    public void remove() {
        Map params = new HashMap();
        params.put("name", "java");
        params.put("principal_flag", false);
        params.put("super_flag", false);
        AdminUserSession adminUserSession = new AdminUserSession(params);
        redisTemplate.opsForValue().set("test", adminUserSession);
        adminUserSession = (AdminUserSession) redisTemplate.opsForValue().get("test");
        System.out.println(adminUserSession);
      //  redisTemplate.delete(redisTemplate.keys("*"));
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 50; i++) {
         //   Thread.sleep(1000);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String content = HttpKit.get("http://127.0.0.1/limit");
                    System.out.println(content);
                }
            }).start();
        }
    }


    @Test
    public void testRedisStringCache() {
        cacheBean.put("1", "java");
        cacheBean.put("2", "php");
        cacheBean.put("3", "python");
        String value = cacheBean.get(cacheName, "1");
        System.out.println("value:" + value);
        System.out.println(cacheBean.getKeys(cacheName));
        cacheBean.removeAll(cacheName);
        System.out.println(cacheBean.getKeys(cacheName));
    }

    @Test
    public void testRedisObjectCache() {
        AdminUserSession adminUserSession = new AdminUserSession(new HashMap());
        Set<String> set = new HashSet<>();
        redisTemplate.opsForValue().set("user", adminUserSession);
        set.add("test:12:12");
        adminUserSession.setPermissionList(set);
        System.err.println(adminUserSession);

        adminUserSession = (AdminUserSession) redisTemplate.opsForValue().get("user");
        System.out.println(adminUserSession);
    }

    @Test
    public void testEhcacheObjectCache() {
        EhcacheBean ehcacheBean = new EhcacheBean();
        AdminUserSession adminUserSession = new AdminUserSession(new HashMap());

        System.err.println(adminUserSession);
        ehcacheBean.put("user", adminUserSession);
        Set<String> set = new HashSet<>();
        set.add("test:12:12");
        adminUserSession.setPermissionList(set);
        adminUserSession = (AdminUserSession) ehcacheBean.get("user");
        System.out.println(adminUserSession);
    }

    @Test
    public void testEhcache() {
        CacheBean ehcacheBean = new EhcacheBean();
        ehcacheBean.put(cacheName, "1", "java");
        ehcacheBean.put(cacheName, "2", "php");
        ehcacheBean.put(cacheName, "3", "python");
        Collection collection = ehcacheBean.getKeys();
        if (ObjectUtils.isNotEmpty(collection)) {
            collection.forEach(key -> {
                System.out.println("key" + key);
                System.out.println(ehcacheBean.get(key) + "");
            });
        }
    }

    @Test
    public void testIp() {
        System.out.println(IpUtils.getIpAddress("182.101.63.196"));
    }
}
