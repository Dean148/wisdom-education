package com.education.api.config.auth;

import com.education.auth.AuthHandler;
import com.education.auth.AuthRealmManager;
import com.education.auth.realm.LoginAuthRealm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * auth 认证配置
 * @author zengjintao
 * @create_at 2021年11月27日 0027 11:47
 * @since version 1.0.4
 */
@Configuration
public class AuthBeanConfig {

    @Resource
    private LoginAuthRealm adminLoginRealm;

    @Bean
    public AuthRealmManager authRealmManager() {
        AuthRealmManager authRealmManager = new AuthRealmManager();
        authRealmManager.addLoginAuthRealm(adminLoginRealm);
        return authRealmManager;
    }

    @Bean
    public AuthHandler authHandler() {
        return new AuthHandler();
    }
}
