package com.education.auth;

import com.education.auth.annotation.Mode;
import com.education.auth.annotation.Permission;

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
        Permission permission = method.getAnnotation(Permission.class);
        String[] values = permission.value();
        Mode mode = permission.mode();
        switch (mode) {
            case AND:
                checkPermissionAnd(values);
                break;
            case OR:
                checkPermissionOr(values);
        }
    }

    private static boolean checkPermissionOr(String[] values) {
        boolean hasPermission = false;
        for (String value : values) {
            hasPermission = AuthUtil.hasPermission(value);
            if (hasPermission) {
                break;
            }
        }
        return hasPermission;
    }

    private static boolean checkPermissionAnd(String[] values) {
        boolean hasPermission = false;
        for (String value : values) {
            hasPermission = AuthUtil.hasPermission(value);
            if (!hasPermission) {
                break;
            }
        }
        return hasPermission;
    }
}
