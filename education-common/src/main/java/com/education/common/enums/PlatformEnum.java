package com.education.common.enums;

/**
 * @author zengjintao
 * @create_at 2021/10/23 11:30
 * @since version 1.0.3
 */
public enum PlatformEnum {

    SYSTEM_ADMIN("educationAdmin", "管理后台"),
    SYSTEM_STUDENT("educationStudent", "学生端");

    private String headerValue;
    private String name;

    PlatformEnum(String headerValue, String name) {
        this.headerValue = headerValue;
        this.name = name;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public String getName() {
        return name;
    }
}
