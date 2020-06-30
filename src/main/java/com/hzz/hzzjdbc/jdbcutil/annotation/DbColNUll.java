package com.hzz.hzzjdbc.jdbcutil.annotation;

import java.lang.annotation.*;

/**
 * 用来忽略无用字段
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DbColNUll {

}
