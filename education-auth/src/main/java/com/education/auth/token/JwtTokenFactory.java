package com.education.auth.token;

/**
 * @author zengjintao
 * @create_at 2021年11月26日 0026 10:32
 * @since version 1.0.4
 */
public class JwtTokenFactory extends TokenFactory {

    private long expirationTime = 0;

    public JwtTokenFactory(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public JwtTokenFactory() {

    }

    @Override
    public String createToken() {
        return null;
    }

    @Override
    public long getTokenExpirationTime() {
        if (expirationTime == 0) {
            return super.getTokenExpirationTime();
        }
        return expirationTime;
    }
}
