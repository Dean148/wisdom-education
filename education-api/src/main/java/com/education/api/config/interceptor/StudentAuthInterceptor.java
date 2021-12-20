package com.education.api.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.education.business.service.education.StudentInfoService;
import com.education.common.exception.BusinessException;
import com.education.common.interceptor.BaseInterceptor;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.entity.StudentInfo;
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

    @Autowired
    private StudentInfoService studentInfoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if (StrUtil.isNotBlank(method) && "OPTIONS".equalsIgnoreCase(method)) {
            return true;
        }
        super.checkHeader(request);
        StudentInfo studentInfo = studentInfoService.getStudentInfo();
        if (ObjectUtils.isEmpty(studentInfo)) {
            throw new BusinessException(new ResultCode(ResultCode.UN_AUTH_ERROR_CODE, "会话已过期，请重新登录!"));
        }
        return checkToken(request, response);
    }
}
