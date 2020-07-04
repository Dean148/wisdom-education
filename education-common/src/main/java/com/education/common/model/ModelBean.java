package com.education.common.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_date 2020/7/4 10:17
 * @since 1.0.0
 */
public abstract class ModelBean<T extends ModelBean> implements Serializable {

    private final Map<String, Object> attrs = new HashMap();

    public void setAttr(String key, Object value) {
        attrs.put(key, value);
    }
}
