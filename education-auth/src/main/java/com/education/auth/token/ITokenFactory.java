package com.education.auth.token;

import cn.hutool.core.util.IdUtil;

/**
 * token 创建接口
 * @author zengjintao
 * @create_at 2021年11月26日 0026 10:36
 * @since version 1.0.4
 */
public interface ITokenFactory {

    long DEFAULT_EXPIRATION_TIME = 60 * 60 * 1000;

    String createToken(Object value, long expirationTime);

    default String createToken() {
        return IdUtil.simpleUUID();
    }

    Object parseToken(String token);
}
