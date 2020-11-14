package com.education.admin.api;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.common.cache.EhcacheBean;
import com.education.common.cache.CacheBean;
import com.education.common.model.AdminUserSession;
import com.education.common.model.ModelBeanMap;
import com.education.common.utils.IpUtils;
import com.education.common.utils.MapTreeUtils;
import com.education.common.utils.ObjectUtils;
import com.education.mapper.system.SystemAdminMapper;
import com.education.mapper.system.SystemMenuMapper;
import com.education.model.entity.SystemAdmin;
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

    @Autowired
    private SystemAdminMapper systemAdminMapper;

  //  @Autowired
   // private RedissonClient redissonClient;

    @Test
    public void remove() {
      //  List<ModelBeanMap> dataList = systemMenuMapper.treeList();
       // List<ModelBeanMap> parentList = MapTreeUtils.getParentList(dataList, 2);
       // System.out.println(parentList);
      //  redisTemplate.delete(redisTemplate.keys("*"));
        Map params = new HashMap<>();
        String[] array = new String[]{"name", "dsdsd"};
        params.put("name"," test");

        params.put("data", array);
        //System.out.println(JSONObject.toJSONString(params));
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

    }            collection.forEach(key -> {
        System.out.println("key" + key);
        System.out.println(ehcacheBean.get(key) + "");
    });
}

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            String content = HttpKit.get("http://127.0.0.1/limit/submit?name=java");
            System.out.println(content);
        }
    }

  /*  @Test
    public void testMyBatis() {
        SystemAdmin systemAdmin = new SystemAdmin();
       // systemAdmin.setAttr("login_name", "");
      //  List<SystemAdmin> list = systemAdminMapper.list(systemAdmin);
       // System.out.println(list);
        systemAdmin.setAttr("login_name", "test11");
        systemAdmin.setAttr("password", "password11");
        systemAdmin.setAttr("encrypt", "password11");
        systemAdmin.setAttr("create_date", new Date());
        int result = systemAdminMapper.saveAdmin(systemAdmin);
        System.out.println(result);
    }*/


/*   @Test
   public void mybatisPlus() {
       QueryWrapper<SystemAdmin> queryWrapper = new QueryWrapper<SystemAdmin>();
       String admin = "admin";
       queryWrapper.select("id", "name").eq(ObjectUtils.isNotEmpty(admin), "login_name", admin);
       SystemAdmin systemAdmin = systemAdminMapper.selectOne(queryWrapper);
       System.out.println(systemAdmin);
      // systemAdminMapper.selectOne(queryWrapper.)
      // List<SystemAdmin> list = systemAdminMapper.pageList();
      // System.out.println(list);
   }*/

    @Test
    public void testIp() {
        System.out.println(IpUtils.getIpAddress("182.101.63.196"));
    }
}
