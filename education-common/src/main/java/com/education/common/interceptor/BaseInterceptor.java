package com.education.common.interceptor;

import com.education.common.cache.CacheBean;
import com.education.common.constants.AuthConstants;
import com.education.common.constants.CacheTime;
import com.education.common.constants.Constants;
import com.education.common.model.JwtToken;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.RequestUtils;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2018/12/22 19:43
 */
public abstract class BaseInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);

    @Autowired
    private CacheBean redisCacheBean;
    @Autowired
    private JwtToken adminJwtToken;
    @Autowired
    private DefaultSessionManager sessionManager;

    /**
     * 校验token 是否合法
     * @param jwtToken
     * @param request
     * @param response
     * @return
     */
    protected boolean checkToken(JwtToken jwtToken, HttpServletRequest request, HttpServletResponse response) {
        //获取token
        String token = request.getHeader(AuthConstants.AUTHORIZATION);
        String userId = jwtToken.parseTokenToString(token);
        if (ObjectUtils.isEmpty(token) || ObjectUtils.isEmpty(userId)) { //token不存在 或者token失效
            logger.warn("token 不存在或者token: {}已失效", token);
            Result.renderJson(response, Result.fail(ResultCode.UN_AUTH_ERROR_CODE, "用户未认证"));
            return false;
        }
        this.refreshTokenIfNeed(token, userId, request, response);
        return true;
    }

    /**
     * 刷新session
     * @param request
     */
    private void refreshTokenIfNeed(String token, String value, HttpServletRequest request, HttpServletResponse response) {
        long validTime = adminJwtToken.getTokenValidDate(token).getTime();
        long now = new Date().getTime();
        // 失效时间小于五分钟，重新刷新token和shiro session
        if (validTime - now < CacheTime.FIVE_SECOND_MILLIS) {
            token = adminJwtToken.createToken(value, CacheTime.ONE_HOUR_MILLIS);
            response.addHeader(AuthConstants.AUTHORIZATION, token);
            // 刷新shiro session
            sessionManager.setGlobalSessionTimeout(CacheTime.ONE_HOUR_MILLIS);
        }
    }


    /**
     * 获取json 参数值
     * @param request
     * @return
     */
    protected String readData(HttpServletRequest request) {
       return RequestUtils.readData(request);
    }
}
