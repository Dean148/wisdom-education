package com.education.common.interceptor.validate;

import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/24 9:56
 */
public abstract class AbstractValidate implements Validate {

    @Override
    public boolean validateEvent() {
        return false;
    }

    abstract boolean doValidateEvent(Map<String, Object> params);
}
