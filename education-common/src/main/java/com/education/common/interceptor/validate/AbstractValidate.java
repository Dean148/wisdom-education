package com.education.common.interceptor.validate;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/24 9:56
 */
public abstract class AbstractValidate implements Validate {

    private Object paramValue;

    @Override
    public void setParamValue(Object paramValue) {
        this.paramValue = paramValue;
    }

    protected Object getParamValue() {
        return paramValue;
    }
}
