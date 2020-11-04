package com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig;

import org.springframework.transaction.annotation.Propagation;

import java.lang.annotation.*;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/11/3 11:09
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TransactionalMostConnect {


    /**
     * The transaction propagation type.
     * <p>Defaults to {@link Propagation#REQUIRED}.
     * @see org.springframework.transaction.interceptor.TransactionAttribute#getPropagationBehavior()
     */
    Propagation propagation() default Propagation.REQUIRED;



    Class<? extends Throwable>[] rollbackFor() default {};


    String[] DataSourcesNames() default {};
}
