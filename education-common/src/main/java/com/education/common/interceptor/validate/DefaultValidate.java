package com.education.common.interceptor.validate;

import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/24 12:14
 */
public class DefaultValidate extends AbstractValidate {

    @Override
    boolean doValidateEvent(Map<String, Object> params) {
        return false;
    }
}
