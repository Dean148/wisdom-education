package com.education.auth;

import com.education.auth.annotation.Logical;
import com.education.auth.annotation.RequiresPermissions;
import com.education.auth.exception.PermissionException;

import java.lang.reflect.Method;
import java.util.function.Consumer;

/**
 * 权限策略
 * @author zengjintao
 * @create_at 2021年11月26日 0026 17:13
 * @since version 1.0.4
 */
public class PermissionStrategy {

    public static Consumer<Method> checkMethodAnnotation = (method) -> {
        validatePermission(method);
    };


    public static void validatePermission(Method method) {
        RequiresPermissions permission = method.getAnnotation(RequiresPermissions.class);
        if (permission == null) {
            return;
        }
        String[] values = permission.value();
        Logical mode = permission.logical();
        boolean hasPermission = true;
        switch (mode) {
            case AND:
                hasPermission = checkPermissionAnd(values, permission.loginType());
                break;
            case OR:
                hasPermission = checkPermissionOr(values, permission.loginType());
        }
        if (!hasPermission) {
            throw new PermissionException();
        }
    }

    private static boolean checkPermissionOr(String[] values, String loginType) {
        boolean hasPermission = false;
        for (String value : values) {
            hasPermission = AuthUtil.hasPermission(loginType, value);
            if (hasPermission) {
                break;
            }
        }
        return hasPermission;
    }

    private static boolean checkPermissionAnd(String[] values, String loginType) {
        boolean hasPermission = false;
        for (String value : values) {
            hasPermission = AuthUtil.hasPermission(loginType, value);
            if (!hasPermission) {
                break;
            }
        }
        return hasPermission;
    }
}
