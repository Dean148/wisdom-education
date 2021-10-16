package com.education.common.constants;


/**
 * 系统常量类
 * @author zengjintao
 * @version 1.0
 * @create_at 2018/11/27 20:01
 */
public final class SystemConstants {


    public static final String USER_INFO_CACHE = "user.cache";
    public static final String SESSION_NAME = "u_id_key";
    public static final String DEFAULT_SESSION_ID = "JSESSIONID";


    public static final String[] IMAGE_ALIAS = new String[] {"head_img", "image"};


    public static final double PASS_MARK_RATE = 0.6; // 及格比例
    public static final double NICE_MARK_RATE = 0.8; // 优秀比例

    public static final String SESSION_KEY = "user.session.cache";

    public static final String PAPER_INFO_TEMPLATE = "/template/enjoy/paperInfoTemplate.html";

    public static final Integer SEND_RUNNING = 1;

    public static final Integer SEND_SUCCESS = 2;

    public static final Integer CONSUME_SUCCESS = 3;

    public static final Integer CONSUME_FAIL = 4;

    public static final Integer SEND_FAIL = 5;

    // 消息发送最大次数
    public static final Integer MAX_SEND_COUNT = 3;

    public static final String REDIS_TEMPLATE_BEAN_NAME = "redisTemplate";

    public static final String DEFAULT_GROUP_JOB = "default_job";
}
