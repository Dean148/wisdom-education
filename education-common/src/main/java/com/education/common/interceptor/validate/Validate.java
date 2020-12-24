package com.education.common.interceptor.validate;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/24 9:55
 */
public interface Validate {

    /**
     * 校验是否通过
     * @return
     */
    default boolean validateEvent() {
       return false;
    }
}
