package com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author ：hzz
 * @description：事务管理的配置类
 * @date ：2020/11/3 11:13
 */
public class TransactionalHandler implements InvocationHandler {

    //通过构造方法接受一个没有被代理的原来的对象
    //通过下面的方法名的反射找到这个对象对应方法
    private Object target;

    public TransactionalHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String classname = target.getClass().getName();
        String methodName = method.getName();
        System.out.println(classname + "." + methodName + "方法开始执行");
        //这里实际是Method类通过方法名反射调用了原方法(addone)
        Object value = method.invoke(target, args);
        System.out.println(classname + "." + methodName + "方法执行完毕");
        return value;
    }
}
