package com.education.common.exception;

import com.education.common.utils.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 简述
 *
 * @Description ：全局异常捕获
 * @ Author      :  ZhangXu
 * @ CreateDate  :  2022/3/11 14:24
 * @ Exception   :
 */

@ControllerAdvice
public class BusinessExceptionUtil {

    /**
     * 自定义全局类型异常捕获
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public ResultCode errorHander(BusinessException e) {
        //ResultCode[code,message]捕获反馈给客户端
        return e.getResultCode();
    }
}
