package com.education.auth.token;


import cn.hutool.core.util.IdUtil;

/**
 * @author zengjintao
 * @create_at 2021年11月26日 0026 10:51
 * @since version 1.0.4
 */
public class DefaultTokenFactory extends TokenFactory {

    @Override
    public String createToken() {
        return IdUtil.simpleUUID();
    }
}
