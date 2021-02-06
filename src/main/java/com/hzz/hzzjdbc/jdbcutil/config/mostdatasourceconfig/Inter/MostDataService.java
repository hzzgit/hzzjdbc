package com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig.Inter;

import java.lang.annotation.*;

/**
 * @author ：hzz
 * @description：用来放在service类上面，代表类是多数据源托管
 * @date ：2021/2/6 15:58
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MostDataService {
}
