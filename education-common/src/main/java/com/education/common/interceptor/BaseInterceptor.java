package com.education.common.interceptor;

import com.education.common.cache.CacheBean;
import com.education.common.constants.AuthConstants;
import com.education.common.constants.CacheKey;
import com.education.common.constants.CacheTime;
import com.education.common.constants.SystemConstants;
import com.education.common.enums.PlatformEnum;
import com.education.common.model.JwtToken;
import com.education.common.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2018/12/22 19:43
 */
public abstract class BaseInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private CacheBean redisCacheBean;
    private final Set<String> platformSet = new HashSet();

    protected boolean checkHeader(HttpServletRequest request, HttpServletResponse response) {
        if (platformSet.size() == 0) {
            platformSet.add(PlatformEnum.SYSTEM_ADMIN.getHeaderValue());
            platformSet.add(PlatformEnum.SYSTEM_STUDENT.getHeaderValue());
        }

        String platform = request.getHeader(AuthConstants.PLATFORM);
        if (ObjectUtils.isEmpty(platform)) {
            logger.warn("请求头未携带:{}", AuthConstants.PLATFORM);
            Result.renderJson(response, Result.fail(ResultCode.UN_AUTH_ERROR_CODE, "请求头未携带:" + AuthConstants.PLATFORM));
            return false;
        }

        if (!platformSet.contains(platform)) {
            Result.renderJson(response, Result.fail(ResultCode.UN_AUTH_ERROR_CODE, AuthConstants.PLATFORM + ":请求头错误!"));
        }

        return checkToken(request, response);
    }

    /**
     * 校验token 是否合法
     * @param request
     * @param response
     * @return
     */
    protected boolean checkToken(HttpServletRequest request, HttpServletResponse response) {
        //获取token
        String token = request.getHeader(AuthConstants.AUTHORIZATION);
        String userId = jwtToken.parseTokenToString(token);
        if (ObjectUtils.isEmpty(userId)) { //token不存在 或者token失效
            logger.warn("token 不存在或者token: {}已失效", token);
            Result.renderJson(response, Result.fail(ResultCode.UN_AUTH_ERROR_CODE, "会话已过期,请重新登录"));
            return false;
        }
        this.refreshTokenIfNeed(token, userId, request, response);
        return true;
    }

    /**
     * 刷新token
     */
    private void refreshTokenIfNeed(String token, String userId, HttpServletRequest request, HttpServletResponse response) {
        long validTime = jwtToken.getTokenValidDate(token).getTime();
        long now = new Date().getTime();
        Object sessionCache;
        // 失效时间小于2分钟，重新创建token
        if (validTime > now && validTime - now < CacheTime.TWO_SECOND_MILLIS) {
            token = jwtToken.createToken(userId, CacheTime.ONE_HOUR_MILLIS);
            response.addHeader(AuthConstants.AUTHORIZATION, token);
            String platform = request.getHeader(AuthConstants.PLATFORM);
            if (PlatformEnum.SYSTEM_ADMIN.getHeaderValue().equals(platform)) {
                String sessionId = request.getSession().getId();
                sessionCache = redisCacheBean.get(SystemConstants.SESSION_KEY, sessionId);
                // 刷新shiro session
                redisCacheBean.put(SystemConstants.SESSION_KEY, sessionId, sessionCache, CacheTime.ONE_WEEK_SECOND);
            } else if (PlatformEnum.SYSTEM_STUDENT.getHeaderValue().equals(platform)) {
                sessionCache = redisCacheBean.get(CacheKey.STUDENT_USER_INFO_CACHE, token);
                // 将学员信息重新放入缓存
                redisCacheBean.put(CacheKey.STUDENT_USER_INFO_CACHE, userId, sessionCache, CacheTime.ONE_WEEK_SECOND);
            }
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
