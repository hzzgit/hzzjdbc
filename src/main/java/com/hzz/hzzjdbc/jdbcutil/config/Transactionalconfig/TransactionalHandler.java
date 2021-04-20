package com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author ：hzz
 * @description：事务管理的配置类,这边如果是有实现接口的调用方式
 * @date ：2020/11/3 11:13
 */
public class TransactionalHandler implements InvocationHandler {

    //这边直接使用适配器模式以组合的形式加入进来
    private TransactionalInterceptor transactionalInterceptor;

    public void setTransactionalInterceptor(TransactionalInterceptor transactionalInterceptor) {
        this.transactionalInterceptor = transactionalInterceptor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object intercept = transactionalInterceptor.intercept(proxy, method, args, null);
        return intercept;
    }
}
