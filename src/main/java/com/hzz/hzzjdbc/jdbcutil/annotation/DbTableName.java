package com.hzz.hzzjdbc.jdbcutil.annotation;

import java.lang.annotation.*;

/**
 * 用来确定表名和库民
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DbTableName {
    String value() default "";

    String resultMap() default "";
}
