package com.education.canal;

import java.lang.annotation.*;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/8/21 20:21
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CanalTable {
    String table();
}
