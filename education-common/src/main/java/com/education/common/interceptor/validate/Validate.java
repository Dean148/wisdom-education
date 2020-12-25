package com.education.common.interceptor.validate;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/24 9:55
 */
public interface Validate {


    void setParamValue(Object paramValue);

    /**
     * 参数校验方法
     * @return
     */
    boolean validate();


    default void setValidateProperty() {

    }

}
