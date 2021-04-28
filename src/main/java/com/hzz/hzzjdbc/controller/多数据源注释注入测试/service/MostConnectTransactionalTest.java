package com.hzz.hzzjdbc.controller.多数据源注释注入测试.service;

import com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig.TransactionalMostConnect;
import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;


/**
 * 业务逻辑处理类
 */
@Service
public class MostConnectTransactionalTest  {



    @Autowired
    private MysqlDao mysqlDao;

    @Resource
    private MysqlDao mysql96;
    @Resource
    private MysqlDao mysql45;
    @Resource
    private MysqlDao mysql80w;
    /**
    /**
     * 多数据源获取类
     */
//    @TransactionalMostConnect(DataSourcesNames ={"mysqlDao","mysql96","mysql45"},
//            rollbackFor = {Exception.class,RuntimeException.class, SQLException.class},
//            TransactionIsolation = Connection.TRANSACTION_REPEATABLE_READ,timeout = 10)
    @TransactionalMostConnect(DataSourcesNames = {"mysql96"})
    public void testrollback() throws SQLException {
        //mysqlDao.excutesql("update student set name ='测试11' where id='1263264001' ");
        mysql96.excutesql("update department set name ='修改事务'  where depId ='117440629'");
       // mysql45.excutesql("update department set name ='测试11' where depId='117445961' ");
      //  mysql80w.excutesql("update department set name ='测试11' where depId='7'");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @TransactionalMostConnect(DataSourcesNames = {"mysql96"})
    public String selectrollback(){
        return mysql96.queryFirstValToString("select name from department where depId ='117440629'");

    }


    public String findname(String name){
        String s = mysqlDao.queryFirstValToString("select name from student limit 1 ");
        return  s;
    }

}
