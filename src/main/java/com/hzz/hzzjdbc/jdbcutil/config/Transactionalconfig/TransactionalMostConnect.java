package com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;

import java.lang.annotation.*;
import java.sql.Connection;

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
     * 设置事务传播级别
     * <p>Defaults to {@link Propagation#REQUIRED}.
     * @see org.springframework.transaction.interceptor.TransactionAttribute#getPropagationBehavior()
     */
    int TransactionIsolation() default Connection.TRANSACTION_REPEATABLE_READ;
    /**
     * 设置超时时间(s)
     */
    int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;
    /**
     * 设置捕获到的异常进行回滚，不配置则所有异常都回复你
     * @return
     */
    Class<? extends Throwable>[] rollbackFor() default {};

    /**
     * 可指定数据源进行回滚，不配置则所有数据库都回滚，主数据库名称为mysqlDao
     * 其他数据库  如 spring.datasource.mysql96.driverClassName 则名为mysql96
     *
     * @return
     */
    String[] DataSourcesNames() default {};
}
