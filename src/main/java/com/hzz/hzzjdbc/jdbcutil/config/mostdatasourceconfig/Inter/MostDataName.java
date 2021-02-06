package com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig.Inter;

import java.lang.annotation.*;

/**
 * @author ：hzz
 * @description：放在数据库连接注释的字段上面，用来确认注入的是哪个数据库
 * @date ：2021/2/6 15:59
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MostDataName {

    String DataSourcesName() default "";
}
