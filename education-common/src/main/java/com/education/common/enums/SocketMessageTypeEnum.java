package com.education.common.enums;

import java.util.Arrays;

/**
 * socket 消息类型枚举
 * @author zengjintao
 * @create_at 2022/1/2 18:40
 * @since version 1.0.4
 */
public enum SocketMessageTypeEnum {

    HEART(10001, "心跳检测"),
    ADMIN_CONNECTION_SUCCESS(10010, "管理后台socket连接成功"),
    STUDENT_CONNECTION_SUCCESS(10011, "学生端socket连接成功"),
    REJECT_SESSION(10012, "账号下线通知"),
    EXAM_CORRECT(10013, "试卷批改通知");

    private Integer value;
    private String name;

    SocketMessageTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static boolean contains(Integer value) {
        SocketMessageTypeEnum socketMessageTypeEnum = Arrays.stream(values())
                .filter(item -> item.value.equals(value))
                .findAny().orElseGet(() -> null);
        return socketMessageTypeEnum != null;
    }

    public static boolean isConnectionSuccess(Integer value) {
        return ADMIN_CONNECTION_SUCCESS.value.equals(value)
                || STUDENT_CONNECTION_SUCCESS.value.equals(value);
    }
}
