package com.education.model.request;

import com.education.common.enums.DeviceType;

import javax.validation.constraints.NotBlank;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/12 19:49
 */
public class UserLoginRequest {

    @NotBlank(message = "请输入用户名")
    private String userName;
    @NotBlank(message = "请输入密码")
    private String password;
    @NotBlank(message = "请传递一个验证码时间戳")
    private String key;
    @NotBlank(message = "请输入验证码")
    private String code;

    /**
     * 设备类型
     */
    private String deviceType = DeviceType.PC.getValue();

    private boolean checked = false;

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
