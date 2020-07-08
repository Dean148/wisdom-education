package com.education.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 实体类基类
 * @author zengjintao
 * @version 1.0
 * @create_date 2020/7/8 17:29
 * @since 1.0.0
 */
public abstract class ModelBean<T extends ModelBean> {

    private final Map<String, Object> attrs = new HashMap();

    public void setAttr(String key, Object value) {
        attrs.put(key, value);
    }
}
