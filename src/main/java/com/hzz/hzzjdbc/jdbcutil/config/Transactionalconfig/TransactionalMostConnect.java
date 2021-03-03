package com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig;

import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.transaction.TransactionDefinition;

import java.lang.annotation.*;
import java.lang.reflect.Method;
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
     * <p>Defaults to {@link Connection#TRANSACTION_REPEATABLE_READ}.
     * @see com.hzz.hzzjdbc.jdbcutil.searchmain.SearchExecuter#begintransaction(int, boolean) ()
     */
    int TransactionIsolation() default Connection.TRANSACTION_REPEATABLE_READ;
    /**
     * 设置超时时间(s)
     * @see TransactionalInterceptor#intercept(Object, Method, Object[], MethodProxy)
     */
    int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;
    /**
     * 设置捕获到的异常进行回滚，不配置则所有异常都回滚
     * @see TransactionalInterceptor#intercept(Object, Method, Object[], MethodProxy)
     */
    Class<? extends Throwable>[] rollbackFor() default {};

    /**
     * 可指定数据源进行回滚，不配置则所有数据库都回滚，主数据库名称为mysqlDao
     * 其他数据库  如 spring.datasource.mysql96.driverClassName 则名为mysql96
     * @see TransactionalInterceptor#intercept(Object, Method, Object[], MethodProxy)
     * @return
     */
    String[] DataSourcesNames() default {};
}
