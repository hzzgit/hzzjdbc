package com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/11/4 17:31
 */
public class TransactionalInterceptor implements MethodInterceptor {

    private Object finalBean;

    private Map<String, Byte> methodName = new HashMap<>();

    public TransactionalInterceptor(Object finalBean, Map<String, Byte> methodName) {
        this.finalBean = finalBean;
        this.methodName = methodName;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        String name1 = method.getName();
        System.out.println("方法名:" + name1);
        if (methodName.containsKey(name1)) {
            System.out.println("动态代理前");

            try {
                Object o1 = method.invoke(finalBean, objects);
                System.out.println("动态代理后");
                return o1;
            } catch (Exception e) {
                System.out.println("动态代理执行方法异常");
            }

        }
        Object o1 = method.invoke(finalBean, objects);
        return o1;
    }
}
