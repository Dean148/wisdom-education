package com.education.common.interceptor.validate;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/24 13:18
 */
public class ValidateBuilder {

    public static Validate build() {
        return new DefaultValidate();
    }
}
