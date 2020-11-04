package com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig;

import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ：hzz
 * @description：事务管理的配置类
 * @date ：2020/11/3 11:13
 */
public class TransactionalProcesser implements CommandLineRunner, ApplicationContextAware, EnvironmentAware {

    private ConcurrentMap<Class, Object> beansFactory = new ConcurrentHashMap<>();

    public Object getBean(Class cla) {
        return beansFactory.get(cla);
    }


    private ApplicationContext applicationContext;
    private Environment environment;


    @Override
    public void run(String... args) throws Exception {
        String[] beanNamesForAnnotation = applicationContext.getBeanNamesForAnnotation(Service.class);
        if (beanNamesForAnnotation != null && beanNamesForAnnotation.length > 0) {
            for (String beannames : beanNamesForAnnotation) {
                Object bean = applicationContext.getBean(beannames);
                if (bean != null) {
                    Class<?> aClass = bean.getClass();
                    String name = aClass.getName();
                    if (name.indexOf("$") > -1) {
                        name = name.substring(0, name.indexOf("$"));
                    }
                    aClass = Class.forName(name);
                    Method[] methods = aClass.getMethods();
                    boolean arg = false;
                    Map<String, Byte> methodName = new HashMap<>();
                    if (methods != null && methods.length > 0) {
                        for (Method method : methods) {
                            TransactionalMostConnect annotation = method.getAnnotation(TransactionalMostConnect.class);
                            if (annotation != null) {
                                arg = true;
                                methodName.put(method.getName(), (byte) 1);
                            }
                        }
                    }

                    if (arg) {//如果是有事务管理的注解的话，那么就执行反向代理
//                        //根据对象的类获取类加载器
//                        ClassLoader classLoader = aClass.getClassLoader();
//                        //获取被代理对象说实现的所有接口
//                        Class<?>[] interfaces = aClass.getInterfaces();
//                        //新建代理对象,里面参数需要(类加载器,一个对象所实现的接口,InvocationHandler接口类的对象)
//                        Proxy.newProxyInstance(classLoader, interfaces, new TransactionalHandler(bean));

                        Enhancer en = new Enhancer();
                        en.setSuperclass(aClass);
                        //这边定义回调
                        Object finalBean = bean;
                        en.setCallback(new MethodInterceptor() {
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
                                return o;
                            }
                        });
                        Object o = en.create();
                        beansFactory.put(aClass, o);
                    }
                }

            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * 注入到spring的bean销毁的时候进行的方法
     */
    public void destroy() {

    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext con = new AnnotationConfigApplicationContext("com.hzz.hzzjdbc");
        Object test = con.getBean("test");
        System.out.println(1);
    }
}
