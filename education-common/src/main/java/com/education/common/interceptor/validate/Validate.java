package com.education.common.interceptor.validate;

import com.education.common.annotation.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/24 9:55
 */
public interface Validate {


    /**
     * 判断value 类型
     * @param paramValue
     * @return
     */
    boolean supportParamType(Object paramValue);

    /**
     * 执行参数校验
     * @param request
     * @param response
     * @param errorCode
     * @param errorMsg
     * @param paramValue
     */
    void validateParam(HttpServletRequest request, HttpServletResponse response,
                       Integer errorCode, String errorMsg, Object paramValue);

    void setParam(Param param);

    Param getParam();
}
