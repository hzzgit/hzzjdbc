package com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.searchmain.MysqlUtil;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/11/4 17:31
 */
public class TransactionalInterceptor implements MethodInterceptor {

    private Object finalBean;

    private Map<String, Byte> methodName = new HashMap<>();

    private ApplicationContext applicationContext;

    public TransactionalInterceptor(Object finalBean, Map<String, Byte> methodName, ApplicationContext applicationContext) {
        this.finalBean = finalBean;
        this.methodName = methodName;
        this.applicationContext = applicationContext;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        String name1 = method.getName();
        System.out.println("方法名:" + name1);

        if (methodName.containsKey(name1)) {
            System.out.println("动态代理前");
            TransactionalMostConnect annotation = method.getAnnotation(TransactionalMostConnect.class);
            if (annotation != null) {
                List<MysqlDao> mysqlDaoList = new ArrayList<>();
                String[] beanNamesForAnnotation = applicationContext.getBeanNamesForType(MysqlDao.class);
                System.out.println("动态代理注解上面的内容为:");
                String[] strings = annotation.DataSourcesNames();

                if (strings != null && strings.length > 0) {
                    for (String MysqlDaoName : strings) {
                        if(applicationContext.containsBean(MysqlDaoName)){
                            mysqlDaoList.add((MysqlDao) applicationContext.getBean(MysqlDaoName));
                        }
                    }
                } else {
                    for (String MysqlDaoName : beanNamesForAnnotation) {
                        mysqlDaoList.add((MysqlDao) applicationContext.getBean(MysqlDaoName));
                    }
                }
                try {
                    openCon(mysqlDaoList);
                    Object o1 = method.invoke(finalBean, objects);
                    System.out.println("动态代理后");
                    commitCon(mysqlDaoList);
                    return o1;
                } catch (Exception e) {
                    System.out.println("动态代理执行方法异常，进行事务回滚操作");
                    Class<? extends Throwable>[] classes = annotation.rollbackFor();
                    if(e.equals(classes)){
                        System.out.println("事务捕获到的异常相同");
                    }
                    try {
                        rollback(mysqlDaoList);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }

        }
        return null;
    }

    private void openCon(List<MysqlDao> mysqlDaoList) {
        for (MysqlDao mysqlDao : mysqlDaoList) {
            MysqlUtil mysqlUtil = mysqlDao.getMysqlUtil();
            //这边打开事务
            mysqlUtil.begintransaction(false);
        }
    }

    private void commitCon(List<MysqlDao> mysqlDaoList) {
        for (MysqlDao mysqlDao : mysqlDaoList) {
            MysqlUtil mysqlUtil = mysqlDao.getMysqlUtil();
            //这边结束整个sql，并最后提交或者回滚
            mysqlUtil.endtransaction();
        }
    }

    private void rollback(List<MysqlDao> mysqlDaoList) {
        for (MysqlDao mysqlDao : mysqlDaoList) {
            MysqlUtil mysqlUtil = mysqlDao.getMysqlUtil();
            //这边结束整个sql，并最后提交或者回滚
            mysqlUtil.rollback();
        }
    }
}
