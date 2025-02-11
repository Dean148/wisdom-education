package com.education.auth;

/**
 * @author zengjintao
 * @create_at 2021年11月25日 0025 16:41
 * @since version 1.0.4
 */
public class LoginToken {

    private String username;
    public String password;
    private String loginType;
    private String deviceType;
    private final boolean remember;

    public LoginToken(String username, String password) {
        this(username, password, null, null, false);
    }

    public LoginToken(String username, String password, String loginType, String deviceType) {
        this(username, password, loginType, deviceType, false);
    }

    public LoginToken(String username, String password, boolean remember) {
        this(username, password, null, null, remember);
    }

    public LoginToken(String username, String password, String loginType, String deviceType, boolean remember) {
        this.username = username;
        this.password = password;
        this.loginType = loginType;
        this.deviceType = deviceType;
        this.remember = remember;
    }

    public String getUsername() {
        return username;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
