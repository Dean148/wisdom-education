package com.education.common.model;

/**
 * @author zengjintao
 * @create_at 2021年10月25日 0025 17:26
 * @since version 1.6.5
 */
public class UserHold {

    private static final ThreadLocal<Boolean> REMEMBER_ME = new ThreadLocal<>();

    public static void putRememberMe(boolean value) {
        REMEMBER_ME.set(value);
    }

    public static boolean getRememberMe() {
        return REMEMBER_ME.get();
    }
}
