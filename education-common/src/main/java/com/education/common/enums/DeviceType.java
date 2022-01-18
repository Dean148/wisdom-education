package com.education.common.enums;

/**
 * @author zjt
 * @create_at 2022年1月18日 0018 11:01
 * @since 1.6.9
 */
public enum DeviceType {

    PC("PC"),
    MOBILE("mobile");

    private String value;

    DeviceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
