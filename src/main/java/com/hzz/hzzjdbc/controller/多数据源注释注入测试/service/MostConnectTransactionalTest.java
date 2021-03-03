package com.hzz.hzzjdbc.controller.多数据源注释注入测试.service;

import com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig.TransactionalMostConnect;
import com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig.MostDataSourceProcessInter;
import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/10/29 17:11
 */
@Service
public class MostConnectTransactionalTest {

    @Autowired
    private MostDataSourceProcessInter mostDataSourceProcessInter;

    @Autowired
    private MysqlDao mysqlDao;

    public String test(String name) throws SQLException {

        String s = mysqlDao.queryFirstValToString("select name from student limit 1");
//        throw  new SQLException("");
        return s;
    }

    /*注释这边还不能支持多数据源的事务，仅能用封装的方法进行*/
    @Transactional(rollbackFor = Exception.class)
//    @TransactionalMostConnect(DataSourcesNames ={"datasource","mysql96","mysql45"},rollbackFor = Exception.class)
    @TransactionalMostConnect(DataSourcesNames ={"mysqlDao","mysql96","mysql45"},
            rollbackFor = {Exception.class,RuntimeException.class,SQLException.class},
            TransactionIsolation = Connection.TRANSACTION_REPEATABLE_READ,timeout = 10)
    public void testrollback() throws SQLException {
        MysqlDao datasource = mostDataSourceProcessInter.getMainMysqlDao();
        datasource.excutesql("update student set name ='测试11' where id='1263264001' ");

        MysqlDao datasource2 = mostDataSourceProcessInter.getMysqlDao("mysql96");
        datasource2.excutesql("update department set name ='测试22'  where depId ='117440629'");

        MysqlDao datasource3 = mostDataSourceProcessInter.getMysqlDao("mysql45");
        datasource3.excutesql("update department set name ='测试11' where depId='117445961' ");

        throw  new SQLException("");
    }


}
