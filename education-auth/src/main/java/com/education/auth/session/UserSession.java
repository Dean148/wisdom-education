package com.education.auth.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户session
 * @author zengjintao
 * @create_at 2021年11月25日 0025 16:26
 * @since version 1.0.4
 */
public abstract class UserSession implements Serializable {

    /**
     * 权限列表
     */
    private final List<String> permissionList = new ArrayList<>();

    /**
     * 用户id
     */
    private Number id;

    private String token;

    private Date createDate;

    private String loginType;

    private String deviceType;

    public Date getCreateDate() {
        return createDate;
    }

    public String getLoginType() {
        return loginType;
    }


    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public UserSession(Number userId) {
        this.id = userId;
        this.createDate = new Date();
    }

    public List<String> getPermissionList() {
        return permissionList;
    }

    public void addPermission(String permission) {
        permissionList.add(permission);
    }

    public void addPermission(Collection<String> permission) {
        permissionList.addAll(permission);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
