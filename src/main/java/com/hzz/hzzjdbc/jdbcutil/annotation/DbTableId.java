package com.hzz.hzzjdbc.jdbcutil.annotation;


import java.lang.annotation.*;

/**
 * 用来确定表的主键是哪个
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DbTableId {
}
