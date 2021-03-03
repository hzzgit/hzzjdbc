package com.hzz.hzzjdbc.controller.多数据源注释注入测试.service;

import com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig.TransactionalMostConnect;
import com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig.MostDataSourceProcessInter;
import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;


/**
 * 业务逻辑处理类
 */
@Service
public class MostConnectTransactionalTest {
    /**
     * 多数据源获取类
     */
    @Autowired
    private MostDataSourceProcessInter mostDataSourceProcessInter;

//    @TransactionalMostConnect(DataSourcesNames ={"mysqlDao","mysql96","mysql45"},
//            rollbackFor = {Exception.class,RuntimeException.class,SQLException.class},
//            TransactionIsolation = Connection.TRANSACTION_REPEATABLE_READ,timeout = 10)
    @TransactionalMostConnect
    public void testrollback() throws SQLException {
        MysqlDao datasource = mostDataSourceProcessInter.getMainMysqlDao();
        datasource.excutesql("update student set name ='测试11' where id='1263264001' ");

        MysqlDao mysql96 = mostDataSourceProcessInter.getMysqlDao("mysql96");
        mysql96.excutesql("update department set name ='测试22'  where depId ='117440629'");

        MysqlDao mysql45 = mostDataSourceProcessInter.getMysqlDao("mysql45");
        mysql45.excutesql("update department set name ='测试11' where depId='117445961' ");

        MysqlDao mysql80w = mostDataSourceProcessInter.getMysqlDao("mysql80w");
        mysql80w.excutesql("update department set name ='测试11' where depId='7'");

        try {
            Thread.sleep(200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public String findname(String name){
        MysqlDao datasource = mostDataSourceProcessInter.getMainMysqlDao();
        String s = datasource.queryFirstValToString("select name from student limit 1 ");
        return  s;
    }

}
