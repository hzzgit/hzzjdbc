package com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig;

import com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig.MostDataSourceProcessInter;
import com.hzz.hzzjdbc.jdbcutil.util.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
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
@Slf4j
public class TransactionalProcesser implements CommandLineRunner, ApplicationContextAware, EnvironmentAware {

    /*用来存放类名和动态代理之后的类*/
    private ConcurrentMap<Class, Object> beansFactory = new ConcurrentHashMap<>();

    private MostDataSourceProcessInter mostDataSourceProcessInter;

    public Object getBean(Class cla) {
        return beansFactory.get(cla);
    }

    public TransactionalProcesser(MostDataSourceProcessInter mostDataSourceProcessInter) {
        this.mostDataSourceProcessInter = mostDataSourceProcessInter;
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

                    if (arg) {
                        //如果是有事务管理的注解的话，那么就执行反向代理,这边是另一种带有注解的动态代理方式
//                        //根据对象的类获取类加载器
                        Enhancer en = new Enhancer();
                        en.setSuperclass(aClass);
                        //这边定义回调
                        Object finalBean = bean;
                        en.setCallback(new TransactionalInterceptor(finalBean,methodName,mostDataSourceProcessInter));
                        Object o = en.create();
                        //这边是动态代理之后的类的存放，这时候已经可以对这个类进行动态代理了，
                        beansFactory.put(aClass, o);
                    }
                }
            }
        }

        /*将匹配到的类进行注入*/
        inject(Controller.class);
        inject(RestController.class);
       // inject(Service.class);
        log.debug("动态代理自定义多数据源事务成功");


    }


    private void inject(Class c){
        String[] beanNamesForAnnotation2 = applicationContext.getBeanNamesForAnnotation(c);
        dongtaizhuru(beanNamesForAnnotation2);
    }
    /**
     * 对已有的注释的接口类进行注入带有动态代理改造之后的类
     */
    private void dongtaizhuru( String[] beanNamesForAnnotation){
        if (beanNamesForAnnotation != null && beanNamesForAnnotation.length > 0) {
            for (String beannames : beanNamesForAnnotation) {
                Object bean = applicationContext.getBean(beannames);
                Field[] fields = bean.getClass().getDeclaredFields();
                for (Field field : fields) {
                    Autowired annotation = field.getAnnotation(Autowired.class);
                    if(annotation!=null){
                        String name = field.getName();
                        Class<?> type = field.getType();
                        if(beansFactory.containsKey(type)){
                            Object o = beansFactory.get(type);
                            try {
                                ReflectionUtil.setValue(bean,name,o);
                                log.debug("动态代理对bean:"+bean+",参数名:"+name+"注入了"+o.getClass());
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
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


}
