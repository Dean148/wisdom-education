package com.education.common.interceptor.validate;

import java.util.Map;

/**
 * 数组类型校验
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/24 9:57
 */
public class ArrayValidate extends AbstractValidate {


    public ArrayValidate(String jsonParam) {
        super(jsonParam);
    }

    @Override
    boolean doValidateEvent(Map<String, Object> params) {
        return false;
    }
}
