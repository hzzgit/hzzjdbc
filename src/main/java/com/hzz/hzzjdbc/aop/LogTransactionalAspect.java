package com.hzz.hzzjdbc.aop;

import com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig.TransactionalMostConnect;
import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.searchmain.MysqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2021/4/28 11:02
 */
@Component
@Aspect
@Order(1)
@Slf4j
public class LogTransactionalAspect {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 这边可以直接定义注解，在bean初始化之后会根据这个判断是否要生成代理类
     */
    @Pointcut("@annotation(com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig.TransactionalMostConnect)")
    public void TransactionalAspect() {
    }


    @Around("@annotation(transactionalMostConnect)")
    public Object around(ProceedingJoinPoint pjp, TransactionalMostConnect transactionalMostConnect) throws Throwable {

        System.out.println("动态代理前,创建连接:" + pjp);
        List<MysqlDao> mysqlDaoList = new ArrayList<>();
        String[] strings = transactionalMostConnect.DataSourcesNames();
        if (strings != null && strings.length > 0) {
            for (String MysqlDaoName : strings) {
                if (applicationContext.getBean(MysqlDaoName) != null) {
                    mysqlDaoList.add((MysqlDao) applicationContext.getBean(MysqlDaoName));
                }
            }
        } else {
            String[] beanNamesForType = applicationContext.getBeanNamesForType(MysqlDao.class);
            for (String s : beanNamesForType) {
                mysqlDaoList.add((MysqlDao) applicationContext.getBean(s));
            }
        }
        Object proceed = null;
        try {
            openCon(mysqlDaoList, transactionalMostConnect);
            proceed = pjp.proceed();
            commitCon(mysqlDaoList);
            System.out.println("动态代理后,提交连接成功:" + pjp);
        } catch (Throwable e) {
            System.out.println("动态代理执行方法异常，进行事务回滚操作,异常为" + e);
            //如果是运行时异常，直接回滚并抛出
            if (e != null && e.getCause() != null && e.getCause().getClass().equals(RuntimeException.class)) {
                rollback(mysqlDaoList);
                throw e.getCause();
            }
            Class<? extends Throwable>[] classes = transactionalMostConnect.rollbackFor();
            if (classes != null && classes.length > 0) {
                for (Class<? extends Throwable> aClass : classes) {
                    if (e.getCause().getClass().equals(aClass)) {
                        try {
                            rollback(mysqlDaoList);
                        } catch (Exception ex) {
                            log.error("回滚失败", e);
                        }
                        break;
                    }
                }
            } else {
                try {
                    rollback(mysqlDaoList);
                } catch (Exception ex) {
                    log.error("回滚失败", e);
                }

            }
            throw e;
        }

        return proceed;

    }


    private void openCon(List<MysqlDao> mysqlDaoList, TransactionalMostConnect transactionalMostConnect) {
        for (MysqlDao mysqlDao : mysqlDaoList) {
            MysqlUtil mysqlUtil = mysqlDao.getMysqlUtil();
            //这边打开事务
            Connection con = mysqlUtil.begintransaction(transactionalMostConnect.TransactionIsolation(), false);
            mysqlDao.setCon(con);
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
