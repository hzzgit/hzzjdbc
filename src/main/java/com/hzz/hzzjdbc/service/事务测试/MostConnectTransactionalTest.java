package com.hzz.hzzjdbc.service.事务测试;

import com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig.TransactionalMostConnect;
import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.searchmain.MysqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/10/29 17:11
 */
@Service
public class MostConnectTransactionalTest {


    @Autowired(required = false)
    private MysqlDao mysqlDao;

    @Autowired(required = false)
    @Qualifier("mysqldata2")
    private MysqlDao mysqldata2;

    @Autowired(required = false)
    @Qualifier("mysqldata3")
    private MysqlDao mysqldata3;


    /*注释这边还不能支持多数据源的事务，仅能用封装的方法进行*/
    //@Transactional(rollbackFor = Exception.class)
    @TransactionalMostConnect(DataSourcesNames ={"mysqldata2","mysqldata","mysqldata3"},rollbackFor = Exception.class)
    public void testrollback() {
        MysqlUtil devmysqlUtil = mysqldata2.getMysqlUtil();
        devmysqlUtil.excutesql("update department set name ='测试22'  where depId ='117440629'");
        MysqlUtil localmysqlUtil = mysqlDao.getMysqlUtil();
        localmysqlUtil.excutesql("update student set name ='测试11' where id='1263264001' ");

        MysqlUtil dev45mysqlUtil = mysqldata3.getMysqlUtil();
        dev45mysqlUtil.excutesql("update department set name ='测试11' where depId='117445961' ");
        throw  new RuntimeException("");
    }


}
