package com.education.common.interceptor.validate;


import java.util.*;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/24 13:18
 */
public class ValidateManager {

    private final List<Validate> validateList = new ArrayList();

    private static final ValidateManager validateManager = new ValidateManager();

    private ValidateManager() {
        this.registerValidate();
    }

    private void registerValidate() {
        validateList.add(new EmptyValidate());
        validateList.add(new CollectionValidate());
        validateList.add(new PropertyValidate());
        validateList.add(new RegexValidate());
    }

    public List<Validate> getValidateList() {
        return validateList;
    }

    public static ValidateManager build() {
        return validateManager;
    }
}
