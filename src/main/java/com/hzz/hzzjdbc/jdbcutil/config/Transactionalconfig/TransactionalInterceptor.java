package com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig;

import com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig.Thread.TransactionallCallBack;
import com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig.MostDataSourceProcessInter;
import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/11/4 17:31
 */
@Slf4j
public class TransactionalInterceptor implements MethodInterceptor {

    private Object finalBean;

    private Map<String, Byte> methodName = new HashMap<>();

    private MostDataSourceProcessInter mostDataSourceProcessInter;

    public TransactionalInterceptor(Object finalBean, Map<String, Byte> methodName, MostDataSourceProcessInter mostDataSourceProcessInter) {
        this.finalBean = finalBean;
        this.methodName = methodName;
        this.mostDataSourceProcessInter = mostDataSourceProcessInter;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        String name1 = method.getName();
        System.out.println("方法名:" + name1);
        if (methodName.containsKey(name1)) {
            System.out.println("动态代理前");
            TransactionalMostConnect annotation = method.getAnnotation(TransactionalMostConnect.class);
            List<MysqlDao> mysqlDaoList = new ArrayList<>();
            String[] strings = annotation.DataSourcesNames();
            if (strings != null && strings.length > 0) {
                for (String MysqlDaoName : strings) {
                    if (mostDataSourceProcessInter.getMysqlDao(MysqlDaoName) != null) {
                        mysqlDaoList.add(mostDataSourceProcessInter.getMysqlDao(MysqlDaoName));
                    }
                }
            } else {
                mysqlDaoList = mostDataSourceProcessInter.getMysqlDaoList();
            }
            int timeout = annotation.timeout();
            Object o1 = null;
            TransactionallCallBack transactionallCallBack = new TransactionallCallBack(method, objects, finalBean, mysqlDaoList, annotation);
            FutureTask futureTask = new FutureTask(transactionallCallBack);
            Thread thread = new Thread(futureTask);
            thread.start();
            if (timeout > 0) {
                try {
                    o1 = futureTask.get(timeout, TimeUnit.SECONDS);
                } catch (TimeoutException e) {
                    //这边试图去结束运行的线程，避免运行结束提交
                    transactionallCallBack.setNotcommit();
                    futureTask.cancel(true);
                    rollback(mysqlDaoList);
                    throw new Exception("事务执行超时，进行回滚", new RuntimeException("事务执行超时，进行回滚"));
                }
            } else {
                o1 = futureTask.get();
            }
            return o1;
        } else {
            Object o1 = null;
            try {
                o1 = method.invoke(finalBean, objects);
            } catch (Exception e) {
                throw e.getCause();
            }
            return o1;
        }

    }

    private void rollback(List<MysqlDao>  mysqlDaoList) {
        for (MysqlDao mysqlDao : mysqlDaoList) {
            mysqlDao.rollback();
        }
    }
}
