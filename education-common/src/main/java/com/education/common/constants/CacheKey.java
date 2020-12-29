package com.education.common.constants;

import com.education.common.config.EntityParamGenerator;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/29 10:17
 */
public interface CacheKey {

    String KEY_GENERATOR_BEAN_NAME = "entityParamGenerator";
    /**
     * 字典类型缓存 cacheName
     */
    String SYSTEM_DICT = "system:dict";

    String SYSTEM_DICT_VALUE = "system:dict:value";
}
