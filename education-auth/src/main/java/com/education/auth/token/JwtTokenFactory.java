package com.education.auth.token;

import com.education.common.model.JwtToken;

/**
 * @author zengjintao
 * @create_at 2021年11月26日 0026 10:32
 * @since version 1.0.4
 */
public class JwtTokenFactory extends TokenFactory {

    private long expirationTime = 0;

    private final JwtToken jwtToken;

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public JwtTokenFactory(JwtToken jwtToken, long expirationTime) {
        this.expirationTime = expirationTime;
        this.jwtToken = jwtToken;
    }

    public JwtTokenFactory(JwtToken jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public String createToken(Object value, long expirationTime) {
        return jwtToken.createToken(value, expirationTime);
    }

    @Override
    public Object parseToken(String token) {
        return null;
    }
}
