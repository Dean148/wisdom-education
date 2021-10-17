package com.education.common.constants;


/**
 * 系统常量类
 * @author zengjintao
 * @version 1.0
 * @create_at 2018/11/27 20:01
 */
public final class Constants {

    public static final String EDUCATION_ADMIN_SECRET_KEY = "education_admin";
    public static final String EDUCATION_FRONT_SECRET_KEY = "education_front";


    public static final String USER_INFO_CACHE = "user.cache";
    public static final String SESSION_NAME = "u_id_key";
    public static final String DEFAULT_SESSION_ID = "JSESSIONID";
    public static final int SESSION_TIME_OUT = 24 * 60 * 60 * 5;
    public static final int SESSION_TIME_OUT_MILLISECONDS = SESSION_TIME_OUT * 1000;

    public static final int TWO_HOUR_SECOND = 60 * 60 * 2;

    public static final int ONE_DAY = 24 * 60;

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

    public static final Integer COURSE_VALUATE_MARK = 10;
}
