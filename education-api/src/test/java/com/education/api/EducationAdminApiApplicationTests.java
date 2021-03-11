package com.education.api;



import com.education.business.service.education.QuestionInfoService;
import com.education.business.service.system.SystemAdminService;
import com.education.common.cache.CacheBean;
import com.education.common.cache.EhcacheBean;
import com.education.common.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;
import java.util.List;
import java.util.Map;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class EducationAdminApiApplicationTests {

    public static void main(String[] args) {
        System.out.println(ObjectUtils.charSort("A,B,C".replaceAll(",", "")));

        System.out.println(ObjectUtils.charSort("C,A,B".replaceAll(",", "")));
    }

    @Autowired
    private QuestionInfoService questionInfoService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    private CacheBean cacheBean = new EhcacheBean();

    @Autowired
    private SystemAdminService systemAdminService;


    @Test
    public void importData() {
        List<Map<String, Object>> dataList = jdbcTemplate.queryForList("select course_id, language_points_id" +
                " from question_info");
        dataList.forEach(data -> {
            Integer courseId = (Integer) data.get("course_id");
            Integer languagePointsId = (Integer) data.get("language_points_id");
            List<Map<String, Object>> courseLanguageDataList = jdbcTemplate.queryForList("select * from course_language_points where course_id = ? and language_points_id = ?",
                    courseId, languagePointsId);

            if (courseLanguageDataList.size() ==  0) {
                jdbcTemplate.update("insert into course_language_points(course_id, language_points_id, create_date) values (?, ?, ?)",
                        courseId, languagePointsId, new Date());
                log.info("insert into course_language_points(course_id, language_points_id, create_date) values " + "(" + courseId
                        + "," + languagePointsId + "," + new Date());
            }
        });
    }

    @Test
    public void check() {
      //  redisTemplate.opsForList().ad

        cacheBean.put("test", "test");


        System.out.println(cacheBean.getKeys());

       // redisTemplate.boundHashOps("userId").increment("user:1", 1);

      //  System.out.println(redisTemplate.boundHashOps("userId").get("user:1"));
       // redisTemplate.opsForValue().b
       /* SystemAdmin systemAdmin = new SystemAdmin();
        systemAdmin.setLoginName("admin");
        systemAdmin.setEncrypt("1111");
        systemAdmin.setPassword("12222");
        systemAdmin.setCreateType(2);
        systemAdminService.saveOrUpdate(systemAdmin);*/
    }

    @Test
    public void testQuestion() {
        List<Map<String, Object>> questionList = jdbcTemplate.queryForList("SELECT * from (SELECT id, subject_id, content, count(1) number from question_info GROUP BY content, subject_id ) a\n" +
                "\n" +
                "where number > 1");
        System.err.println(questionList.size());
        questionList.forEach(item -> {
            Integer subjectId = (Integer) item.get("subject_id");
            String content = (String) item.get("content");
            List<Map<String, Object>> questionEqList = jdbcTemplate.queryForList("select id from question_info where content = ? and subject_id = ?" +
                    " order by id asc", content, subjectId);
            for (int i = 1; i < questionEqList.size(); i++) {
                Map eq = questionEqList.get(i);
                String sql = "update question_info set delete_flag = 1 where id = ?";
                jdbcTemplate.update("update question_info set delete_flag = 1 where id = ?", eq.get("id"));
                log.info(sql.replace("?", "") + eq.get("id"));
            }
        });
     //   QuestionInfoDto questionInfoDto = questionInfoService.selectById(1728);
     //   System.out.println(questionInfoDto);
    }


  /*  @Autowired
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

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            list.add(i);
        }

      //  list = list.subList(9, 10);

      //  list = list.subList(10, 15);

        PageInfo<Integer> page = ObjectUtils.selectPageList(5, 4, list);
        System.out.println(page.getDataList());
        System.out.println(page.getTotal());
     *//*   PageInfo<Integer> page = ObjectUtils.selectPageList(3, 3, list);

        System.out.println(page.getDataList());
        System.out.println(page.getTotal());*//*
       // String address = IpUtils.getIpAddress("111.72.96.124");
       // System.out.println(address);

     //   redisTemplate.boundHashOps("test").increment("userId", 1);

     //   System.err.println(redisTemplate.boundHashOps("test").get("userId"));
       // redisTemplate.boundHashOps("test").

       // Collection keys = redisTemplate.keys("*");
    //    redisTemplate.delete(redisTemplate.keys("*"));
      //  System.out.println(keys);
      //  List<ModelBeanMap> dataList = systemMenuMapper.treeList();
       // List<ModelBeanMap> parentList = MapTreeUtils.getParentList(dataList, 2);
       // System.out.println(parentList);
      //  redisTemplate.delete(redisTemplate.keys("*"));
      *//*  Map params = new HashMap<>();
        String[] array = new String[]{"name", "dsdsd"};
        params.put("name"," test");

        params.put("data", array);*//*
        //System.out.println(JSONObject.toJSONString(params));
    }


    @Test
    public void testRedisStringCache() {
            redisTemplate.delete(cacheBean.getKeys(""));
       // cacheBean.put("1", "java");
       // cacheBean.put("2", "php");
       // cacheBean.put("3", "python");
       // String value = cacheBean.get(cacheName, "1");
      *//*  System.out.println("value:" + value);
        System.out.println(cacheBean.getKeys(cacheName));
        cacheBean.removeAll(cacheName);
        System.out.println(cacheBean.getKeys(cacheName));*//*
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

  *//*  @Test
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
    }*//*



   @Test
   public void mybatisPlus() {

   //    SystemAdmin systemAdmin = systemAdminMapper.selectOne(new QueryWrapper<SystemAdmin>().eq("id", 1));
   //    System.out.println(systemAdmin);
       AdminRoleDto adminRoleDto = systemAdminMapper.selectById(4);
       System.out.println(adminRoleDto.getRoleIds());

       adminRoleDto.setId(null);
       int s = systemAdminMapper.insert(adminRoleDto);
       System.out.println(s);
      *//* QueryWrapper<SystemAdmin> queryWrapper = new QueryWrapper<SystemAdmin>();
       String admin = "admin";
       queryWrapper.select("id", "name").eq(ObjectUtils.isNotEmpty(admin), "login_name", admin);
       SystemAdmin systemAdmin = systemAdminMapper.selectOne(queryWrapper);
       System.out.println(systemAdmin);*//*

       SimpleSession simpleSession = new SimpleSession();

       cacheBean.put("test0", simpleSession);

       System.out.println(cacheBean.get("test0").getClass());
      List<MenuTree> menuTreeList = TreeUtils.buildTreeData(systemMenuMapper.getTreeMenuList()) ;
       System.out.println(menuTreeList);
      // systemAdminMapper.selectOne(queryWrapper.)
      // List<SystemAdmin> list = systemAdminMapper.pageList();
      // System.out.println(list);
   }

    @Test
    public void testIp() {
        System.out.println(IpUtils.getIpAddress("182.101.63.196"));
    }*/
}
