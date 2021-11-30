package com.education.auth;

import cn.hutool.core.util.StrUtil;
import com.education.auth.realm.LoginAuthRealm;
import com.education.auth.session.LocalSessionStorage;
import com.education.auth.session.SessionStorage;
import com.education.auth.token.DefaultTokenFactory;
import com.education.auth.token.TokenFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zengjintao
 * @create_at 2021年11月25日 0025 16:36
 * @since version 1.0.4
 */
public class AuthRealmManager {

    private final Map<String, LoginAuthRealm> loginAuthRealmMap = new HashMap<>();

    private final SessionStorage sessionStorage;

    private TokenFactory tokenFactory;

    private AuthConfig authConfig;

    public AuthRealmManager addLoginAuthRealm(LoginAuthRealm loginAuthRealm) {
        String loginType = loginAuthRealm.getLoginType();
        if (StrUtil.isNotBlank(loginType)) {
            loginAuthRealmMap.put(loginType, loginAuthRealm);
        } else {
            loginAuthRealmMap.put(loginAuthRealm.getClass().getSimpleName(), loginAuthRealm);
        }
        return this;
    }

    public AuthRealmManager(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public TokenFactory getTokenFactory() {
        return tokenFactory;
    }

    public AuthRealmManager() {
        this.sessionStorage = new LocalSessionStorage();
        this.tokenFactory = new DefaultTokenFactory();
        this.authConfig = new AuthConfig();
    }

    public AuthConfig getAuthConfig() {
        return authConfig;
    }

    public SessionStorage getSessionStorage() {
        return sessionStorage;
    }

    public List<LoginAuthRealm> getLoginAuthRealmList() {
        return (List<LoginAuthRealm>) loginAuthRealmMap.values();
    }

    public LoginAuthRealm getByLoginType(String loginType) {
        return loginAuthRealmMap.get(loginType);
    }
}
