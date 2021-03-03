package com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig.Thread;

import com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig.TransactionalMostConnect;
import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.searchmain.MysqlUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2021/3/3 9:56
 */
@Slf4j
public class TransactionallCallBack implements Callable<Object> {

    private Method method;
    private Object[] objects;
    private Object finalBean;

    /**
     * 是否提交,如果超时回滚就不执行下去了
     */
    private boolean iscommit=true;

    private List<MysqlDao> mysqlDaoList;

    private TransactionalMostConnect annotation;

    public TransactionallCallBack(Method method, Object[] objects,
                                  Object finalBean, List<MysqlDao> mysqlDaoList,
                                  TransactionalMostConnect annotation) {
        this.method = method;
        this.objects = objects;
        this.finalBean = finalBean;
        this.mysqlDaoList = mysqlDaoList;
        this.annotation = annotation;
    }

    public void setNotcommit() {
        this.iscommit = false;
    }

    @SneakyThrows
    @Override
    public Object call() throws Exception {
        try {
            openCon();
            Object o1 = method.invoke(finalBean, objects);
            if(iscommit) {
                commitCon();
            }
            return o1;
        } catch (Exception e) {
            System.out.println("动态代理执行方法异常，进行事务回滚操作,异常为" + e.getCause());
            //如果是运行时异常，直接回滚并抛出
            if (e.getCause().getClass().equals(RuntimeException.class)) {
                rollback();
                throw e.getCause();
            }
            Class<? extends Throwable>[] classes = annotation.rollbackFor();
            if (classes != null && classes.length > 0) {
                for (Class<? extends Throwable> aClass : classes) {
                    if (e.getCause().getClass().equals(aClass)) {
                        try {
                            rollback();
                        } catch (Exception ex) {
                            log.error("回滚失败", e);
                        }
                        break;
                    }
                }
            } else {
                try {
                    rollback();
                } catch (Exception ex) {
                    log.error("回滚失败", e);
                }

            }
            throw e.getCause();
        }
    }

    private void openCon() {
        for (MysqlDao mysqlDao : mysqlDaoList) {
            MysqlUtil mysqlUtil = mysqlDao.getMysqlUtil();
            //这边打开事务
            Connection con = mysqlUtil.begintransaction(annotation.TransactionIsolation(), false);
            mysqlDao.setCon(con);
        }
    }

    private void commitCon() {
        for (MysqlDao mysqlDao : mysqlDaoList) {
            MysqlUtil mysqlUtil = mysqlDao.getMysqlUtil();
            //这边结束整个sql，并最后提交或者回滚
            mysqlUtil.endtransaction();
        }
    }

    private void rollback() {
        for (MysqlDao mysqlDao : mysqlDaoList) {
            MysqlUtil mysqlUtil = mysqlDao.getMysqlUtil();
            //这边结束整个sql，并最后提交或者回滚
            mysqlUtil.rollback();
        }
    }

}
