package com.education.api.config.interceptor;

import com.education.auth.AuthUtil;
import com.education.auth.session.UserSession;
import com.education.business.interceptor.BaseInterceptor;
import com.education.business.service.education.StudentInfoService;
import com.education.common.enums.LoginEnum;
import com.education.common.exception.BusinessException;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 学员端api 拦截器
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 21:38
 */
@Component
public class StudentAuthInterceptor extends BaseInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.checkHeader(request);
        UserSession userSession = AuthUtil.getSession(LoginEnum.STUDENT.getValue());
        if (ObjectUtils.isEmpty(userSession)) {
            throw new BusinessException(new ResultCode(ResultCode.UN_AUTH_ERROR_CODE, "会话已过期，请重新登录!"));
        }
        return checkToken(LoginEnum.STUDENT.getValue(), response);
    }
}
