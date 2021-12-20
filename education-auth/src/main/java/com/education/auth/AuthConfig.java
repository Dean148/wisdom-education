package com.education.auth;

/**
 * @author zengjintao
 * @create_at 2021年11月26日 0026 13:59
 * @since version 1.0.4
 */
public class AuthConfig {

    private static final String DEFAULT_HEADERS = "Authorization";
    private static final String DEFAULT_SESSION_ID_PREFIX = "session:";

    /**
     * 默认token 请求头
     */
    private String headers = DEFAULT_HEADERS;

    /**
     * 是否允许一个账号同时在线
     */
    private boolean allowMoreOnline = false;

    /**
     * session id 会话前缀
     */
    private String sessionIdPrefix = DEFAULT_SESSION_ID_PREFIX;

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public boolean isAllowMoreOnline() {
        return allowMoreOnline;
    }

    public void setAllowMoreOnline(boolean allowMoreOnline) {
        this.allowMoreOnline = allowMoreOnline;
    }
}
