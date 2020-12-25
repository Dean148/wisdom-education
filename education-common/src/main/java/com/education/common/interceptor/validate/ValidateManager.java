package com.education.common.interceptor.validate;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/24 13:18
 */
public class ValidateManager {

    private final Map<String, Validate> validateMap = new HashMap<>();

    private static ValidateManager validateManager = new ValidateManager();

    private ValidateManager() {
        registerValidate(String.class, new EmptyValidate());
        registerValidate(Integer.class, new EmptyValidate());
        registerValidate(Collection.class, new ArrayValidate());
    }

    private void registerValidate(Class<?> typeClass, Validate validate) {
        this.validateMap.put(typeClass.getName(), validate);
    }

    public Validate getValidate(Object paramValue) {
        Validate validate = validateMap.get(paramValue.getClass());
        validate.setParamValue(paramValue);
        return validate;
    }

    public static ValidateManager build() {
        return validateManager;
    }
}
