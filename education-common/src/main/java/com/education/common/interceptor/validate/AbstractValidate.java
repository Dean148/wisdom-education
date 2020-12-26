package com.education.common.interceptor.validate;


import com.education.common.annotation.Param;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/24 9:56
 */
public abstract class AbstractValidate implements Validate {

    private Param param;

    public void setParam(Param param) {
        this.param = param;
    }

    public Param getParam() {
        return param;
    }
}
